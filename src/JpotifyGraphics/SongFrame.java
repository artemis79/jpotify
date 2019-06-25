package JpotifyGraphics;

import Logic.Library;
import Logic.Song;
import Logic.Time;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SongFrame extends JPanel {

    private Song song;
    private Library library;
    private JLabel songName;
    private JLabel songAlbum;
    private JLabel artist;
    private JButton playSong;
    private JLabel trackTime;
    private JButton likeSong;
    private final String PARENT_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\";
    private final String PLAY_PATH = PARENT_PATH + "icons8-circled-play-80.png";
    private final String EMPTY_LIKE = PARENT_PATH + "icons8-love-80.png";
    private final String FULL_LIKE = PARENT_PATH + "icons8-love-80 (1).png";
    private boolean liked = false;

    public SongFrame (Song song , Library library){
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setLayout(new GridLayout(1 , 6));
        this.song = song;
        this.library = library;
        songName = new JLabel("     " + song.getSongName());
        songName.setForeground(Color.LIGHT_GRAY);
        songAlbum = new JLabel(song.getAlbumName());
        songAlbum.setForeground(Color.LIGHT_GRAY);
        artist = new JLabel(song.getArtist());
        artist.setForeground(Color.LIGHT_GRAY);
        trackTime = new JLabel((new Time(0 , 0)).toString());
        try {
            trackTime = new JLabel(song.getTrackDuration().toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        trackTime.setForeground(Color.LIGHT_GRAY);
        playSong = new JButton();
        playSong.setPreferredSize(new Dimension(30 , 20));
        likeSong = new JButton();
        likeSong.setPreferredSize(new Dimension(30 , 20));
        try {
            setButtonImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.stopSong();
                    MainFrame.playSongFromLibrary(library , song);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        likeSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!liked){

                }
            }
        });
        this.add (playSong);
        this.add (likeSong);
        this.add (songName);
        this.add (songAlbum);
        this.add (artist);
        this.add (trackTime);
        this.setVisible(true);
    }

    private void setButtonImage () throws IOException {
        File file = new File (PLAY_PATH);
        BufferedImage bufferedImage = ImageIO.read(file);
        bufferedImage = resize(bufferedImage , 20 , 20);
        playSong.setIcon(new ImageIcon(bufferedImage));
        file = new File (EMPTY_LIKE);
        bufferedImage = ImageIO.read(file);
        bufferedImage = resize(bufferedImage , 20 , 20);
        likeSong.setIcon(new ImageIcon(bufferedImage));
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public JButton getPlaySongButton (){
        return playSong;
    }

}
