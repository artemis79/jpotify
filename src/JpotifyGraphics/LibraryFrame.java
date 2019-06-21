package JpotifyGraphics;

import Logic.*;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryFrame extends JPanel {

    private Library library;
    private PlayListFrame playListFrame;
    private HomeFrame homeFrame;
    private ArrayList<Album> albums;
    private ArrayList<Song> songs;


    public LibraryFrame (){
        super();
        this.setPreferredSize(new Dimension(150 , 50));
        this.setLayout(new BorderLayout());
        this.playListFrame = new PlayListFrame();
        this.homeFrame = new HomeFrame();
        this.add (homeFrame , BorderLayout.NORTH);
        this.add (playListFrame , BorderLayout.CENTER);
        this.setVisible(true);

    }

    public Library getLibrary (){
        return library;
    }

    private class HomeFrame extends JPanel {
        private JButton buttonLibrary;
        private JButton buttonAlbum;
        private JButton buttonSongs;
        private JLabel label;
        private final String HOME_LABEL = "Home";

        public HomeFrame (){
            this.buttonLibrary = new JButton(" Library ");
            this.buttonAlbum = new JButton("  Albums ");
            this.buttonSongs = new JButton("  Songs  ");
            this.label = new JLabel(HOME_LABEL);
            buttonLibrary.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnValue = fileChooser.showOpenDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        library = new Library("your Library" , "me");
                        try {
                            library.importSongsPathToLibraryFromPc(selectedFile.getAbsolutePath());
                            albums = library.getAllAlbums();
                            songs = library.getAllSongs();

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (JavaLayerException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            buttonSongs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            this.setLayout(new GridLayout(4 , 1));
            this.add (label);
            this.add (buttonLibrary);
            this.add (buttonAlbum);
            this.add (buttonSongs);
        }

    }

    public class PlayListFrame extends JPanel {

        private ArrayList<Playlist> playlists;
        private JLabel labelPlayList;
        private final String LABEL = "PlayLists";
        private JButton buttonAdd;
        private final String LABEL_ADD = "Add PlayList";
        private JList<Playlist> list;


        public PlayListFrame (){
            super();
            this.setPreferredSize(new Dimension(100 , 400));
            labelPlayList = new JLabel(LABEL);
            buttonAdd = new JButton(LABEL_ADD);
            list = new JList<Playlist>();
            this.setLayout(new BorderLayout());
            this.add (labelPlayList , BorderLayout.NORTH);
            this.add (buttonAdd , BorderLayout.SOUTH);
            this.add (list , BorderLayout.CENTER);
        }
    }
}
