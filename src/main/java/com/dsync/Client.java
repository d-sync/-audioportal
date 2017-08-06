package com.dsync;

import com.dsync.commands.CommandExecutor;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

public class Client {



	protected Connection connection;
	private volatile boolean clientConnected = false;
	private volatile Operation currentOperation;

	public class SocketThread extends Thread {
//		public class SocketThread {

		@Override
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
					connection.send(new Message(Operation.AUTHENTIFICATION_RESPONSE, getUserMSISDN()));
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
				if (message != null) {
					currentOperation = message.getOperation();
					if (currentOperation == Operation.VIEW_CONTENT_POP || currentOperation == Operation.VIEW_CONTENT_NEW || currentOperation == Operation.VIEW_CONTENT_HITS || currentOperation == Operation.NEXT_AUDIO || currentOperation == Operation.MY_ACCOUNT || currentOperation == Operation.DELETE_AUDIO) {
						CommandExecutor.execute(currentOperation);
						if (message.getAudioContent() != null) {
							ConsoleHelper.writeMessage("\nplaying: " + message.getAudioContent());
						} else {
							ConsoleHelper.writeMessage("\nВаша фонотека пуста.");
						}
					}
					else CommandExecutor.execute(currentOperation);
				}
			}
		}
	}

	protected String getUserMSISDN() {
		ConsoleHelper.writeMessage("Введите ваш msssdn.");
		String name = ConsoleHelper.readString();

		return name;
	}

	protected SocketThread getSocketThread() {
		return new SocketThread();
	}

	protected void send(Operation operation) {
		try {
			connection.send(new Message(operation));
		} catch (IOException e) {
			ConsoleHelper.writeMessage("Ошибка ");
			clientConnected = false;
		}
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
		clientLogic();
	}

	private void clientLogic() {
		while (clientConnected) {
			if (currentOperation == Operation.MAIN_MENU) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "3")));
				switch (text) {
					case "1" : currentOperation = Operation.VIEW_CONTENT; send(currentOperation); break;
					case "2" : currentOperation = Operation.MY_ACCOUNT; send(currentOperation); break;
					case "3" : currentOperation = Operation.INFO; send(currentOperation); break;
				}

			} else if (currentOperation == Operation.VIEW_CONTENT) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "3", "*")));
				switch (text) {
					case "1" : currentOperation = Operation.VIEW_CONTENT_POP; send(currentOperation); break;
					case "2" : currentOperation = Operation.VIEW_CONTENT_NEW; send(currentOperation); break;
					case "3" : currentOperation = Operation.VIEW_CONTENT_HITS; send(currentOperation); break;
					case "*" : currentOperation = Operation.MAIN_MENU; send(currentOperation); break;
				}
			} else if (currentOperation == Operation.VIEW_CONTENT_POP || currentOperation == Operation.VIEW_CONTENT_NEW || currentOperation == Operation.VIEW_CONTENT_HITS || currentOperation == Operation.NEXT_AUDIO || currentOperation == Operation.BUY_AUDIO) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "*", "#")));
				switch (text) {
					case "1" : currentOperation = Operation.NEXT_AUDIO; send(currentOperation); break;
					case "2" : currentOperation = Operation.BUY_AUDIO; send(currentOperation); break;
					case "*" : currentOperation = Operation.MAIN_MENU; send(currentOperation); break;
					case "#" : currentOperation = Operation.VIEW_CONTENT; send(currentOperation); break;
				}
			} else if (currentOperation == Operation.MY_ACCOUNT || currentOperation == Operation.NEXT_MY_AUDIO || currentOperation == Operation.DELETE_AUDIO) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "*")));
				switch (text) {
					case "1" : currentOperation = Operation.NEXT_MY_AUDIO; send(currentOperation); break;
					case "2" : currentOperation = Operation.DELETE_AUDIO; send(currentOperation); break;
					case "*" : currentOperation = Operation.MAIN_MENU; send(currentOperation); break;
				}
			} else if (currentOperation == Operation.INFO) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2")));
				switch (text) {
					case "1" : currentOperation = Operation.INFO_OPPORTUNITY; send(currentOperation); break;
					case "2" : currentOperation = Operation.INFO_PRICE; send(currentOperation); break;
				}
			} else if (currentOperation == Operation.INFO_OPPORTUNITY || currentOperation == Operation.INFO_PRICE) {
				String text = ConsoleHelper.readString(new HashSet<>(Arrays.asList("*", "#")));
				switch (text) {
					case "*" : currentOperation = Operation.MAIN_MENU; send(currentOperation); break;
					case "#" : currentOperation = Operation.INFO; send(currentOperation); break;
				}
			}
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();
	}



}
