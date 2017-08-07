package com.dsync;

import com.dsync.configs.initializer.TestDataInitializer;
import com.dsync.model.AudioContent;
import com.dsync.services.AudioContentService;
import com.dsync.services.AudioContentServiceImpl;
import com.dsync.services.UserService;
import com.dsync.services.UserServiceImpl;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {

	private static class Handler extends Thread {
		private Socket socket;
		private String msisdn;
		private Queue<AudioContent> currentAudioContents;
		private AudioContent audioContent;

		private UserService userService = new UserServiceImpl();
		private AudioContentService audioContentService = new AudioContentServiceImpl();

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
				Operation o = message.getOperation();
				if (o == Operation.VIEW_CONTENT_POP) {
					currentAudioContents =  new LinkedList<>(audioContentService.getPopularAudioContent());
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.VIEW_CONTENT_POP, audioContent));
				} else if (o == Operation.VIEW_CONTENT_NEW) {
					currentAudioContents = new LinkedList<>(audioContentService.getNewAudioContent());
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.VIEW_CONTENT_NEW, audioContent));
				} else if (o == Operation.VIEW_CONTENT_HITS) {
					currentAudioContents = new LinkedList<>(audioContentService.getHitsAudioContent());
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.VIEW_CONTENT_HITS, audioContent));
				} else if (o == Operation.NEXT_AUDIO || o == Operation.NEXT_MY_AUDIO) {
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.NEXT_AUDIO, audioContent));
				} else if (o == Operation.BUY_AUDIO) {
					userService.buyCurrentTrack(msisdn, audioContent);
					connection.send(message);
				} else if (o == Operation.MY_ACCOUNT) {
					currentAudioContents = new LinkedList<>(audioContentService.getUserAudioContent(msisdn));
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.MY_ACCOUNT, audioContent));
				} else if (o == Operation.DELETE_AUDIO) {
					userService.deleteAudioFromUserAccount(audioContent, msisdn);
					currentAudioContents.remove(audioContent);
					audioContent = currentAudioContents.poll();
					currentAudioContents.add(audioContent);
					connection.send(new Message(Operation.DELETE_AUDIO, audioContent));
				}

				else {
					connection.send(message);
				}


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

		TestDataInitializer.init();

		try(ServerSocket serverSocket = new ServerSocket(8080)) {
			ConsoleHelper.writeMessage("Server started.");

			while (true) {
				Handler handler = new Handler(serverSocket.accept());
				handler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
