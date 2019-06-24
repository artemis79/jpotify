package NetworkGraphics;

import Logic.Person;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame myFrame=  new JFrame("network");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(900,900);
        NetworkMainPanel networkMainPanel=new NetworkMainPanel();
        myFrame.add(networkMainPanel,BorderLayout.EAST);
        myFrame.setVisible(true);
    }
}

