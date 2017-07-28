package com.dsync.commands;



import com.dsync.Operation;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

	private static Map<Operation, Command> map = new HashMap<>();

	static {

	}

	public static final String execute(Operation operation) {
		return map.get(operation).execute();
	}

	private CommandExecutor() {}
}
