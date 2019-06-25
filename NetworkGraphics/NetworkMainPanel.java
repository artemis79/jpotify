package NetworkGraphics;

import Logic.Client;
import Logic.Person;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;


public class NetworkMainPanel extends JPanel  {

    static DefaultListModel<PersonGUI> clientPersonsGUI=new DefaultListModel<>();
    static JPanel allUsersPanel;

    public NetworkMainPanel() {
        super();
        Person p=new Person("me");
        PersonGUI personGUI1=new PersonGUI(p);
        clientPersonsGUI.addElement(personGUI1);
        this.setSize(new Dimension(300,900));
        this.setOpaque(true);
        allUsersPanel=new JPanel();
        makeUseresIcon();
        allUsersPanel.setOpaque(true);
        allUsersPanel.setBackground(Color.PINK);
        allUsersPanel.setForeground(Color.GRAY);
        allUsersPanel.setVisible(true);
        this.add(allUsersPanel);
        this.setVisible(true);
    }

    public void makeUseresIcon() {

        allUsersPanel.setLayout(new GridLayout());
        allUsersPanel.setSize(new Dimension(300,900));

        for(int i=0 ; i<clientPersonsGUI.size() ; i++){

            allUsersPanel.add(clientPersonsGUI.elementAt(i));
        }

    }

    public static DefaultListModel<PersonGUI> getClientPersonsGUI() {
        return clientPersonsGUI;
    }

    public static void updateList(){

        allUsersPanel.invalidate();
        allUsersPanel.validate();
        allUsersPanel.repaint();
    }



}
