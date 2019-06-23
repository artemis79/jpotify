package NetworkGraphics;

import Logic.Person;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Frame myFrame=new Frame("network");
        myFrame.setSize(400,850);
        NetworkPart networkPart=new NetworkPart();
        networkPart.makeUseres();
        myFrame.add(networkPart);
        myFrame.setVisible(true);
    }
}

