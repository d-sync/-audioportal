package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class NextAudioCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Next audio");
	}
}
