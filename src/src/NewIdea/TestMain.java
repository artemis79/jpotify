package NewIdea;

import javax.swing.*;
import java.awt.*;

public class TestMain {
    public static void main(String[] args) {

        JFrame frame=new JFrame();
        frame.setSize(new Dimension(1000,900));
        frame.setLayout(new BorderLayout());
        frame.add(MainFrame.getNetworkPanel(),BorderLayout.EAST);
        frame.setVisible(true);
       // System.out.println(MainFrame.networkPanel.get);
      //  System.out.println(MainFrame.getNetworkPanel().clientPersonsGUI.size());

    }
}
