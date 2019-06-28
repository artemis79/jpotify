package NetworkGraphics;

import Logic.Person;
import Logic.SharedPlaylist;
import Logic.Song;
import NewIdea.MainFrame;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class PersonGUI extends JPanel {

    private Person user;
    private JButton nameButton;
    private JPanel userPanel;
    private JLabel nameUP;
    private JLabel status;
    private JLabel isPlayingSongName;
    private JButton savePlaylsit;
    private JButton play;
    private JList sharedPlaylistJList;
    private ArrayList<Song> sharedSongs;
    private ArrayList<Song> savedSongs;     ///ehtebahe

    public PersonGUI (Person p) throws UnsupportedAudioFileException, IOException, JavaLayerException {

        super();
        user=p;
        sharedSongs=p.convetBytesToMp3Songs();
        Container container=new Container();
        container.setLayout(new FlowLayout());
        isPlayingSongName=new JLabel("    is Playing Song   ");
        status=new JLabel("   Online  ");
       // status=new JLabel(p.getStatus());
        //isPlayingSongName=new JLabel("  "+p.getIsPlayingSong().getSongName()+"  ");
        isPlayingSongName.setSize(200,25);
        isPlayingSongName.setForeground(Color.LIGHT_GRAY);
        status.setForeground(Color.green);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(200,100));
        nameButton=new JButton(user.getUserName());
        nameButton.setSize(new Dimension(200,25));
        nameButton.setBackground(Color.GRAY);
        nameButton.setForeground(Color.lightGray);
        this.add(nameButton,BorderLayout.NORTH);
        this.add(status,BorderLayout.CENTER);
        this.add(isPlayingSongName,BorderLayout.AFTER_LAST_LINE);
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        play=new JButton("Play");
        play.setSize(new Dimension(100,50));
        play.setForeground(Color.WHITE);
        play.setBackground(Color.green);
        savePlaylsit=new JButton("Save");
        savePlaylsit.setSize(new Dimension(200,50));
        savePlaylsit.setBackground(Color.green);
        savePlaylsit.setForeground(Color.WHITE);
        container.add(play);
        container.add(savePlaylsit);
        userPanel=new JPanel();
        userPanel.setSize(new Dimension(800,800));
        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(Color.white);
        sharedPlaylistJList=new JList(user.getSharedPlaylistSongsName().toArray());
        sharedPlaylistJList.setBackground(Color.gray);
        sharedPlaylistJList.setForeground(Color.lightGray);
        userPanel.add(sharedPlaylistJList,BorderLayout.SOUTH);
        nameUP=new JLabel(user.getUserName());
        nameUP.setForeground(Color.WHITE);
        nameUP.setFont(new Font(user.getUserName(),Font.PLAIN,30));
        userPanel.add(nameUP,BorderLayout.NORTH);
        userPanel.setBackground(Color.gray);
        userPanel.setForeground(Color.WHITE);
        userPanel.add(container,BorderLayout.CENTER);

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // MainFrame.setjPanel(userPanel);

                JFrame myFrame=new JFrame("userPanel");
                myFrame.setSize(new Dimension(800,800));
                myFrame.add(userPanel);
                myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                myFrame.setVisible(true);
            }
        });

        savePlaylsit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                savedSongs=sharedSongs;

            }
        });



        sharedPlaylistJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {


                for (Song s:sharedSongs) {

                    if (sharedPlaylistJList.getSelectedValue().equals(s.getSongName())) {
                        try {
                            s.play();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }

                    return;
                }


            }
        });

    }

    public JPanel getUserPanel() {
        return userPanel;

    }
}
