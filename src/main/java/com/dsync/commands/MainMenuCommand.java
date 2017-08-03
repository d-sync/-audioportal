package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class MainMenuCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Выберите раздел: 1 - для просмотра контента, 2 - для входа в ЛК, 3 - раздел инфо");
	}
}
