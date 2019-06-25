package JpotifyGraphics;

import Logic.*;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryFrame extends JPanel {

    private Library library;
    private PlayListFrame playListFrame;
    private HomeFrame homeFrame;
    private ArrayList<Album> albums;
    private ArrayList<Song> songs;
    private CenterFrame centerFrame;


    public LibraryFrame (){
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(150 , 50));
        this.setLayout(new BorderLayout());
        this.playListFrame = new PlayListFrame();
        this.homeFrame = new HomeFrame();
        this.add (homeFrame , BorderLayout.NORTH);
        this.add (playListFrame , BorderLayout.CENTER);
        this.setVisible(true);

    }

    public JButton getButtonAlbum (){
        return homeFrame.getButtonAlbum();
    }

    public JButton getButtonSongs (){
        return homeFrame.getButtonSongs ();
    }



    public Library getLibrary (){
        return library;
    }

    public CenterFrame getCenterFrame (int type) throws IOException {
        centerFrame = new CenterFrame(library , type);
        return centerFrame;
    }


    private class HomeFrame extends JPanel {
        private JButton buttonLibrary;
        private JButton buttonAlbum;
        private JButton buttonSongs;
        private JLabel label;
        private final String HOME_LABEL = "Home";

        public HomeFrame (){
            super();
            this.setOpaque(true);
            this.setBackground(Color.darkGray);
            this.buttonLibrary = new JButton(" Library ");
            this.buttonAlbum = new JButton("  Albums ");
            this.buttonSongs = new JButton("  Songs  ");
            this.label = new JLabel(HOME_LABEL);
            label.setFont(new Font(label.getName(), Font.PLAIN, 20));
            label.setForeground(Color.LIGHT_GRAY);
            buttonLibrary.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    fileChooser.setOpaque(true);
                    fileChooser.setBackground(Color.LIGHT_GRAY);
                    fileChooser.setForeground(Color.LIGHT_GRAY);
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

            this.setLayout(new GridLayout(4 , 1));
            this.add (label);
            this.add (buttonLibrary);
            this.add (buttonAlbum);
            this.add (buttonSongs);
        }

        public JButton getButtonAlbum (){
            return buttonAlbum;
        }

        public JButton getButtonSongs () { return buttonSongs;}

    }

    public class PlayListFrame extends JPanel {

        private ArrayList<Playlist> playlists;
        private JLabel labelPlayList;
        private final String LABEL = "PlayLists";
        private JButton buttonAdd;
        private final String LABEL_ADD = "Add PlayList";
        private DefaultListModel<String> playlistNames;
        private JList<String> list;


        public PlayListFrame (){
            super();
            this.setOpaque(true);
            this.setBackground(Color.darkGray);
            JScrollPane scrollPane = new JScrollPane();
            playlists = new ArrayList<Playlist>();
            playlistNames = new DefaultListModel<String>();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            Container container = new Container();
            this.setLayout(new BorderLayout());
            this.setOpaque(true);
            container.setBackground(Color.darkGray);
            container.setPreferredSize(new Dimension(130 , 550));
            labelPlayList = new JLabel(LABEL);
            labelPlayList.setForeground(Color.LIGHT_GRAY);
            labelPlayList.setBackground(Color.darkGray);
            labelPlayList.setFont(new Font(labelPlayList.getName(), Font.PLAIN, 25));
            buttonAdd = new JButton(LABEL_ADD);
            list = new JList<String>(playlistNames);
            list.setBackground(Color.darkGray);
            list.setForeground(Color.LIGHT_GRAY);
            buttonAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    JTextField textField = new JTextField();
                    JButton button = new JButton("Add");
                    button.setForeground(Color.LIGHT_GRAY);
                    button.setBackground(Color.darkGray);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String playListName = textField.getText();
                            Playlist playlist = new Playlist(playListName);
                            playlists.add (playlist);
                            playlistNames.addElement(playListName);
                            scrollPane.setViewportView(list);
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    });
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setPreferredSize(new Dimension(150 , 150));
                    frame.setLayout(new BorderLayout());
                    frame.add (button , BorderLayout.LINE_END);
                    frame.add (textField , BorderLayout.CENTER);
                    frame.setVisible(true);
                }
            });
            this.setLayout(new BorderLayout());
            this.add (labelPlayList , BorderLayout.NORTH);
            this.add (buttonAdd , BorderLayout.SOUTH);
            container.add (list , BorderLayout.CENTER);
            scrollPane.add (container);
            scrollPane.getViewport().setBackground(Color.darkGray);
            this.add (scrollPane, BorderLayout.CENTER);
            scrollPane.setViewportView(container);
        }
    }

}
