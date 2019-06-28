package NewIdea;

import Logic.Client;
import Logic.Person;
import NetworkGraphics.PersonGUI;

import javax.swing.*;
import java.awt.*;

public class NetworkMainPanel2 extends JPanel {

    private static DefaultListModel<PersonGUI> clientPersonsGUI=new DefaultListModel<>();




    public NetworkMainPanel2(){

        super();
        GridLayout gridLayout=new GridLayout(10,1,10,10);
        this.setLayout(gridLayout);
        this.setSize(new Dimension(500,800));
        this.setBackground(Color.gray);
        this.setVisible(true);

    }

    public  DefaultListModel<PersonGUI> getClientPersonsGUI() {
        return clientPersonsGUI;
    }

    public void addPersonGUItoList(PersonGUI p){

        clientPersonsGUI.addElement(p);
        this.add(p);
        SwingUtilities.updateComponentTreeUI(this);
        this.invalidate();
        this.validate();
        this.repaint();
    }



}
