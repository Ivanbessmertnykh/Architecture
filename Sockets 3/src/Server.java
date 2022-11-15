import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;

public class Server {

    //здесь хранится словарь с информацией о клиентах
    //ключ - строка (имя клиента), значение - сокет
    private static Map<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        //запускаем сервер на порту 8080
        try (ServerSocket listener = new ServerSocket(8080)) {
            System.out.println("Server is ready...");

            //создаем класс таймер (для рассылки сообщений клиентам раз в 5 секунд (последний параметр)
            new Timer().schedule(new MessageSender.MessageDistribute(), 0, 5000);

            //Определяем количество потоков - здесь 20
            var pool = Executors.newFixedThreadPool(20);
            while (true) {

                //На каждого клиента создается новый поток
                //Здесь мы получаем подключение клиента и обрабатываем его в выделенном для него потоке
                //Обработка действий клиента происходит в методе run() внутри класса MessageSender
                pool.execute(new MessageSender(listener.accept()));
            }
        }
    }

    //класс, отвечающий за обработку действий клиента
    private static class MessageSender implements Runnable {

        private Socket clientSocket; //сокет(а-ля класс соединения сервера и клиента) клиента
        private BufferedReader in; //поток ввода клиента
        private PrintWriter out; //поток вывода клиента
        private String clientName;

        public MessageSender(Socket clientSocket) {
            try {
                this.clientSocket = clientSocket;
                in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                out = new PrintWriter(this.clientSocket.getOutputStream(), true);
                out.println("Enter your name: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {

                //флажок определяет первый раз ли пользователь пишет сообщение
                boolean isFistTime = true;

                String clientMessage;

                while (in != null && (clientMessage = in.readLine()) != null) {
                    if (clientMessage.equals("exit")) {
                        clientLeavesServer(clientName); //метод для выхода
                        break;
                    }

                    if (isFistTime) {
                        isFistTime = false;

                        //при подключении клиент вводит своё имя
                        clientName = clientMessage;

                        //сохраняем в словарь имя клиента и его сокет
                        clients.put(clientName, clientSocket);

                        //метод для отправки сообщения
                        sendMessage("User ".concat(clientName)
                                .concat(" connected to server successfully"));
                    } else {

                        //добавляем сообщение в список сообщений, которые потом будут раз в 5 секунд рассылаться
                        MessageDistribute.addMessage("User ".concat(clientName)
                                .concat(" sent following message: ")
                                .concat(clientMessage));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void sendMessage(String message) {
            try {

                //перебираем всех клиентов
                for (Map.Entry<String, Socket> client : clients.entrySet()) {

                    //достаем для каждого клиента его поток вывода и записываем туда сообщение
                    PrintWriter out = new PrintWriter(client.getValue().getOutputStream(), true);
                    out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clientLeavesServer(String username) {

            //убираем клиента из словаря клиентов
            clients.remove(username);

            sendMessage("User ".concat(username)
                    .concat(" leaves the server"));
        }

        //класс таймер для отправки сообщений раз в 5 секунд
        //в принципе можно было бы вынести в отдельный публичный класс, но сокроем логику
        private static class MessageDistribute extends TimerTask {

            private static List<String> messages; //список сообщений

            public MessageDistribute() {
                messages = new ArrayList<>();
            }

            public static void addMessage(String message) {
                messages.add(message); //добавляем сообщение в список
            }

            @Override
            public void run() {
                if (!messages.isEmpty()) {
                    for (String message : messages) {

                        //достаем каждое сообщение из списка и отправляем клиентам
                        sendMessage(message);
                    }
                    messages = new ArrayList<>(); //как только все сообщения отправились - очищаем список
                }
            }
        }
    }
}