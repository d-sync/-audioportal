package com.dsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void writeMessage(String message) {
		System.out.println(message);
	}

	public static String readString() {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	public static long readLong() {
		long num;
		try {
			num = Long.parseLong(readString());
		} catch (NumberFormatException e) {
			writeMessage("Ошибка при попытке ввода числа. Попробуйте еще раз.");
			num = readLong();
		}
		return num;
	}
	public static int readInt() {
		int num;
		try {
			num = Integer.parseInt(readString());
		} catch (NumberFormatException e) {
			writeMessage("Ошибка при попытке ввода числа. Попробуйте еще раз.");
			num = readInt();
		}
		return num;
	}
}
