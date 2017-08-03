package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class DeleteAudioCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("This track is deleted");
	}
}
