package com.dsync;

import java.io.IOException;
import java.net.Socket;

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
						ConsoleHelper.readInt();
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
//		socketThread.run();

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
