package org.example.lessonFive.lection.chat;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientManager implements Runnable{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    private static ArrayList<ClientManager> clients = new ArrayList<>();
    private static Set<String> names = new HashSet<>();

//    public static ClientManager createClientManager(String name, Socket socket){
//        if (!names.contains(name)) {
//            return new ClientManager(socket, name);
//        }
//        System.out.println("Client with name " + name + " already exists.");
//        try {
//            if(socket!=null) socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public ClientManager(Socket socket,String name) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.name = name;
            names.add(name);
            clients.add(this);
            broadcastMessage("Enter '@Users_Login Text Message' for send message to the user with name Login\n" +
                    "Enter 'Text Message' for send message to all.\n" +
                    "Enter '@exit' to disconnect",name);
            broadcastMessage("Server: " + name + " is connected");
        } catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    private void broadcastMessage(String messageToSend) {
        if (messageToSend!=null){
            clients.stream().filter(c -> !c.name.equals(this.name))
                    .forEach(c -> {
                        try {
                            c.bufferedWriter.write(messageToSend);
                            c.bufferedWriter.newLine();
                            c.bufferedWriter.flush();
                        } catch (IOException e) {
                            closeEverything(socket,bufferedReader,bufferedWriter);
                        }
                    });
        }
    }
    private void broadcastMessage(String messageToSend,String nameUsersForSend) {
        clients.stream().filter(c -> c.name.equals(nameUsersForSend))
                .forEach(c -> {
                    try {
                        c.bufferedWriter.write(messageToSend);
                        c.bufferedWriter.newLine();
                        c.bufferedWriter.flush();
                    } catch (IOException e) {
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                });

    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClient();
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean checkName(String name){
        return !names.contains(name);
    }


    private void removeClient() {
        broadcastMessage("Server: " + name + " is disconnect.");
        broadcastMessage("Server: @" + name + " you are disconnect.",name);
        clients.remove(this);
        names.remove(this.name);
    }

    @Override
    public void run() {
        String messageFromClient;
        String[] strings;
        String[] loginArr;
        String login;
        StringBuilder message;
        String name;

        while (!socket.isClosed()){
            try {
                messageFromClient = bufferedReader.readLine();
                if ((messageFromClient!=null)&&(messageFromClient.contains("@exit")))
                    removeClient();
                if ((messageFromClient!=null)&&(messageFromClient.contains("@"))){
                    strings = messageFromClient.split(" ");
                    loginArr = strings[1].split("@");
                    login = loginArr[1];
                    message = new StringBuilder();
                    Arrays.stream(strings)
                            .filter(s->!s.contains("@"))
                            .map(s->s+" ")
                            .forEach(message::append);
                    broadcastMessage(message.toString(),login);
                }
                else if (messageFromClient!=null)
                    broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
}
