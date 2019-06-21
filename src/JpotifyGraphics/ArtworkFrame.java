package JpotifyGraphics;

import Logic.Song;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ArtworkFrame extends JPanel {

    private Song song;
    private JLabel labelName;
    private JLabel labelArtist;
    public ArtworkFrame (Song song){
        super();
        this.setPreferredSize(new Dimension(250 , 70));
        this.song = song;
        if (song != null) {
            labelName = new JLabel(song.getSongName());
            labelArtist = new JLabel(song.getArtist());
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.add (labelName);
            this.add(labelArtist);

        }
        else{
            labelName = new JLabel("Song Name");
            labelArtist = new JLabel("Artist Name");
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.add (labelName);
            this.add(labelArtist);
        }
        this.setVisible(true);

    }

    public void setArtwork(Song song){
        this.song = song;
        labelName.setText(song.getSongName());
        labelArtist.setText(song.getArtist());
    }

}
