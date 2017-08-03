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
			} else {
				msisdn = message.getData();
				connection.send(new Message(Operation.ID_ACCEPTED));
			}

		}

		private void serverMainLoop(Connection connection) throws IOException, ClassNotFoundException {
			connection.send(new Message(Operation.MAIN_MENU));
			while (true) {
				Message message = connection.receive();
				connection.send(message);
			}
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
