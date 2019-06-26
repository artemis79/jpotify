package Logic;


import NetworkGraphics.PersonGUI;
import NewIdea.MainFrame;
import NewIdea.NetworkMainPanel2;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


//finished! write this user on socket and read all other users from socket//
public class Client extends Thread {

    Person user;
    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    ArrayList<Person> otherUsers=new ArrayList<>();
    /*static*/ NetworkMainPanel2 networkPanel;

    public Client(Person user, Socket clientSocket,NetworkMainPanel2 networkPanel) throws IOException {

        this.networkPanel=networkPanel;
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

        PersonGUI personGUI=new PersonGUI(user);
        networkPanel.addPersonGUItoList(personGUI);

            try {

                in=new ObjectInputStream(clientSocket.getInputStream());


            } catch (IOException e) {
                e.printStackTrace();
            }


            while (true){

                try {
                    otherUsers.add((Person)in.readObject());
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

        Scanner scanner=new Scanner(System.in);
        Person p =new Person(scanner.next());

        Socket clientSocket=new Socket("localhost",6500);

        Client client=new Client(p,clientSocket,MainFrame.networkPanel);
        client.start();




    }

}
