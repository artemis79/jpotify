package Network;

import Logic.SharedPlaylist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Server {

    ServerSocket serverSocket;
    Scanner socketIn;
    Formatter socketOut;
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socket;

//field beshan status o shared palylist o user name too server ????////

   public Server(ServerSocket serverSocket ) throws IOException {

       this.serverSocket=serverSocket;
       socket=serverSocket.accept();
       socketIn=new Scanner(socket.getInputStream());
       socketOut=new Formatter(socket.getOutputStream());
       in=new ObjectInputStream(socket.getInputStream());
       out=new ObjectOutputStream(socket.getOutputStream());

   }



    public void getStatus(){

       String status=socketIn.next();
        System.out.println("recieved");

    }

    public void getUserInformation(){

       String userName=socketIn.next();

    }

    public void getSharedPlaylist() throws IOException, ClassNotFoundException {

        SharedPlaylist sharedPlaylist= (SharedPlaylist) in.readObject();

    }

}

