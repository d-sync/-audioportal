package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class MyAccountCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Вы находитесь в меню управления услугой и редактирования персональной фонотеки.");
		ConsoleHelper.writeMessage("При проигрывании мелодии наберите:");
		ConsoleHelper.writeMessage("1 - для перехода к следующей");
		ConsoleHelper.writeMessage("2 - для удаления данной мелодии");
		ConsoleHelper.writeMessage("* - для выхода в основное меню");
	}
}
