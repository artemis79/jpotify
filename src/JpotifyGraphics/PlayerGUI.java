package JpotifyGraphics;

import Logic.Album;
import Logic.Playlist;
import Logic.Song;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayerGUI extends JPanel {

    private JSlider songSlider;
    private JSlider soundSlider;
    private ArtworkFrame artworkFrame;
    private Song song;
    private ControlButtons buttons;
    private Album album;
    private Playlist playlist;
    private int wholeTime;


    public PlayerGUI() throws IOException {
        super();
        songSlider = new JSlider(0, 100 , 0);
        soundSlider = new JSlider(0, 100, 50);
        this.setLayout(new BorderLayout());
        soundSlider.addChangeListener(new SoundChangeSlider());
        songSlider.addChangeListener(new SoundChangeSlider());
        buttons = new ControlButtons();
        this.add(buttons, BorderLayout.NORTH);
        this.add(soundSlider, BorderLayout.EAST);
        this.add(songSlider, BorderLayout.CENTER);
        this.setBackground(Color.BLACK);
        artworkFrame = new ArtworkFrame(song);
        this.add (artworkFrame , BorderLayout.WEST);
        this.setVisible(true);
    }

    public void playSong (Song song) throws IOException, InterruptedException {
        this.song = song;
        wholeTime = song.getRemaining();
        buttons.pauseResumeSong(song);
        song.play();
        artworkFrame = new ArtworkFrame(song);
        this.add (artworkFrame , BorderLayout.WEST);
        this.setVisible(true);
        new Thread(new SyncSongSlider()).start();
    }

    private class SyncSongSlider implements Runnable {

        @Override
        public void run() {
            while (!song.getPlayMP3().isComplete()){
                try {
                    songSlider.getModel().setRangeProperties(wholeTime- song.getRemaining(),wholeTime , 0 , wholeTime , true);
                    TimeUnit.MILLISECONDS.sleep(750);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SoundChangeSlider implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            int value = soundSlider.getValue();
            float soundValue = (float) value / 100;

        }

    }

    private class PlayerChangeSlider implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {

        }
    }
}
