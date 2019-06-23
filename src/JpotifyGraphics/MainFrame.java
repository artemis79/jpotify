package JpotifyGraphics;

import Logic.Album;
import Logic.Library;
import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
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
    private LibraryFrame libraryFrame;
    private static PlayerGUI playerGUI;
    private CenterFrame centerFrame;

    public MainFrame () throws IOException, JavaLayerException, InterruptedException {
        super();
        JPanel emptyFrame = new JPanel();
        emptyFrame.setOpaque(true);
        emptyFrame.setBackground(Color.darkGray);
        playerGUI = new PlayerGUI();
        libraryFrame = new LibraryFrame();
        new Thread(new WaitForLibrary()).start();
        JFrame mainFrame = new JFrame(LABEL);

        libraryFrame.getButtonAlbum().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (library != null) {
                    try {
                        centerFrame = libraryFrame.getCenterFrame(0);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //mainFrame.remove(emptyFrame);
                    mainFrame.add(centerFrame, BorderLayout.CENTER);
                    mainFrame.repaint();
                    ArrayList<AlbumArtwork> artworks = centerFrame.getArtworks();
                }
            }});


        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add (playerGUI, BorderLayout.SOUTH);
        mainFrame.add (libraryFrame , BorderLayout.WEST);
        //mainFrame.add (emptyFrame , BorderLayout.CENTER);
        mainFrame.setVisible(true);


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
