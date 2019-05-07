package com.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Console {
	private static Scanner _scanner = new Scanner(System.in);

	public static String lerLinha() {
		limpar();
		return _scanner.nextLine();
	}

	public static String ler() {
		return _scanner.next();
	}

	private static void limpar() {
		_scanner.nextLine();
	}

	public static boolean lerBoolean() {
		String opcao = ler();
		return opcao.equalsIgnoreCase("Y") || opcao.equalsIgnoreCase("S");
	}

	public static int lerInt() {
		return _scanner.nextInt();
	}

	public static float lerFloat() {
		return _scanner.nextFloat();
	}
	
	public static Date lerDate() {
		Date data = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		while (data == null) {
			String linha = lerLinha();
			try {
				data = format.parse(linha);
			} catch (ParseException e) {
				data = null;
			}
		}
		
		return data;
	}
	
	public static void escrever(Object o) {
		System.out.print(o);
	}

	public static void escreverLinha(Object o) {
		System.out.println(o);
	}
}
