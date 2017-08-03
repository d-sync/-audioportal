package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class InfoOpportunityCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Сервис позволяет прослушать контент и купить его. Наберите:");
		ConsoleHelper.writeMessage("* - для выхода в основное меню");
		ConsoleHelper.writeMessage("# - для выхода в предыдущее меню");
	}
}
