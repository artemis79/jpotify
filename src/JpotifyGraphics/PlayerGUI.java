package JpotifyGraphics;

import Logic.Album;
import Logic.Playlist;
import Logic.Song;
import Logic.Time;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayerGUI extends JPanel {

    private JSlider soundSlider;
    private SongSlider songSlider;
    private ArtworkFrame artworkFrame;
    private Song song;
    private ControlButtons buttons;
    private Album album;
    private Playlist playlist;
    private int wholeTime;


    public PlayerGUI() throws IOException {
        super();
        soundSlider = new JSlider(0, 100, 50);
        songSlider = new SongSlider();
        this.setLayout(new BorderLayout());
        buttons = new ControlButtons();
        this.add(buttons, BorderLayout.NORTH);
        this.add(soundSlider, BorderLayout.EAST);
        this.add(songSlider, BorderLayout.CENTER);
        this.setBackground(Color.BLACK);
        artworkFrame = new ArtworkFrame(song);
        this.add (artworkFrame , BorderLayout.WEST);
        this.setVisible(true);
    }

    public void playSong (Song song) throws IOException, InterruptedException, UnsupportedAudioFileException {
        songSlider.playSong(song);
    }


    private class SongSlider extends JPanel{
        private JSlider slider;
        private JLabel trackTimeLabel;
        private JLabel trackPassedLabel;
        private Time trackPassed;

        public SongSlider (){
            super();
            slider = new JSlider(0, 100 , 0);
            trackTimeLabel = new JLabel(" 0 : 00 ");
            trackPassedLabel = new JLabel(" 0 : 00 ");
            trackPassed = new Time (0 , 0);
            this.setLayout(new BorderLayout());
            this.add (trackTimeLabel , BorderLayout.EAST);
            this.add (trackPassedLabel , BorderLayout.WEST);
            this.add (slider , BorderLayout.CENTER);
            this.setVisible(true);
        }

        public void playSong (Song song1) throws IOException, InterruptedException, UnsupportedAudioFileException {
            song = song1;
            song1.calTrackDuration();
            trackPassed.setMinute(0);
            trackPassed.setSecond(0);
            wholeTime = song.getRemaining();
            trackTimeLabel.setText(song1.getTrackDuration().toString());
            artworkFrame.setArtwork(song);
            buttons.pauseResumeSong(song);
            song.play();
            new Thread(new SyncSongSlider()).start();
        }

        public void incrementTimePassed (){
            trackPassed.increment();
            trackPassedLabel.setText(trackPassed.toString());
        }

        public JSlider getSlider(){
            return slider;
        }

    }

    private class SyncSongSlider implements Runnable{

        private boolean run = true;
        @Override
        public void run() {
            while (!song.getPlayMP3().isComplete()){
                try {
                    songSlider.getSlider().getModel().setRangeProperties(wholeTime - song.getRemaining(), wholeTime, 0, wholeTime, true);
                    songSlider.incrementTimePassed();
                    TimeUnit.SECONDS.sleep(1);
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
            int value = soundSlider.getValue();
            try {
                    song.skip(value);
            } catch (IOException e1) {
                    e1.printStackTrace();
            }
        }
    }
}
