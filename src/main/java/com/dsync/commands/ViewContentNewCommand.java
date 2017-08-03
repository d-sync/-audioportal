package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class ViewContentNewCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Вы находитесь в разделе: Новинки");
		ConsoleHelper.writeMessage("При проигрывании мелодии наберите: ");
		ConsoleHelper.writeMessage("1 - для перехода к следующей");
		ConsoleHelper.writeMessage("2 - для покупки данной мелодии");
		ConsoleHelper.writeMessage("* - для выхода в основное меню");
		ConsoleHelper.writeMessage("# - для выхода в предыдущее меню");
	}
}
