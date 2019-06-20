package JpotifyGraphics;

import Logic.Song;

import javax.swing.*;
import java.awt.*;

public class ArtworkFrame extends JPanel {

    private Song song;
    public ArtworkFrame (Song song){
        super();
        this.setPreferredSize(new Dimension(150 , 70));
        JLabel labelName;
        JLabel labelArtist;
        if (song != null) {
            labelName = new JLabel(song.getSongName());
            labelArtist = new JLabel(song.getArtist());
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.add (labelName);
            this.add(labelArtist);
        }
        this.setVisible(true);

    }
}
