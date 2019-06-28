package NewIdea;

import Logic.Client;
import Logic.Person;
import Logic.Song;
import NetworkGraphics.PersonGUI;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class MainFrame extends JFrame {

    private static NetworkMainPanel2 networkPanel=new NetworkMainPanel2();
   private static JPanel jPanel=new JPanel();
    private Client thisUser;

    public MainFrame() throws IOException, JavaLayerException {

        super();
        Person p=new Person("this User");
        p.getSharedPlaylistSongsName().add("Song 1");
        p.getSharedPlaylistSongsName().add("Song 2");
        Socket clientSocket=new Socket("localhost",6500);
        thisUser=new Client(p,clientSocket);
        thisUser.start();
        //networkPanel.setSize(new Dimension());
        jPanel.setSize(new Dimension(800,800));
       // jPanel.setVisible(true);
        this.setSize(new Dimension(1500,800));
        this.setLayout(new BorderLayout());
        this.add(networkPanel,BorderLayout.EAST);
        //this.add(jPanel,BorderLayout.CENTER);
        this.setVisible(true);

    }

    public static void setjPanel(JPanel jPanel) {
        MainFrame.jPanel = jPanel;
        //this.add(jPanel,BorderLayout.CENTER);
    }

    public static NetworkMainPanel2 getNetworkPanel() {
        return networkPanel;
    }

  /*  public void addOtherUsersToNetworkPanel(){

        for (Person p:thisUser.getOtherUsers()) {

            PersonGUI personGUI=new PersonGUI(p);
            networkPanel.addPersonGUItoList(personGUI);

        }
    }*/

}
