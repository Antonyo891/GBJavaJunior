package org.example.lessonFive.lection.example;
/*
1. Уметь подключаться к серверу (установка соединения, указание адреса и порта для соединения)
2. Параллельная обработка сообщений (принамать и отправлять сообщения параллельно )
3. Обработка множественных сообщений (сообщения от пользователй могут приходить в процессе ввода сообщения самим)
4. Корректное закрытие ресурсов
5. Простота использования
* */

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientOne {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            try (Socket clientOne = new Socket(inetAddress,1100)) {
                System.out.println(clientOne.getInetAddress());
                System.out.println(clientOne.getLocalPort());

                InputStream inputStream = clientOne.getInputStream();
                OutputStream outputStream = clientOne.getOutputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                PrintStream  printStream = new PrintStream(outputStream);
                printStream.println("Hi to all!");
                System.out.println(dataInputStream.readLine());
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
