package Network;

import Logic.Person;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainNetwork {

    public static void main(String[] args) {

        try
        {
            ServerSocket serverSocket=new ServerSocket(6500);
            Server t = new Server(serverSocket);
            t.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
