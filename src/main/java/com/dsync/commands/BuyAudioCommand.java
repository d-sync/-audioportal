package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class BuyAudioCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("You bought this track");
	}
}
