package JpotifyGraphics;

import Logic.Library;
import Logic.Playlist;
import Logic.Song;
import Logic.Time;
import javazoom.jl.player.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SongFrame extends JPanel {

    private Song song;
    private Library library;
    private ArrayList<Playlist> playlists;
    private Playlist currentPlaylist;
    private JLabel songName;
    private JLabel songAlbum;
    private JLabel artist;
    private JButton playSong;
    private JLabel trackTime;
    private JButton likeSong;
    private JButton addSong;
    private final String PARENT_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\";
    private final String PLAY_PATH = PARENT_PATH + "icons8-circled-play-80.png";
    private final String EMPTY_LIKE = PARENT_PATH + "icons8-love-80.png";
    private final String FULL_LIKE = PARENT_PATH + "icons8-love-80 (1).png";
    private final String ADD_TO_PLAYLIST = PARENT_PATH + "icons8-plus-80.png";
    private boolean liked = false;

    public SongFrame (Song song , Library library){
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setPreferredSize(new Dimension(300 , 50));
        this.setLayout(new GridLayout(1 , 5));
        this.song = song;
        this.library = library;
        this.playlists = playlists;
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
        //playSong.setPreferredSize(new Dimension(30 , 20));
        likeSong = new JButton();
        //likeSong.setPreferredSize(new Dimension(30 , 20));
        addSong = new JButton();
        try {
            setButtonImage(PLAY_PATH , playSong);
            boolean flag = false;
            for (Song song1 : LibraryFrame.getPlaylists().get(0).getPlaylistSongs())
            {
                if (song1.getSongName().equals(song.getSongName())){
                    flag = true;
                    break;
                }
            }
            if (flag)
                setButtonImage(FULL_LIKE , likeSong);
            else
                setButtonImage(EMPTY_LIKE , likeSong);
            setButtonImage(ADD_TO_PLAYLIST , addSong);
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
                    try {
                        setButtonImage(FULL_LIKE , likeSong);
                        liked = true;
                        ArrayList<Playlist> playlists = LibraryFrame.getPlaylists();
                        for (Playlist playlist : playlists){
                            if (playlist.getPlaylistName().equals("Favourite Songs")) {
                                boolean flag = true;
                                for (Song song1 : playlist.getPlaylistSongs()) {
                                    if (song1.getSongName().equals(song.getSongName()))
                                        flag = false;
                                }
                                if (flag)
                                    playlist.addSongToPlaylist(song);

                            }
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    try {
                        setButtonImage(EMPTY_LIKE , likeSong);
                        liked = false;
                        ArrayList<Playlist> playlists = LibraryFrame.getPlaylists();
                        for (Playlist playlist : playlists){
                            if (playlist.getPlaylistName().equals("Favourite Songs")) {
                                playlist.removeSongFromPlaylist(song);
                            }
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        addSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Playlist> playlists = LibraryFrame.getPlaylists();
                JFrame frame = new JFrame("Select Your Playlist");
                DefaultListModel model = new DefaultListModel();
                for (Playlist playlist : playlists){
                    model.addElement(playlist.getPlaylistName());
                }
                JList<String> list = new JList<>(model);
                frame.setPreferredSize(new Dimension(100 , 200));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                list.setForeground(Color.LIGHT_GRAY);
                list.setBackground(Color.darkGray);
                frame.add (list);
                frame.setVisible(true);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()){
                            String selectedPlaylist = list.getSelectedValue();
                            for (Playlist playlist : playlists){
                                if (playlist.getPlaylistName().equals(selectedPlaylist))
                                    playlist.addSongToPlaylist(song);
                            }
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                });


            }
        });
        Container container = new Container();
        container.setLayout(new FlowLayout());
        container.add (playSong);
        container.add (likeSong);
        container.add(addSong);
        container.setVisible(true);
        this.add (container);
        this.add (songName);
        this.add (songAlbum);
        this.add (artist);
        this.add (trackTime);
        this.setVisible(true);
    }

    public SongFrame (Song song , Playlist playlist){
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setPreferredSize(new Dimension(300 , 50));
        this.setLayout(new GridLayout(1 , 5));
        this.song = song;
        this.currentPlaylist = playlist;
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
        addSong = new JButton();
        try {
            setButtonImage(PLAY_PATH , playSong);
            setButtonImage(ADD_TO_PLAYLIST , addSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.stopSong();
                    MainFrame.playSongFromPlaylist(playlist , song);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        addSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Playlist> playlists = LibraryFrame.getPlaylists();
                JFrame frame = new JFrame("Select Your Playlist");
                DefaultListModel model = new DefaultListModel();
                for (Playlist playlist : playlists){
                    if (!playlist.getPlaylistName().equals(currentPlaylist.getPlaylistName()))
                        model.addElement(playlist.getPlaylistName());
                }
                JList<String> list = new JList<>(model);
                frame.setPreferredSize(new Dimension(100 , 200));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                list.setForeground(Color.LIGHT_GRAY);
                list.setBackground(Color.darkGray);
                frame.add (list);
                frame.setVisible(true);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()){
                            String selectedPlaylist = list.getSelectedValue();
                            for (Playlist playlist : playlists){
                                if (playlist.getPlaylistName().equals(selectedPlaylist))
                                    playlist.addSongToPlaylist(song);
                            }
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                });
            }
        });
        Container container = new Container();
        container.setLayout(new FlowLayout());
        container.add (playSong);
        container.add(addSong);
        container.setVisible(true);
        this.add (container);
        this.add (songName);
        this.add (songAlbum);
        this.add (artist);
        this.add (trackTime);
        this.setVisible(true);


    }

    private void setButtonImage (String filePath , JButton button) throws IOException {
        File file = new File (filePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        bufferedImage = resize(bufferedImage , 20 , 20);
        button.setIcon(new ImageIcon(bufferedImage));

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
