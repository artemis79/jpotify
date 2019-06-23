package JpotifyGraphics;

import Logic.Album;
import Logic.Playlist;
import Logic.Song;
import Logic.Time;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
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
    private static Thread syncSong;


    public PlayerGUI() throws IOException {
        super();
        soundSlider = new JSlider(0, 100, 50);
        songSlider = new SongSlider();
        soundSlider.setBackground(Color.darkGray);
        soundSlider.setForeground(Color.LIGHT_GRAY);
        soundSlider.addChangeListener(new SoundChangeSlider());
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

    public ControlButtons getButtons (){
        return buttons;
    }

    public void playSong (Song song) throws IOException, InterruptedException, UnsupportedAudioFileException {
        songSlider.playSong(song);
    }

    public static Thread getSyncSong (){
        return syncSong;
    }


    private class SongSlider extends JPanel{
        private JSlider slider;
        private JLabel trackTimeLabel;
        private JLabel trackPassedLabel;
        private Time trackPassed;
        private Time trackTime;

        public SongSlider (){
            super();
            this.setOpaque(true);
            this.setBackground(Color.darkGray);
            slider = new JSlider(0, 100 , 0);
            slider.setBackground(Color.darkGray);
            slider.setForeground(Color.cyan);
            trackTimeLabel = new JLabel(" 0 : 00 ");
            trackTimeLabel.setForeground(Color.LIGHT_GRAY);
            trackPassedLabel = new JLabel(" 0 : 00 ");
            trackPassedLabel.setForeground(Color.LIGHT_GRAY);
            trackPassed = new Time (0 , 0);
            trackTime = new Time (0 , 0);
            this.setLayout(new BorderLayout());
            this.add (trackTimeLabel , BorderLayout.EAST);
            this.add (trackPassedLabel , BorderLayout.WEST);
            this.add (slider , BorderLayout.CENTER);
            this.setVisible(true);
        }

        public synchronized void playSong (Song song1) throws IOException, InterruptedException, UnsupportedAudioFileException {
            song = song1;
            song1.calTrackDuration();
            trackTime.setSecond(song1.getTrackDuration().getSecond());
            trackTime.setMinute(song1.getTrackDuration().getMinute());
            trackPassed.setMinute(0);
            trackPassed.setSecond(0);
            wholeTime = song.getRemaining();
            trackTimeLabel.setText(song1.getTrackDuration().toString());
            artworkFrame.setArtwork(song);
            buttons.pauseResumeSong(song);
            song.play();
            syncSong = new Thread(new SyncSongSlider());
            syncSong.start();
        }


        public void incrementTimePassed (){
            trackPassed.increment();
            trackPassedLabel.setText(trackPassed.toString());
        }

        public JSlider getSlider(){
            return slider;
        }

        private class SyncSongSlider implements Runnable{

            private boolean exit = false;
            @Override
            public void run() {
                while (!trackTime.isGreater(trackTime) && !exit){
                    try {
                        songSlider.getSlider().getModel().setRangeProperties(wholeTime - song.getRemaining(), wholeTime, 0, wholeTime, true);
                        songSlider.incrementTimePassed();
                        TimeUnit.SECONDS.sleep(1);

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                song.stop();
            }

            public void exit (){
                exit = true;
            }

        }

    }


    private class SoundChangeSlider implements ChangeListener {


        @Override
        public void stateChanged(ChangeEvent e) {

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
