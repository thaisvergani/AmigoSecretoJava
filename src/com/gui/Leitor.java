package com.gui;

import java.util.Scanner;

public class Leitor {
	private static Scanner _scanner = new Scanner(System.in);

	public static String lerLinha() {
		limpar();
		return _scanner.nextLine();
	}

	public static String ler() {
		limpar();
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
}
