package com.dsync;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

public class Client {

	protected Connection connection;
	private volatile boolean clientConnected = false;

	public class SocketThread extends Thread {
//		public class SocketThread {

//		@Override
		public void run() {
			try {
				connection = new Connection(new Socket("localhost", 8080));
				clientHandshake();
				clientMainLoop();
			} catch (IOException | ClassNotFoundException e) {

			}
		}

		protected void notifyConnectionStatusChanged(boolean clientConnectedd) {
			clientConnected = clientConnectedd;
			synchronized (Client.this)
			{
				Client.this.notify();
			}
		}

		protected void clientHandshake() throws IOException, ClassNotFoundException {
			while (true) {
				Message message = connection.receive();
				if (message.getOperation() == Operation.AUTHENTIFICATION_REQUEST) {
					connection.send(new Message(Operation.AUTHENTIFICATION_RESPONSE, getUserName()));
				} else if (message.getOperation() == Operation.ID_ACCEPTED) {
					notifyConnectionStatusChanged(true);
					break;
				} else if (message.getOperation() == Operation.ID_NOT_FOUND) {
					ConsoleHelper.writeMessage("Пользователь с таким id не обнаружен в базе.");
				}
			}
		}

		protected void clientMainLoop() throws IOException, ClassNotFoundException {
			while (true) {
				Message message = connection.receive();
				switch (message.getOperation()) {
					case MAIN_MENU: {
						ConsoleHelper.writeMessage("Для выбора интересующего Вас раздела введите число.");
						ConsoleHelper.writeMessage("1. Просмотр контента\t2. Управление личным кабинетом\t 3. Инфо");
						switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "3")))) {
							case "1": connection.send(new Message(Operation.VIEW_CONTENT)); break;
							case "2": connection.send(new Message(Operation.MY_ACCOUNT)); break;
							case "3": connection.send(new Message(Operation.INFO)); break;
						}
						break;
					}
					case INFO: {
						ConsoleHelper.writeMessage("Для получения информации о возможностях услуги нажмите 1. Для того чтобы узнать стоимость нажмите 2.");
						switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2")))) {
							case "1": {
								ConsoleHelper.writeMessage("Сервис позволяет прослушать контент и купить его.\nВыход в основное меню - *, # - вернуться в предыдущее меню");
								switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("*", "#")))) {
									case "*": connection.send(new Message(Operation.MAIN_MENU)); break;
									case "#": connection.send(new Message(Operation.INFO)); break;
								}
								break;
							}
							case "2": {
								ConsoleHelper.writeMessage("Весть контент стоит 100 рублей.\nВыход в основное меню - *, # - вернуться в предыдущее меню");
								switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("*", "#")))) {
									case "#": connection.send(new Message(Operation.INFO)); break;
									case "*": connection.send(new Message(Operation.MAIN_MENU)); break;
								}
								break;
							}
						}
						break;
					}
					case VIEW_CONTENT: {

						break;
					}
					case MY_ACCOUNT: {
						ConsoleHelper.writeMessage("Вы находитесь в меню управления услугой и редактирования персональной фонотеки.\nПри проигрывании мелодии наберите:\n1 для перехода к следующей\n2 для удаления данной мелодии.\n* для выхода в основное меню");

						break;
					}
				}
			}
		}
	}

	protected String getUserName() {
		ConsoleHelper.writeMessage("Enter your id.");
		String name = ConsoleHelper.readString();

		return name;
	}

	protected SocketThread getSocketThread() {
		return new SocketThread();
	}

	protected void send(String text) {

	}

	public void run() {
		SocketThread socketThread = getSocketThread();
		socketThread.setDaemon(true);
		socketThread.start();
		socketThread.run();

		try {
			synchronized (this) {
				this.wait();
			}
		} catch (InterruptedException e) {
			ConsoleHelper.writeMessage("Ошибка во время ожидания.");
		}
		while (clientConnected) {
			String text = ConsoleHelper.readString();
			send(text);
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();
	}



}
