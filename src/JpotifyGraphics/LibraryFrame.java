package JpotifyGraphics;

import Logic.*;
import javafx.print.PageLayout;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class LibraryFrame extends JPanel {

    private Library library;
    private PlayListFrame playListFrame;
    private HomeFrame homeFrame;
    private ArrayList<Album> albums;
    private ArrayList<Song> songs;
    private CenterFrame centerFrame;
    private static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    private final String SAVE_LIBRARY = "save.bin";


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

    public JList<String> getList (){
        return homeFrame.getButtonPlaylist();
    }

    public static ArrayList<Playlist> getPlaylists (){
        return playlists;
    }


    public Library getLibrary (){
        return library;
    }

    public CenterFrame getCenterFrame (int type , Playlist playlist) throws IOException {
        if (playlist == null)
            centerFrame = new CenterFrame(library  , type);
        else
            centerFrame = new CenterFrame(playlist , type);
        return centerFrame;
    }

    public void setLibrary (Library library){
        this.library = library;
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
                            File file = new File (SAVE_LIBRARY);
                            if (file.exists())
                                file.delete();
                            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(SAVE_LIBRARY));
                            dataOutputStream.writeUTF(selectedFile.getAbsolutePath());
                            dataOutputStream.close();

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

        public JList<String> getButtonPlaylist (){
            return playListFrame.getList();
        }

    }

    public class PlayListFrame extends JPanel {

        private ArrayList<Playlist> playlists;
        private JLabel labelPlaylist;;

        private final String LABEL = "PlayLists";
        private JButton buttonAdd;
        private final String LABEL_ADD = "Add Playlist";
        private JButton buttondelete;
        private final String LABEL_DELETE = "Delete Playlist";
        private DefaultListModel<String> playlistNames;
        private JList<String> list;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private final String SAVE_PLAYLIST = "playlists.bin";


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
            labelPlaylist = new JLabel(LABEL);
            labelPlaylist.setForeground(Color.LIGHT_GRAY);
            labelPlaylist.setBackground(Color.darkGray);
            labelPlaylist.setFont(new Font(labelPlaylist.getName(), Font.PLAIN, 25));
            buttonAdd = new JButton(LABEL_ADD);
            buttondelete = new JButton(LABEL_DELETE);
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
                            LibraryFrame.playlists.add(playlist);
                            playlistNames.addElement(playListName);
                            scrollPane.setViewportView(list);
                            try {
                                dataOutputStream.writeUTF(playListName);
                                System.out.println("yes they were written");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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
            buttondelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Delete Playlist");
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for (int i = 0; i < playlists.size(); i++){
                        if (!playlists.get(i).getPlaylistName().equals("Favourite Songs") && !playlists.get(i).getPlaylistName().equals("Shared Playlist")) {
                            model.addElement(playlists.get(i).getPlaylistName());
                        }
                    }
                    JList<String> list = new JList<String>(model);
                    list.setBackground(Color.darkGray);
                    list.setForeground(Color.LIGHT_GRAY);
                    frame.add (list);
                    list.addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            playlistNames.removeElement(list.getSelectedValue());
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    });
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
            this.setLayout(new BorderLayout());
            this.add (labelPlaylist , BorderLayout.NORTH);
            Container tmpContainer = new Container();
            tmpContainer.setLayout(new GridLayout(2 , 1));
            tmpContainer.add(buttonAdd);
            tmpContainer.add(buttondelete);
            tmpContainer.setVisible(true);
            this.add (tmpContainer , BorderLayout.SOUTH);
            container.add (list , BorderLayout.CENTER);
            scrollPane.add (container);
            scrollPane.getViewport().setBackground(Color.darkGray);
            this.add (scrollPane, BorderLayout.CENTER);
            scrollPane.setViewportView(container);

            Playlist favourites = new Playlist("Favourite Songs");
            playlistNames.addElement(favourites.getPlaylistName());
            playlists.add(favourites);
            Playlist sharedPlaylist = new Playlist("Shared Playlist");
            playlistNames.addElement(sharedPlaylist.getPlaylistName());
            scrollPane.setViewportView(list);
            LibraryFrame.playlists.add(favourites);
            LibraryFrame.playlists.add(sharedPlaylist);
            File savePlaylists = new File (SAVE_PLAYLIST);
            try {
                dataOutputStream = new DataOutputStream(new FileOutputStream(SAVE_PLAYLIST));
                dataOutputStream.writeUTF(favourites.getPlaylistName());
                dataOutputStream.writeUTF(sharedPlaylist.getPlaylistName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public JList<String> getList (){
            return list;
        }

        private void getPlaylistsFromFile (){
            try {
                dataInputStream = new DataInputStream(new FileInputStream(SAVE_PLAYLIST));
                while (true){
                    String playlistName = dataInputStream.readUTF();
                    File file = new File (playlistName + ".bin");
                    if (file.exists()){
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
                        Song song;
                        Playlist playlist = new Playlist(playlistName);
                        while (true){
                            try {
                                song = (Song) objectInputStream.readObject();
                            }catch (EOFException e){
                                e.printStackTrace();
                                break;
                            }
                            playlist.addSongToPlaylist(song);
                        }
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
