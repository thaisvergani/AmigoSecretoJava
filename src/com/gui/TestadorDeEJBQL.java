package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * Esta classe implementa uma ferramenta de teste para consultas EJB-QL, em
 * JFC/Swing. Deve ser empacotada dentro do JAR contendo as entidades de
 * persistencia, e a variável PERSISTENCE_UNIT deverá ser alterada para conter o
 * nome da unidade de persistência, conforme informado no arquivo
 * persistence.xml
 * 
 * @author Krohn
 * @since Julho 2011
 * 
 */
public class TestadorDeEJBQL extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextArea taComandos, taLog;
	private JPanel pFundo, pBotoes, pComandos;
	private JButton bLimpa, bExecuta;
	private JSplitPane split, split2;
	private JScrollPane sComandos, sResultados, sLog;
	private JTable tableResultados;
	private JLabel status;

	private JMenuBar menuBar;
	private JMenu mFile, mAction;
	private JMenuItem mOpen, mSave, mExit, mClean, mRun, mSaveAs;

	private String fileName;

	private EntityManagerFactory emf;
	private EntityManager em;

	private static final String APP_TITLE = "Testador de EJB-QL";
	private static final String PERSISTENCE_UNIT = "JPAStandalone";
	private static final Font STATEMENTS_FONT = new Font("Courier New", Font.PLAIN, 14);
	private static final Font LOG_FONT = new Font("Courier New", Font.PLAIN, 12);

	private static final int APP_HEIGHT = 600;
	private static final int APP_WIDTH = 800;

	private static final String PATTERN_LAYOUT = "%5p - %m%n";

	private final UndoManager undo = new UndoManager();
	private int row;

	/**
	 * Inicializa os componentes do JPA
	 * 
	 * @param persistenceUnit
	 *            O nome da unidade de persistencia utilizada
	 */
	private void initJPA(String persistenceUnit) {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
	}

	/**
	 * Inicializa os componentes que montam a interface gráfica
	 */
	@SuppressWarnings("serial")
	private void initComponents() {

		this.setTitle(APP_TITLE);

		menuBar = new JMenuBar();
		mFile = new JMenu("Arquivo");
		mOpen = new JMenuItem("Abrir...");
		mSave = new JMenuItem("Salvar");
		mSaveAs = new JMenuItem("Salvar Como...");
		mSave.setEnabled(false);
		mExit = new JMenuItem("Sair");
		mAction = new JMenu("Ações");
		mRun = new JMenuItem("Executar");
		mClean = new JMenuItem("Limpar");

		mOpen.addActionListener(this);
		mFile.add(mOpen);

		mSave.addActionListener(this);
		mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		mFile.add(mSave);

		mSaveAs.addActionListener(this);
		mFile.add(mSaveAs);

		mExit.addActionListener(this);
		mFile.add(mExit);

		mRun.addActionListener(this);
		mRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK));
		mAction.add(mRun);

		mClean.addActionListener(this);
		mClean.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		mAction.add(mClean);

		menuBar.add(mFile);
		menuBar.add(mAction);

		this.setJMenuBar(menuBar);

		taComandos = new JTextArea();
		taComandos.setFont(STATEMENTS_FONT);

		taLog = new JTextArea();
		taLog.setFont(LOG_FONT);

		Appender appender = new TextAreaAppender(taLog);
		appender.setLayout(new PatternLayout(PATTERN_LAYOUT));
		BasicConfigurator.configure(appender);

		pFundo = new JPanel(new BorderLayout());
		pBotoes = new JPanel(new FlowLayout());

		bLimpa = new JButton("Limpar");
		bLimpa.addActionListener(this);

		bExecuta = new JButton("Executar");
		bExecuta.addActionListener(this);

		tableResultados = new JTable();

		sComandos = new JScrollPane(taComandos);
		pComandos = new JPanel(new BorderLayout());
		pComandos.add(sComandos, BorderLayout.CENTER);
		status = new JLabel("Linha: 1 Coluna: 1");
		status.setFont(LOG_FONT);
		pComandos.add(status, BorderLayout.SOUTH);

		final Document doc = taComandos.getDocument();

		// Listen for undo and redo events
		doc.addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent evt) {
				undo.addEdit(evt.getEdit());
			}
		});

		// Create an undo action and add it to the text component
		taComandos.getActionMap().put("Undo", new AbstractAction("Undo") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canUndo()) {
						undo.undo();
					}
				} catch (CannotUndoException e) {
					showException("Erro de Undo!", e);
				}
			}
		});

		// Bind the undo action to ctl-Z
		taComandos.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK), "Undo");

		// Create a redo action and add it to the text component
		taComandos.getActionMap().put("Redo", new AbstractAction("Redo") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canRedo()) {
						undo.redo();
					}
				} catch (CannotRedoException e) {
					showException("Erro de Redo!", e);
				}
			}
		});

		// Bind the redo action to ctl-Y
		taComandos.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK), "Redo");

		// Cria uma ação para eliminar a linha sob o cursor
		taComandos.getActionMap().put("DeleteRow", new AbstractAction("DeleteRow") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					taComandos.replaceRange("", taComandos.getLineStartOffset(row), taComandos.getLineEndOffset(row));
				} catch (BadLocationException e) {
					showException("Erro excluindo linha!", e);
				}
			}
		});

		// Amarra a ação de excluir linha ao CTRL+D
		taComandos.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK), "DeleteRow");

		taComandos.addCaretListener(new CaretListener() {
			// Each time the caret is moved, it will trigger the listener and
			// its method caretUpdate.
			// It will then pass the event to the update method including the
			// source of the event (which is our textarea control)
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea) e.getSource();

				// Lets start with some default values for the line and column.
				int linenum = 1;
				int columnnum = 1;

				// We create a try catch to catch any exceptions. We will simply
				// ignore such an error for our demonstration.
				try {
					// First we find the position of the caret. This is the
					// number of where the caret is in relation to the start of
					// the JTextArea
					// in the upper left corner. We use this position to find
					// offset values (eg what line we are on for the given
					// position as well as
					// what position that line starts on.
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);

					// We subtract the offset of where our line starts from the
					// overall caret position.
					// So lets say that we are on line 5 and that line starts at
					// caret position 100, if our caret position is currently
					// 106
					// we know that we must be on column 6 of line 5.
					columnnum = caretpos - editArea.getLineStartOffset(linenum);

					// We have to add one here because line numbers start at 0
					// for getLineOfOffset and we want it to start at 1 for
					// display.
					// linenum += 1;
				} catch (Exception ex) {
				}

				// Once we know the position of the line and the column, pass it
				// to a helper function for updating the status bar.
				row = linenum;
				updateStatus(linenum + 1, columnnum + 1);
			}
		});

		sLog = new JScrollPane(taLog);
		sResultados = new JScrollPane(tableResultados);

		this.setSize(APP_WIDTH, APP_HEIGHT);

		this.initSplits();

		pBotoes.add(bExecuta);
		pBotoes.add(bLimpa);

		pFundo.add(split, BorderLayout.CENTER);
		pFundo.add(pBotoes, BorderLayout.SOUTH);

		this.getContentPane().add(pFundo);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		taComandos.requestFocus();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				exit();
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				split2.setDividerLocation(getWidth() / 2);
				split.setDividerLocation(getHeight() / 3);
			}
		});

		this.centralizeOnScreen();

	}

	// This helper function updates the status bar with the line number and
	// column number.
	private void updateStatus(int linenumber, int columnnumber) {
		status.setText("Linha: " + linenumber + " Coluna: " + columnnumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(bLimpa) || e.getSource().equals(mClean)) {
			this.clean();
		}

		if (e.getSource().equals(bExecuta) || e.getSource().equals(mRun)) {
			this.execute();
		}

		if (e.getSource().equals(mOpen)) {
			this.open();
		}

		if (e.getSource().equals(mSave)) {
			this.save();
		}

		if (e.getSource().equals(mSaveAs)) {
			this.saveAs();
		}

		if (e.getSource().equals(mExit)) {
			this.exit();
		}

	}

	/**
	 * Busca o diretório a partir de onde o último arquivo foi carregado ou onde
	 * o último foi salvo, para que os outros possam ser gravados/lidos do mesmo
	 * local sem a necessidade de ficar procurando por diretórios.
	 * 
	 * @return Um String contendo o nome do diretório corrente.
	 */
	private String getWorkingDir() {
		String dir = System.getProperty("user.dir");

		if (this.fileName != null) {
			dir = this.fileName.substring(0, this.fileName.lastIndexOf(File.separator));
		}
		return dir;

	}

	/**
	 * Abre a janela de abertura de arquivos, a partir de onde se pode escolher
	 * um arquivo que terá seu conteúdo carregado para o testador de consultas
	 */
	private void open() {
		JFileChooser fc = new JFileChooser(this.getWorkingDir());
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			BufferedReader d;
			StringBuffer sb = new StringBuffer();
			try {

				this.fileName = file.getAbsolutePath();

				d = new BufferedReader(new FileReader(file));
				String line;
				while ((line = d.readLine()) != null) {
					sb.append(line + "\n");
				}
				taComandos.setText(sb.toString());
				d.close();

				this.setTitle(APP_TITLE + " - " + this.fileName);
			} catch (FileNotFoundException fe) {
				showException("Arquivo não encontrado : " + this.fileName, fe);
			} catch (IOException ioe) {
				showException("Erro de E/S : ", ioe);
			}
		}
		mSave.setEnabled(true);
		taComandos.requestFocus();

	}

	/**
	 * Abre a janela de salvamento de arquivos, onde se poderá escolher um nome
	 * de arquivo para onde a consulta em teste será salva.
	 */
	private void saveAs() {

		JFileChooser fc = new JFileChooser(this.getWorkingDir());
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			this.fileName = file.getAbsolutePath();
			this.setTitle(APP_TITLE + " - " + this.fileName);
			this.save();

		}
		mSave.setEnabled(true);
		taComandos.requestFocus();

	}

	/**
	 * Realiza o salvamento da consulta em teste para o arquivo previamente
	 * selecionado.
	 */
	private void save() {
		try {
			DataOutputStream d = new DataOutputStream(new FileOutputStream(this.fileName));
			String line = taComandos.getText();
			BufferedReader br = new BufferedReader(new StringReader(line));
			while ((line = br.readLine()) != null) {
				d.writeBytes(line + "\r\n");
			}
			d.close();
		} catch (Exception ex) {
			showException("Problema salvando arquivo : " + this.fileName, ex);
		}
		taComandos.requestFocus();
	}

	/**
	 * Libera os recursos em uso e encerra a aplicação
	 */
	private void exit() {
		try {
			this.em.close();
			this.emf.close();
		} catch (Exception e) {
			showException("Ocorreu um erro ao encerrar o EntityManager!", e);
		} finally {
			System.exit(0);
		}
	}

	/**
	 * Apaga todos os conteúdos da tela, deixando-a pronta para novos testes.
	 * Não apaga o arquivo corrente.
	 */
	private void clean() {
		this.cleanStatements();
		this.cleanResults();
	}

	/**
	 * Apaga o conteúdo da tabela de resultados
	 */
	private void cleanResults() {
		this.tableResultados = new JTable();
		sResultados = new JScrollPane(tableResultados);
		this.pFundo.remove(this.split);
		this.initSplits();
		pFundo.add(this.split);
		this.validate();
		this.repaint();
	}

	/**
	 * Inicializa os painéis split da aplicação
	 */
	private void initSplits() {

		split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pComandos, sLog);
		split2.setContinuousLayout(true);
		split2.setOneTouchExpandable(true);
		split2.setDividerLocation(this.getWidth() / 2);

		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, sResultados);
		split.setContinuousLayout(true);
		split.setOneTouchExpandable(true);
		split.setDividerLocation(getHeight() / 3);

	}

	/**
	 * Apaga o conteúdo digitado no campo para a inserção das consultas JPA.
	 */
	private void cleanStatements() {
		taComandos.setText("");
		taLog.setText("");
		taComandos.requestFocus();
		updateStatus(1, 1);
	}

	/**
	 * Tenta executar a query em teste, e exibe os resultados ou uma mensagem
	 * indicando que houve erro na execução.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void execute() {

		String comando = taComandos.getSelectedText() != null ? this.removeComments(taComandos.getSelectedText()) : this.removeComments(taComandos
						.getText());

		try {
			Query q = em.createQuery(comando);
			List resultados = q.getResultList();
			this.displayResults(resultados);
			taComandos.requestFocus();
		} catch (Exception e) {
			if (comando.length() == 0) {
				JOptionPane.showMessageDialog(this, "Não há nada para ser executado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				showException("Ocorreu um erro durante a execução da consulta!", e);
			}
			this.cleanResults();
		}
	}

	private String removeComments(String statement) {

		StringBuilder sb = new StringBuilder();
		String[] linhas = statement.split("\n");
		for (int i = 0; i < linhas.length; i++) {

			if (linhas[i].contains("//")) {
				sb.append(linhas[i].substring(0, linhas[i].indexOf("//")) + "\n");
			} else {
				sb.append(linhas[i] + "\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Apresenta as exceções ocorridas em uma tela rolável, de maneira que o
	 * usuário possa entender o que está fazendo errado!
	 * 
	 * @param title
	 *            O título da janela
	 * @param e
	 *            A exceção a ser apresentada
	 */
	private void showException(String title, Throwable e) {

		StringBuilder sb = new StringBuilder(title + "\n\n" + e.getMessage() + " \n\n");

		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			sb.append("\tat " + trace[i] + "\n");
		}

		JTextArea area = new JTextArea(sb.toString());
		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension((int) (this.getWidth() * .66), (int) (this.getHeight() * .66)));

		JOptionPane.showMessageDialog(this, scroll, title, JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Obtém os nomes dos atributos de uma entidade, apara serem usados como
	 * cabeçalhos na JTable que exibe os resultados
	 * 
	 * @param o
	 *            Um objeto retornado pela consulta JPA
	 * @return Um Vector contendo os nomes dos cabeçalhos das colunas para a
	 *         JTable
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector getAttributes(Object o) {

		Vector v = new Vector();

		try {
			BeanInfo info;
			info = Introspector.getBeanInfo(o.getClass());
			PropertyDescriptor[] pds = info.getPropertyDescriptors();

			for (int i = 0; i < pds.length; i++) {
				if (!"class".equals(pds[i].getName())) {
					v.add(pds[i].getName());
				}
			}
		} catch (IntrospectionException e) {
			showException("Ocorreu um erro na instrospecção dos atributos da entidade!", e);
		}
		return v;
	}

	/**
	 * Converte objetos encontrados nos resultados para a classe vector, de
	 * forma que os mesmos possam ser exibidos em JTables
	 * 
	 * @param o
	 *            Um objeto retirado da lista de objetos resultado da execução
	 *            da consulta JPA
	 * @return Um Vector contendo os dados resultantes da consulta
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector entityToVector(Object o) {
		Vector v = new Vector();

		if (o != null) {

			if (o.getClass().getName().startsWith("java.lang")) {
				v.add(o);
			} else
				if (o.getClass().getName().startsWith("[Ljava.lang")) {
					Object[] a = (Object[]) o;
					for (int i = 0; i < a.length; i++) {
						v.add(a[i]);
					}
				} else {
					Vector attributes = this.getAttributes(o);
					for (int i = 0; i < attributes.size(); i++) {
						try {
							v.add(PropertyUtils.getSimpleProperty(o, (String) attributes.get(i)));
						} catch (IllegalAccessException e) {
							showException("Acesso Ilegal!", e);
						} catch (InvocationTargetException e) {
							showException("Erro de invocação de método por reflection!", e);
						} catch (NoSuchMethodException e) {
							showException("Erro de reflection : Método não encontrado!", e);
						}
					}
				}
		}
		return v;
	}

	/**
	 * Exibe na tabela de resultados os valores resultantes da execução da
	 * consulta EJB-QL.
	 * 
	 * @param resultados
	 *            A lista de resultados retornada pela consulta
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void displayResults(List resultados) {

		if (resultados != null && !resultados.isEmpty()) {

			Object o = resultados.get(0);
			Vector columnHeads = this.getAttributes(o);

			if (o.getClass().getName().startsWith("[Ljava.lang")) {
				Object[] a = (Object[]) o;
				for (int i = 0; i < a.length; i++) {
					columnHeads.add(a[i].getClass().getName());
				}
			} else {
				columnHeads = this.getAttributes(o);
				if (columnHeads.isEmpty()) {
					columnHeads.add(o.getClass().getName());
				}
			}

			Vector rows = new Vector();

			for (Object object : resultados) {
				rows.add(this.entityToVector(object));
			}

			this.tableResultados = new JTable(rows, columnHeads);
			this.pFundo.remove(this.split);
			sResultados = new JScrollPane(this.tableResultados);

			this.initSplits();

			pFundo.add(this.split);
			this.validate();
			this.repaint();

		} else {
			JOptionPane.showMessageDialog(this, "A consulta não retornou nenhum resultado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			this.cleanResults();
		}
	}

	/**
	 * Centraliza a janela na tela, levando em conta a resolução usada no
	 * computador
	 */
	private void centralizeOnScreen() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		int appHeight = this.getHeight();
		int appWidth = this.getWidth();

		this.setLocation((screenHeight - appHeight) / 2, (screenWidth - appWidth) / 2);

	}

	/**
	 * Método main da aplicação
	 * 
	 * Altere o valor de PERSISTENCE_UNIT para a unidade de persistência usada
	 * antes de executá-lo
	 * 
	 * @param args
	 *            Argumentos da linha de comando
	 */
	public static void main(String[] args) {

		TestadorDeEJBQL t = new TestadorDeEJBQL();
		t.initComponents();
		t.initJPA(PERSISTENCE_UNIT);
		t.setVisible(true);

	}

}

/**
 * Classe interna para direcionar o output de depuração do Hibernate para a
 * tela, de forma que seja possível ver o SQL gerado
 * 
 * @author krohn
 * 
 */
class TextAreaAppender extends WriterAppender {

	static private JTextArea jTextArea = null;

	public TextAreaAppender(JTextArea textArea) {
		super();
		TextAreaAppender.jTextArea = textArea;
	}

	/** Set the target JTextArea for the logging information to appear. */
	static public void setTextArea(JTextArea jTextArea) {
		TextAreaAppender.jTextArea = jTextArea;
	}

	/**
	 * Format and then append the loggingEvent to the stored JTextArea.
	 */
	@Override
	public void append(LoggingEvent loggingEvent) {

		final String message = this.layout.format(loggingEvent);

		// Append formatted message to textarea using the Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jTextArea.append(message);
			}
		});
	}
}