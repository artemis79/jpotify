package Network;

import Logic.Person;
import Logic.SharedPlaylist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;



///akharin chizi ke goosh dade ya alan dare goosh mikone???




public class Client {

    Person user;
    Socket clientSocket;
    Scanner socketIn;
    Formatter socketOut;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Client(Person user, Socket clientSocket) throws IOException {
        this.user = user;
        this.clientSocket = clientSocket;
        socketIn=new Scanner(clientSocket.getInputStream());
        socketOut=new Formatter(clientSocket.getOutputStream());
        in=new ObjectInputStream(clientSocket.getInputStream());
        out=new ObjectOutputStream(clientSocket.getOutputStream());
    }


    public void sendStatus(){ //online hast ya age nist akharin barike online boode key boode//

        String status=user.getStatus();
        socketOut.format("%s\n",status);
        socketOut.flush();


    }

    public void sendSharedPlaylist() throws IOException {

        SharedPlaylist sharedPlaylist=user.getSharedPlaylist();
        out.writeObject(sharedPlaylist);

    }

    public void sendUserInformation(){

        String name=user.getUserName();
        socketOut.format("%s\n",name);
        socketOut.flush();
        System.out.println("Send");


    }


}
