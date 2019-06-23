package Logic;


import NetworkGraphics.NetworkPart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


//finished! write this user on socket and read all other users from socket//
public class Client extends Thread {

    Person user;
    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    ArrayList<Person> otherUsers=new ArrayList<>();

    public Client(Person user, Socket clientSocket) throws IOException {

        this.user = user;
        this.clientSocket = clientSocket;
        out=new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(user);

    }


    @Override
    public void run() {

            try {

                in=new ObjectInputStream(clientSocket.getInputStream());


            } catch (IOException e) {
                e.printStackTrace();
            }


            while (true){

                try {
                    otherUsers.add((Person)in.readObject());
                   // System.out.println("Other users is not null");
                    //show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }



    }


    public void show(){

        for (Person p:otherUsers) {

            System.out.println(p.getUserName());
        }
    }


    public static void main(String[] args) throws IOException {

        Scanner scanner=new Scanner(System.in);
        Person p =new Person(scanner.next());
        Socket clientSocket=new Socket("localhost",8765);
        Client client=new Client(p,clientSocket);
        client.start();
       // client.show();

    }

}
