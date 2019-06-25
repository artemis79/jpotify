package JpotifyGraphics;

import Logic.Album;
import Logic.Library;
import Logic.Playlist;
import Logic.Song;
import javazoom.jl.player.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ControlButtons extends JPanel {

    private JLabel pauseOrResume;
    private JLabel rewind;
    private JLabel forward;
    private JLabel shuffle;
    private JLabel repeat;
    private Song song;
    private Album album;
    private Playlist playlist;
    private Library library;
    private int type;
    private final int PLAYING_ALBUM = 0;
    private final int PLAYING_PLAYLIST = 1;
    private final int PLAYING_LIBRARY = 2;
    private final String PARENT_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\";
    private final String PAUSE_PATH =  PARENT_PATH + "icons8-pause-button-80.png";
    private final String RESUME_PATH = PARENT_PATH + "icons8-circled-play-80.png";
    private final String FORWARD_PATH = PARENT_PATH + "icons8-fast-forward-round-80.png";
    private final String REWIND_PATH = PARENT_PATH + "icons8-rewind-button-round-80.png";
    private final String SHUFFLE_PATH =  PARENT_PATH + "icons8-shuffle-80.png";
    private final String REPEAT_PATH =  PARENT_PATH + "icons8-repeat-80.png";

    public ControlButtons () {
        super();
        //setOpaque(false);
        setBackground(Color.darkGray);
        setImages();
        pauseOrResume.addMouseListener(new Pause ());
        forward.addMouseListener(new Next());
        rewind.addMouseListener(new Previous());
        this.setLayout(new FlowLayout());
        this.add (shuffle );
        this.add(rewind);
        this.add (pauseOrResume);
        this.add(forward );
        this.add (repeat );
        this.setVisible(true);

    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private void setImageButton (String path , JLabel label , int size){
        File imgPath = new File(path);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imgPath);
            bufferedImage = resize(bufferedImage , size , size);
            label.setIcon(new ImageIcon(bufferedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImages (){

        pauseOrResume = new JLabel();
        setImageButton(PAUSE_PATH , pauseOrResume , 60);
        rewind = new JLabel();
        setImageButton(REWIND_PATH , rewind , 50);
        forward = new JLabel();
        setImageButton(FORWARD_PATH , forward , 50);
        shuffle = new JLabel();
        setImageButton(SHUFFLE_PATH , shuffle , 30);
        repeat = new JLabel();
        setImageButton(REPEAT_PATH , repeat , 30);
    }

    public void pauseResumeSong (Song song){
        this.song = song;
    }

    public void nextSong (Song song) throws InterruptedException, UnsupportedAudioFileException, IOException {
        if (type == PLAYING_ALBUM) {
            ArrayList<Song> songs = album.getAlbumSongs();
            Iterator<Song> it = songs.iterator();
            while (it.hasNext()) {
                Song findingSong = it.next();
                if (findingSong.getSongName().equals(song.getSongName()) && it.hasNext()) {
                    song.stop();
                    //PlayerGUI.getSyncSong().stop();
                    findingSong = it.next();
                    MainFrame.playSongFromAlbum(album, findingSong);
                    break;
                }
            }
        }
        else if (type == PLAYING_LIBRARY){
            ArrayList<Song> songs = library.getAllSongs();
            Iterator<Song> it = songs.iterator();
            while (it.hasNext()) {
                Song findingSong = it.next();
                if (findingSong.getSongName().equals(song.getSongName()) && it.hasNext()) {
                    song.stop();
                    findingSong = it.next();
                    MainFrame.playSongFromLibrary(library , findingSong);
                    break;
                }
            }
        }

    }

    public void previousSong (Song song) throws InterruptedException, UnsupportedAudioFileException, IOException {
        if (type == PLAYING_ALBUM) {
            ArrayList<Song> songs = album.getAlbumSongs();
            if (!song.getSongName().equals(songs.get(0).getSongName())) {
                int i;
                for (i = 0; i < songs.size() - 1; i++) {
                    if (songs.get(i + 1).getSongName().equals(song.getSongName())) {
                        song.stop();
                        //PlayerGUI.getSyncSong().stop();
                        MainFrame.playSongFromAlbum(album, songs.get(i));
                    }
                }
            }
        }
        else if (type == PLAYING_LIBRARY) {
            ArrayList<Song> songs = library.getAllSongs();
            if (!song.getSongName().equals(songs.get(0).getSongName())) {
                int i;
                for (i = 0; i < songs.size() - 1; i++) {
                    if (songs.get(i + 1).getSongName().equals(song.getSongName())) {
                        song.stop();
                        //PlayerGUI.getSyncSong().stop();
                        MainFrame.playSongFromLibrary(library , songs.get(i));
                    }
                }
            }
        }
    }

    public void setAlbum (Album album){
        this.album = album;
        type = PLAYING_ALBUM;
    }

    public void setLibrary (Library library){
        this.library = library;
        type = PLAYING_LIBRARY;
    }


    private class Pause implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (song != null) {
                try {
                    //if the song is in PLAYING state its status is equal to 2
                    if (song.getSongStatus() == 2) {
                        song.pause();
                        PlayerGUI.getSyncSong().suspend();
                        File fileResume = new File (RESUME_PATH);
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(fileResume);
                            bufferedImage = resize(bufferedImage , 60 , 60);
                            pauseOrResume.setIcon(new ImageIcon(bufferedImage));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                    //if the song is in PAUSED state its status is equal to 1
                    else if (song.getSongStatus() == 1) {
                        song.resume();
                        PlayerGUI.getSyncSong().resume();
                        File fileResume = new File (PAUSE_PATH);
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(fileResume);
                            bufferedImage = resize(bufferedImage , 60 , 60);
                            pauseOrResume.setIcon(new ImageIcon(bufferedImage));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (InterruptedException e1) {
                    System.out.println("Sorry the song could not be paused");
                    e1.printStackTrace();
                }
            }
        }


        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class Next implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (album != null&& song != null && type == PLAYING_ALBUM){
                try {
                    nextSong(song);
                    PlayerGUI.getSyncSong().stop();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if (library != null && song != null && type == PLAYING_LIBRARY){
                try {
                    nextSong(song);
                    PlayerGUI.getSyncSong().stop();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class Previous implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (album != null && song != null && type == PLAYING_ALBUM){
                try {
                    previousSong(song);
                    PlayerGUI.getSyncSong().stop();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if (library != null && song != null && type == PLAYING_LIBRARY){
                try {
                    previousSong(song);
                    PlayerGUI.getSyncSong().stop();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }





}
