import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Client {

    private static PrintWriter out; //поток вывода
    private static BufferedReader in; //поток ввода

    public static void main(String[] args) throws IOException {

        //создаем соединение с сервером (хост - 127.0.0.1 и порт 8080)
        try (Socket clientSocket = new Socket("127.0.0.1", 8080)) {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);

            //создадим один поток для сервера
            var pool = Executors.newFixedThreadPool(1);
            while (true) {

                //Здесь мы получаем подключение сервера и обрабатываем его в выделенном для него потоке
                //Обработка действий сервера происходит в методе run() внутри класса MessageGetter
                pool.execute(new MessageGetter());

                //Читаем ввод клиента
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = br.readLine();

                //выводим сообщение внутрь клиента
                out.println(input);

                //если клиент захотел выйти, то закрываются все потоки ввода, вывода и закрывается сам сокет
                if (input.equals("exit")) {
                    out.close();
                    in.close();
                    clientSocket.close();
                    System.exit(0);
                }
            }
        }
    }

    //класс, отвечающий за вывод сообщений от других клиентов (передает сервер)
    private static class MessageGetter implements Runnable{

        @Override
        public void run() {
            try {
                String serverMessage;
                while(in != null && (serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
