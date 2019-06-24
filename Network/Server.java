package Network;

import Logic.Person;
import Logic.SharedPlaylist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Server extends Thread {

    ServerSocket serverSocket;
    static ArrayList<ObjectOutputStream> outputStreams=new ArrayList<>();
    ArrayList<ObjectInputStream> inputStreams=new ArrayList<>();
    ArrayList<Socket> clients=new ArrayList<>();
    private ObjectOutputStream out;


   public Server(ServerSocket serverSocket ) throws IOException {

       this.serverSocket=serverSocket;

   }



    @Override
    public void run(){

        while (true) {

            Socket client = null;

            try {

                client = serverSocket.accept();
                System.out.println("new client accepted!");
                clients.add(client);
                out = new ObjectOutputStream(client.getOutputStream());

                if (ClientHandler.users!= null) {

                    for (Person user:ClientHandler.users) {

                        Person p=user;
                        out.writeObject(p);
                    }
                }

                outputStreams.add(out);

                ClientHandler clientHandler = new ClientHandler(client,out);
                clientHandler.start();

            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}

