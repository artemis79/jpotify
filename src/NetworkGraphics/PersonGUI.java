package NetworkGraphics;

import Logic.Person;
import Logic.SharedPlaylist;
import Logic.Song;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PersonGUI extends JPanel {

    private Person user;
    private JButton nameButton;
    private JPanel userPanel;
    private JLabel nameUP;
    private JPanel sharedPlaylistPanel;
    private SharedPlaylist sharedPlaylist;
    private ArrayList<Song> playlistSongs;
    private ArrayList<JButton> songButton;
    public PersonGUI (Person p){

        super();
        user=p;
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(400,400));
        nameButton=new JButton(user.getUserName());
        nameButton.setBackground(Color.GRAY);
        this.add(nameButton,BorderLayout.NORTH);
        this.setForeground(Color.DARK_GRAY);
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        userPanel=new JPanel();
        userPanel.setLayout(new BorderLayout());
        nameUP=new JLabel(user.getUserName());
        userPanel.add(nameUP,BorderLayout.NORTH);
        nameUP.setVisible(true);
        sharedPlaylistPanel=new JPanel();
        sharedPlaylist=user.getSharedPlaylist();
        playlistSongs=sharedPlaylist.getPlaylistSongs();
        sharedPlaylistPanel.setLayout(new GridLayout(playlistSongs.size(),1));

        for (Song s:playlistSongs) {

            JButton button=new JButton(s.getSongName());
            sharedPlaylistPanel.add(button);


        }

        userPanel.add(sharedPlaylistPanel,BorderLayout.SOUTH);
        sharedPlaylistPanel.setVisible(true);

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.setVisible(true);
                JFrame myFrame=new JFrame("userPanel");
                myFrame.add(userPanel);
                myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myFrame.setVisible(true);
            }
        });

    }

    public JPanel getUserPanel() {
        return userPanel;
    }
}
