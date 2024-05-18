package org.example.lessonFive.lection.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTwo {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Login");
        String login = scanner.nextLine();
        Socket socket1 = new Socket(InetAddress.getLocalHost(),1200);
        Client client = new Client(socket1,login);
        if (socket1.isConnected()){
            client.listenForMessage();
            client.sendMessage();
        }
        System.out.println("Disconnect");
    }
}
