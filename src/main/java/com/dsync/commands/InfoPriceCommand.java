package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class InfoPriceCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Весь контент стоит 100 рублей. Наберите:");
		ConsoleHelper.writeMessage("* - для выхода в основное меню");
		ConsoleHelper.writeMessage("# - для выхода в предыдущее меню");
	}
}
