package org.example.lessonFive.lection.example;

/*
1. Слушать заданный порт, в ожидании запроса на подключение
2. После получения запроса и начала формирования связи с клиентом
 должен продолжать слушать и быть готовым к новым подключениям
3. Идентифицировать участника чата по имени
4. Формировать оповещение о подключенных участниках чата
5. При подключении участника уметь оповестить других участников о подключении
6. Уметь передавать сообщения всем участникам кроме себя.
* */

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1100);
             Socket socket = serverSocket.accept()) {

            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println("Hello");
        }catch (UnknownHostException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
