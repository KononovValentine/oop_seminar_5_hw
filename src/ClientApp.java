import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 1234)) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                System.out.println("Введите выражение: ");
                String request = scanner.nextLine();
                dataOutputStream.writeUTF(request);
                if (request.equals("end")) break;
                System.out.println("Получили сообщение от сервера: " + dataInputStream.readUTF());
                System.out.println("Получили сообщение от сервера: " + dataInputStream.readUTF());
                System.out.println("Получили сообщение от сервера: " + dataInputStream.readUTF());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
