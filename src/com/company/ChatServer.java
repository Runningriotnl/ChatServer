package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        ChatServer chatServer = new ChatServer();
        chatServer.serverListeningOnPort(args[0]);

    }

    private void serverListeningOnPort(String arg) {
        // Port number the server listens on
        int portNumber = Integer.parseInt(arg);
        boolean listening = true;
        List<Session> clientSessions = new ArrayList<>();

        try (
            //Set up server to listen on port inputed by user (args[0])
            ServerSocket serverSocket = new ServerSocket(portNumber)) {

            // Server is always listening for new clients
            while (listening) {
                // Creates an session for the client
                System.out.println("The server is ready and listening");
                Session client = new Session(serverSocket.accept(), Collections.unmodifiableList(clientSessions));
                System.out.println("A connection is being made to the server");
                clientSessions.add(client);
                client.start();

            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

