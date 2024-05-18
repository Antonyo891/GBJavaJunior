package org.example.lessonFive.lection.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    private Boolean active;
    private final String EXIT;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;
        this.EXIT = "Server: @" + name + " you are disconnect.";
        active = true;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedReader.close();
            }
            if (socket!=null){
                socket.close();
            }
            active = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void sendMessage(){
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (active){
                String message = scanner.nextLine();
                bufferedWriter.write(name + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void  listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroup;
                while (active){
                    try {
                        messageFromGroup = bufferedReader.readLine();
                        if ((messageFromGroup.contains("Error"))||(messageFromGroup.equals(EXIT))){
                            closeEverything(socket,bufferedReader,bufferedWriter);
                        }
                        System.out.println(messageFromGroup);
                    } catch (IOException e) {
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }
}
