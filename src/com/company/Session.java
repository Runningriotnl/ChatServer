package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Session extends Thread  {
    private final Socket socket;
    private final List<Session> clientSessions;

    public Session(Socket socket, List<Session> clientSessions) {
        this.socket = socket;
        this.clientSessions = clientSessions;
    }

    public void run() {

        // Try with resource closes program when the connection is finished
        try {
            String inputLine;
            // reading from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Reads messages from the client and sends the same message back to the client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Session " + this + " is sending the following message: " + inputLine);
                for(Session s : clientSessions) {
                    s.send(inputLine, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void send(String message, Session ses)  throws IOException {
        // Writing to the client
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("From " + ses + ": " + message);
    }


}
