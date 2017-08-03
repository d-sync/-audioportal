package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class InfoCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Раздел помощь. Наберите:");
		ConsoleHelper.writeMessage("1 - для получения информации о возможностях услуги ");
		ConsoleHelper.writeMessage("2 - для того чтобы узнать стоимость услуги.");
	}
}
