package Network;

import Logic.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    Person user;
    Socket Socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    private String userName;
    static ArrayList<Person> users= new ArrayList<>();


    public ClientHandler(Socket client, ObjectOutputStream Out) throws Exception {

        if (client == null) throw new Exception("client can't be null");

            this.Socket = client;
            this.out=Out;
            in = new ObjectInputStream(client.getInputStream());
            System.out.println("wait for client to send its person!");
            Person addPerson = (Person)in.readObject();
            userName=addPerson.getUserName();////ezafi///
            System.out.println("client sent its username!");

        for (ObjectOutputStream outstream:Server.outputStreams) {

            System.out.println("send user to clients!");
            outstream.writeObject(addPerson);
        }
        users.add(addPerson);
        System.out.println("end constructor!");
    }

    public void run () {

        try {
            while (!Socket.isClosed()) {

                Person p=(Person) in.readObject();
                for (ObjectOutputStream outStream : Server.outputStreams) {
                    System.out.println("Sending person to users");
                    outStream.writeObject(p);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {

            Person p=new Person(userName);
            users.remove(userName);
            Server.outputStreams.remove(out);
            System.out.println("User " + userName+" and Streamer removed");
            for (ObjectOutputStream out:Server.outputStreams)
            {
                out.writeObject(p);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

