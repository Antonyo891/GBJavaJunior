package org.example.lessonFive.lection.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    private final ServerSocket SERVER_SOCKET;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;

    public Server(ServerSocket serverSocket) {
        this.SERVER_SOCKET = serverSocket;
    }
    public void runServer(){
        System.out.println("Server START");
        try {
            while (!SERVER_SOCKET.isClosed()){
                Socket socket = SERVER_SOCKET.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                name = bufferedReader.readLine();
                if (ClientManager.checkName(name)) {
                    ClientManager client = new ClientManager(socket,name);
                    Thread thread = new Thread(client);
                    thread.start();
                    System.out.println("A new client is connected");
                }
                else {
                    closeClientSocket(socket);
                }
            }
        } catch (IOException e) {
            closeSocket();
            System.out.println(e.getMessage());
        }
    }
    private void closeClientSocket(Socket socket){
        {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("Error. Client with name " + name + " already exists.");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                socket.close();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
    public void closeSocket(){
        try {
            if (SERVER_SOCKET != null) SERVER_SOCKET.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(1200);) {
            Server server = new Server(serverSocket);
            server.runServer();
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
