import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер запущен, ожидаем соединение....");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился к серверу!");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            while (true) {
                String clientRequest = dataInputStream.readUTF();
                if (clientRequest.equals("end")) break;
                System.out.println("Мы получили строку: " + clientRequest);
                Mather mather = new Mather("(" + clientRequest + ")");
                dataOutputStream.writeUTF("Отвечаем на сообщение: " + clientRequest);
                dataOutputStream.writeUTF("Результат выражения в помощью класса Mather = " + mather.calculate());
                dataOutputStream.writeUTF("Результат выражения с помощью класса EvaluateExpression = "
                        + mather.calculate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
