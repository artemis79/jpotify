package JpotifyGraphics;

import Logic.Album;
import Logic.Playlist;
import Logic.Song;
import Logic.Time;
import javazoom.jl.decoder.JavaLayerException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayerGUI extends JPanel {

    private SoundSlider soundSlider;
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
        soundSlider = new SoundSlider();
        songSlider = new SongSlider();
        soundSlider.setBackground(Color.darkGray);
        soundSlider.setForeground(Color.LIGHT_GRAY);
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

    public void stopSong (){
        if (song != null) {
            song.stop();
        }
    }

    public static Thread getSyncSong (){
        return syncSong;
    }

    private class SoundSlider extends JPanel{

        private JSlider soundSlider;
        private JLabel volumeLabel;
        private final String PARENT = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\";
        private final String VOLUME_PATH = PARENT + "icons8-speaker-80.png";
        private final String MUTE_PATH = PARENT + "icons8-no-audio-80.png";
        private final String LOW_VOLUME_PATH = PARENT + "icons8-low-volume-80.png";

        public SoundSlider (){
            super();
            this.setOpaque(true);
            this.setBackground(Color.darkGray);
            this.setPreferredSize(new Dimension(500 , 50));
            volumeLabel = new JLabel();
            soundSlider = new JSlider(0 ,100 ,50);
            soundSlider.setBackground(Color.darkGray);
            soundSlider.setForeground(Color.LIGHT_GRAY);
            try {
                setImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setLayout(new FlowLayout());
            this.add (volumeLabel);
            this.add (soundSlider);
            this.setVisible(true);


        }

        private void setImage () throws IOException {
            File file = new File (VOLUME_PATH);
            BufferedImage bufferedImage = ImageIO.read(file);
            bufferedImage = resize(bufferedImage , 20 , 20);
            volumeLabel.setIcon(new ImageIcon(bufferedImage));

        }

        private BufferedImage resize(BufferedImage img, int height, int width) {
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        }
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
            slider.addChangeListener(new PlayerChangeSlider());
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
            slider.setMaximum(wholeTime);
            trackTimeLabel.setText(song1.getTrackDuration().toString());
            artworkFrame.setArtwork(song);
            buttons.pauseResumeSong(song);
            song.play();
            syncSong = new Thread(new SyncSongSlider());
            syncSong.start();
        }


        public void incrementTimePassed (int wholeTime , Song song) throws IOException, UnsupportedAudioFileException {
            int seconds = (int) (song.getTrackDuration().getAllToSeconds() * ((double) song.getRemaining() / wholeTime));
            seconds = song.getTrackDuration().getAllToSeconds() - seconds;
            trackPassed.setSecond(seconds % 60);
            trackPassed.setMinute(seconds / 60);
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
                        songSlider.getSlider().getModel().setRangeProperties(wholeTime - song.getRemaining(),0, 0, wholeTime, true);
                        songSlider.incrementTimePassed(wholeTime ,song);
                        TimeUnit.SECONDS.sleep(1);

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
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
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting() && song != null)
            {
                int value = source.getValue();
                try {
                    song.skip(value);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}