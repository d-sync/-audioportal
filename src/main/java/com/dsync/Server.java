package com.dsync;




import com.dsync.model.User;
import com.dsync.services.UserService;
import com.dsync.services.UserServiceImpl;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

public class Server {

	private static class Handler extends Thread {
//	private static class Handler {
		private Socket socket;
		private Operation currentOperation;
		private String msisdn;

		private UserService userService = new UserServiceImpl();

		public Handler(Socket socket) {
			this.socket = socket;
		}

		private void serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
			connection.send(new Message(Operation.AUTHENTIFICATION_REQUEST));
			Message message = connection.receive();
			String name = message.getData();
			if (name == null || !userService.isUserExists(name) || message.getOperation() != Operation.AUTHENTIFICATION_RESPONSE) {
				connection.send(new Message(Operation.ID_NOT_FOUND));
				serverHandshake(connection);
			}
			msisdn = message.getData();
			connection.send(new Message(Operation.ID_ACCEPTED));
		}

		private void serverMainLoop(Connection connection) throws IOException, ClassNotFoundException {
			connection.send(menuText(""));
			while (true) {
				Message message = connection.receive();
				connection.send((menuText(message.getData())));

			}
		}
		private Message menuText(String point) {
			Message message = null;
			if (currentOperation == null) {
				currentOperation = Operation.MAIN_MENU;
			}
			switch (currentOperation) {
				case MAIN_MENU: {
					switch (point) {
						case "1": {
							currentOperation = Operation.VIEW_CONTENT;
							return new Message("Каталог контента. Для начала прослушивания мелодий выберите категорию:\nПопулярные - 1, Новинки - 2, Хиты - 3, Для выхода наберите *");
						}
						case "2": currentOperation = Operation.MY_ACCOUNT; point = ""; break;
						case "3": currentOperation = Operation.INFO; point = ""; break;
						default: {
							return new Message("Для выбора интересующего Вас раздела введите число.\n1. Просмотр контента\t2. Управление личным кабинетом\t3. Инфо", new HashSet<>(Arrays.asList("1", "2", "3")));
						}
					}
					break;
				}
				case VIEW_CONTENT: {
					switch (point) {
						case "1":  {
							try {
								URL url = new URL("/com/dsync/one.wav");
								AudioClip clip = Applet.newAudioClip(url);
								clip.play();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							return new Message("Выбрана категория популярные");
						}
						case "*": {
							currentOperation = Operation.MAIN_MENU;
							return new Message("Для выбора интересующего Вас раздела введите число.\n1. Просмотр контента\t2. Управление личным кабинетом\t3. Инфо");
						}
						default: {
							return new Message("");

						}
					}

				}
				case MY_ACCOUNT: {

					break;
				}
				case INFO: {

					break;
				}
			}


			return message;
		}
		public void someMethod() {
//			switch (message.getOperation()) {
//				case MAIN_MENU: {
//					ConsoleHelper.writeMessage("Для выбора интересующего Вас раздела введите число.");
//					ConsoleHelper.writeMessage("1. Просмотр контента\t2. Управление личным кабинетом\t 3. Инфо");
//					switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2", "3")))) {
//						case "1": connection.send(new Message(Operation.VIEW_CONTENT)); break;
//						case "2": connection.send(new Message(Operation.MY_ACCOUNT)); break;
//						case "3": connection.send(new Message(Operation.INFO)); break;
//					}
//					break;
//				}
//				case INFO: {
//					ConsoleHelper.writeMessage("Для получения информации о возможностях услуги нажмите 1. Для того чтобы узнать стоимость нажмите 2.");
//					switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("1", "2")))) {
//						case "1": {
//							ConsoleHelper.writeMessage("Сервис позволяет прослушать контент и купить его.\nВыход в основное меню - *, # - вернуться в предыдущее меню");
//							switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("*", "#")))) {
//								case "*": connection.send(new Message(Operation.MAIN_MENU)); break;
//								case "#": connection.send(new Message(Operation.INFO)); break;
//							}
//							break;
//						}
//						case "2": {
//							ConsoleHelper.writeMessage("Весть контент стоит 100 рублей.\nВыход в основное меню - *, # - вернуться в предыдущее меню");
//							switch (ConsoleHelper.readString(new HashSet<>(Arrays.asList("*", "#")))) {
//								case "#": connection.send(new Message(Operation.INFO)); break;
//								case "*": connection.send(new Message(Operation.MAIN_MENU)); break;
//							}
//							break;
//						}
//					}
//					break;
//				}
//				case VIEW_CONTENT: {
//
//					break;
//				}
//				case MY_ACCOUNT: {
//					ConsoleHelper.writeMessage("Вы находитесь в меню управления услугой и редактирования персональной фонотеки.\nПри проигрывании мелодии наберите:\n1 для перехода к следующей\n2 для удаления данной мелодии.\n* для выхода в основное меню");
//
//					break;
//				}
//			}
		}




		@Override
		public void run() {
			ConsoleHelper.writeMessage("Установлено новое соединение с уадленным адресом " + socket.getRemoteSocketAddress());
			try {
				Connection connection = new Connection(socket);
				serverHandshake(connection);
				serverMainLoop(connection);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

//		UserService userService = new UserServiceImpl();
//		User user = new User("1212");
//		userService.addUser(user);


		try(ServerSocket serverSocket = new ServerSocket(8080)) {
			ConsoleHelper.writeMessage("Server started.");

			while (true) {
				Handler handler = new Handler(serverSocket.accept());
				handler.start();
//				handler.run();
			}
		} catch (Exception e) {

		}
	}

}
