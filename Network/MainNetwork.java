package Network;

import Logic.Person;
import NetworkGraphics.NetworkMainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainNetwork {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6500);
            Server t = new Server(serverSocket);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JFrame myFrame = new JFrame("network");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());
        //myFrame.setBackground(Color.PINK);
        //myFrame.setForeground(Color.PINK);
        myFrame.setSize(1000, 900);
        NetworkMainPanel networkMainPanel = new NetworkMainPanel();
        myFrame.add(networkMainPanel,BorderLayout.EAST);
        myFrame.setVisible(true);


    }
}
