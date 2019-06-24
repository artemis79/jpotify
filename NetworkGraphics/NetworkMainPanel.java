package NetworkGraphics;

import Logic.Client;
import Logic.Person;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;


public class NetworkMainPanel extends JPanel  {

    static JList jList;
    static DefaultListModel<PersonGUI> clientPersonsGUI=new DefaultListModel<>();

    public NetworkMainPanel() {
        super();
        Person p=new Person("me");
        PersonGUI personGUI1=new PersonGUI(p);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800,850));
        this.setOpaque(true);
        this.setBackground(Color.green);
        jList=new JList();
        jList.setSize(new Dimension(400,600));
        clientPersonsGUI.addElement(personGUI1);
        makeUseresIcon();
        jList.setVisible(true);
        jList.setBackground(Color.lightGray);
        this.add(jList,BorderLayout.EAST);

        this.setVisible(true);
    }

    public void makeUseresIcon() {
        //System.out.println("makeIcons");
        System.out.println(clientPersonsGUI.size());

        for (int i=1 ; i<clientPersonsGUI.size() ; i++) {

            jList.add(clientPersonsGUI.elementAt(i));
        }

    }

    public static DefaultListModel<PersonGUI> getClientPersonsGUI() {
        return clientPersonsGUI;
    }

    public static void updateList(){

        jList.invalidate();
        jList.validate();
        jList.repaint();
    }

    public static JList getjList() {
        return jList;
    }
}
