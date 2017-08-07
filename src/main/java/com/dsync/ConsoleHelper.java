package com.dsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

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

	public static String readString(Set<String> menu) {
		String line = readString();
		if (menu.contains(line)) {
			return line;
		}
		return line = readString(menu);
	}
}
