package com.dsync.commands;

import com.dsync.ConsoleHelper;

public class ViewContentCommand implements Command {
	@Override
	public void execute() {
		ConsoleHelper.writeMessage("Каталог контента. Для начала прослушивания мелодий выберите категорию:");
		ConsoleHelper.writeMessage("Популярные - 1\nНовинки - 2\nХиты - 3\nДля выхода введите *");
	}
}
