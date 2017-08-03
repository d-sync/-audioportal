package com.dsync.commands;

import com.dsync.Operation;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

	private static Map<Operation, Command> map = new HashMap<>();

	static {
		map.put(Operation.MAIN_MENU, new MainMenuCommand());
		map.put(Operation.VIEW_CONTENT, new ViewContentCommand());
		map.put(Operation.VIEW_CONTENT_POP, new ViewContentPopCommand());
		map.put(Operation.VIEW_CONTENT_NEW, new ViewContentNewCommand());
		map.put(Operation.VIEW_CONTENT_HITS, new ViewContentHitsCommand());
		map.put(Operation.NEXT_AUDIO, new NextAudioCommand());
		map.put(Operation.NEXT_MY_AUDIO, new NextAudioCommand());
		map.put(Operation.BUY_AUDIO, new BuyAudioCommand());
		map.put(Operation.DELETE_AUDIO, new DeleteAudioCommand());
		map.put(Operation.MY_ACCOUNT, new MyAccountCommand());
		map.put(Operation.INFO, new InfoCommand());
		map.put(Operation.INFO_OPPORTUNITY, new InfoOpportunityCommand());
		map.put(Operation.INFO_PRICE, new InfoPriceCommand());

	}

	public static final void execute(Operation operation) {
		map.get(operation).execute();
	}

	private CommandExecutor() {}
}
