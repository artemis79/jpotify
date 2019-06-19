package JpotifyGraphics;

import Logic.Library;
import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final static String LABEL = "Jpotify";

    public MainFrame () throws IOException, JavaLayerException, InterruptedException {
        super();
        Song song = new Song("C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Logic\\mysong.mp3");
        PlayerGUI playerGUI = new PlayerGUI(song);
        LibraryFrame libraryFrame = new LibraryFrame();
        JFrame mainFrame = new JFrame(LABEL);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setBackground(Color.BLACK);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add (playerGUI, BorderLayout.SOUTH);
        mainFrame.add (libraryFrame , BorderLayout.WEST);
        mainFrame.setVisible(true);

    }
}
