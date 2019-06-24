package Logic;


import NetworkGraphics.NetworkMainPanel;
import NetworkGraphics.NetworkPart;
import NetworkGraphics.PersonGUI;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
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
    public boolean equals(Object o) {

        if(o instanceof Client  ){

            Client c=(Client)o;

            if(c.getUser().getUserName().equals(this.user.getUserName()))

                return true;
        }

        return false;
    }

    public Person getUser() {
        return user;
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

    public ArrayList<Person> getOtherUsers() {
        return otherUsers;
    }

    public static void main(String[] args) throws IOException, JavaLayerException {

        ArrayList<Client> clientsArray=new ArrayList<>();
        ArrayList<Person> persons=new ArrayList<>();
       // ArrayList<JFrame> allFrames=new ArrayList<>();
        Scanner scanner=new Scanner(System.in);
        Person p =new Person(scanner.next());
       //for test
        //Song s1=new Song("C:\\Users\\Kiana\\Music\\Jpotify\\01 Diamonds.mp3");
        //Song s2=new Song("C:\\Users\\Kiana\\Music\\Jpotify\\9624.mp3");
       // p.addSongToSharedPlaylist(s1);
       // p.addSongToSharedPlaylist(s2);
        //end test
        Socket clientSocket=new Socket("localhost",6500);
        Client client=new Client(p,clientSocket);
        client.start();
        clientsArray.add(client);

        for (Client c:clientsArray) {

            if(!(c.getOtherUsers().contains(client.user))) {
                c.getOtherUsers().add(client.user);
            }

        }

        for (Client c:clientsArray) {

            persons.add(c.getUser());

        }

        for (Person person :persons) {

            PersonGUI personGUI=new PersonGUI(person);
            NetworkMainPanel.getClientPersonsGUI().addElement(personGUI);
        }

        //SwingUtilities.updateComponentTreeUI(NetworkMainPanel.getjList());

        //NetworkMainPanel.updateList();



    }

}
