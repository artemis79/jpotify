package JpotifyGraphics;

import Logic.Album;
import Logic.Library;
import Logic.Playlist;
import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainFrame extends JFrame {

    private final static String LABEL = "Jpotify";
    private Library library;
    private ArrayList<Playlist> playlists;
    private LibraryFrame libraryFrame;
    private static PlayerGUI playerGUI;
    private CenterFrame centerFrame;

    public MainFrame () throws IOException, JavaLayerException, InterruptedException {
        super(LABEL);
        centerFrame = new CenterFrame((Library) null, -1);
        playerGUI = new PlayerGUI();
        libraryFrame = new LibraryFrame();
        new Thread(new WaitForLibrary()).start();


        libraryFrame.getButtonAlbum().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (library != null) {
                    try {
                        remove(getCenterFrame());
                        setCenterFrame( libraryFrame.getCenterFrame(0 , null));
                        ArrayList<AlbumArtwork> artworks = centerFrame.getArtworks();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }});

        libraryFrame.getButtonSongs().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (library != null){
                    try {
                        remove(getCenterFrame());
                        setCenterFrame( libraryFrame.getCenterFrame(1 , null ));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        libraryFrame.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    JList<String> list = libraryFrame.getList();
                    String playListName = list.getSelectedValue();
                    ArrayList<Playlist> playlists = LibraryFrame.getPlaylists();
                    for (Playlist playlist : playlists){
                        if (playlist.getPlaylistName().equals(playListName)){
                            if (playlist.getPlaylistSongs().size() > 0) {
                                try {
                                    remove(getCenterFrame());
                                    setCenterFrame(libraryFrame.getCenterFrame(2, playlist));
                                    break;
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        this.add (playerGUI, BorderLayout.SOUTH);
        this.add (libraryFrame , BorderLayout.WEST);
        this.add (centerFrame , BorderLayout.CENTER);
        this.setVisible(true);


    }

    public void setCenterFrame (CenterFrame centerFrame){
        this.centerFrame = centerFrame;
        add (centerFrame , BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public CenterFrame getCenterFrame (){
        return centerFrame;
    }


    public void setPlaylists (){
        this.playlists = libraryFrame.getPlaylists();
    }

    public static void playSongFromAlbum(Album album , Song startingSong) throws InterruptedException, UnsupportedAudioFileException, IOException {
        ArrayList<Song> songs = album.getAlbumSongs();
        int i;
        for (i = 0; i < songs.size(); i++){
            if (songs.get(i).getSongName().equals(startingSong.getSongName())){
                break;
            }
        }
        playerGUI.getButtons().setAlbum(album);
        playerGUI.playSong(songs.get(i));

    }

    public static void playSongFromLibrary (Library library , Song startingSong) throws InterruptedException, UnsupportedAudioFileException, IOException {
        ArrayList<Song> songs = library.getAllSongs();
        int i;
        for (i = 0; i < songs.size(); i++){
            if (songs.get(i).getSongName().equals(startingSong.getSongName())){
                break;
            }
        }
        playerGUI.getButtons().setLibrary(library);
        playerGUI.playSong(songs.get(i));


    }

    public static void playSongFromPlaylist (Playlist playlist , Song startingSong) throws InterruptedException, UnsupportedAudioFileException, IOException {
        ArrayList<Song> songs = playlist.getPlaylistSongs();
        int i;
        for (i = 0; i < songs.size(); i++){
            if (songs.get(i).getSongName().equals(startingSong.getSongName())){
                break;
            }
        }
        playerGUI.getButtons().setPlaylist(playlist);
        playerGUI.playSong(songs.get(i));
    }

    public static void stopSong (){
        playerGUI.stopSong();
    }


    private class WaitForLibrary implements Runnable {

        @Override
        public void run() {
            while (library == null || library.getAllSongs().size() == 0)
                library = libraryFrame.getLibrary();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


            /*List<Song> songs = Collections.synchronizedList(library.getAllSongs());
            synchronized (songs){
                ListIterator<Song> it = songs.listIterator();
                while (it.hasNext()){
                    try {
                        Song song = it.next();
                        playerGUI.playSong(song);
                        while (song.getSongStatus() != 3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    }
                }
            }*/

}
