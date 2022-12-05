package com.template;

import java.io.IOException;

import org.tedros.chat.server.ChatServer;

public class Main extends ChatServer {

	public Main(int port) throws IOException {
		super(port);
	}
	
	public static void main(String[] args) {
		ChatServer.main(args);
	}

}
