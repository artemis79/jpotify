package JpotifyGraphics;

import Logic.Library;
import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame extends JFrame {

    private final static String LABEL = "Jpotify";
    private Library library;
    private LibraryFrame libraryFrame;
    private PlayerGUI playerGUI;

    public MainFrame () throws IOException, JavaLayerException, InterruptedException {
        super();
        playerGUI = new PlayerGUI();
        libraryFrame = new LibraryFrame();
        new Thread(new WaitForLibrary()).start();
        JFrame mainFrame = new JFrame(LABEL);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add (playerGUI, BorderLayout.SOUTH);
        mainFrame.add (libraryFrame , BorderLayout.WEST);
        mainFrame.setVisible(true);

    }

    private class WaitForLibrary  implements Runnable{

        @Override
        public void run() {
            while (library == null || library.getAllSongs().size() == 0)
                library = libraryFrame.getLibrary();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Song> songs = Collections.synchronizedList(library.getAllSongs());
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
            }

        }
    }
}
