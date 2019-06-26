package NewIdea;

import Logic.Client;
import Logic.Person;
import NetworkGraphics.PersonGUI;

import javax.swing.*;
import java.awt.*;

public class NetworkMainPanel2 extends JPanel {

    private DefaultListModel<PersonGUI> clientPersonsGUI=new DefaultListModel<>();
    private Client client;
   // private MainFrame mainFrame;



    public NetworkMainPanel2(){

        super();
        //this.mainFrame;
        this.setSize(new Dimension(300,800));
        this.setLayout(new GridLayout(20,1));
        this.setBackground(Color.white);
        this.setForeground(Color.blue);
        this.setVisible(true);
        //
        Person user=new Person("me");
        PersonGUI personGUI=new PersonGUI(user);
        this.add(personGUI);
        //

    }


    public void addPersonGUItoList(PersonGUI p){

        clientPersonsGUI.addElement(p);
        this.add(p);
        SwingUtilities.updateComponentTreeUI(this);
        this.invalidate();
        this.validate();
        this.repaint();
       /* SwingUtilities.updateComponentTreeUI(mainFrame);
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();*/
    }



}
