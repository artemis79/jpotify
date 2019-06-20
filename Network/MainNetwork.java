package Network;

import Logic.Person;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainNetwork {

    public static void main(String[] args) throws IOException {

        Person person = new Person("Kianahs");
        Socket clientSocket=new Socket("localhost",8765);
        Client client=new Client(person,clientSocket);

        ServerSocket serverSocket=new ServerSocket(8765);
        Server server=new Server(serverSocket);

        client.sendUserInformation();
        server.getUserInformation();


    }
}
