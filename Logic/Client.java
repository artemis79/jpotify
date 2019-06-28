package Logic;


import NetworkGraphics.PersonGUI;
import NewIdea.MainFrame;
import NewIdea.NetworkMainPanel2;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.UnsupportedAudioFileException;
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
    private ArrayList<Person> otherUsers=new ArrayList<>();


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

        System.out.println("run method");
        //MainFrame.networkPanel.add(personGUI);
        //MainFrame.networkPanel.addPersonGUItoList(personGUI);

            try {

                in=new ObjectInputStream(clientSocket.getInputStream());


            } catch (IOException e) {
                e.printStackTrace();
            }


            while (true){

                try {
                    System.out.println("in while true");
                    Person person=(Person)in.readObject();
                    otherUsers.add(person);
                    PersonGUI personGUI=new PersonGUI(person);
                    NetworkMainPanel2 networkMainPanel2=MainFrame.getNetworkPanel();
                    networkMainPanel2.getClientPersonsGUI().addElement(personGUI);
                    System.out.println("Model size="+networkMainPanel2.getClientPersonsGUI().size());
                    networkMainPanel2.addPersonGUItoList(personGUI);
                   // System.out.println("num");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
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

        Client client=new Client(p,clientSocket);
        client.start();





    }

}
