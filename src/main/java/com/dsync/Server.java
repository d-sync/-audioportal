package com.dsync;




import com.dsync.services.UserService;
import com.dsync.services.UserServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static class Handler extends Thread {
//	private static class Handler {
		private Socket socket;

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
			connection.send(new Message(Operation.ID_ACCEPTED));
		}

		private void serverMainLoop(Connection connection) throws IOException, ClassNotFoundException {
			connection.send(new Message(Operation.MAIN_MENU));
			while (true) {
				Message message = connection.receive();

			}
		}

//		@Override
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

	private void serverMainLoop(Connection connection, String id) throws IOException, ClassNotFoundException {
		while (true) {
//			Message message = connection.receive();
		}
	}
}
