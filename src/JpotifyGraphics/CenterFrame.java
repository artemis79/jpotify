package JpotifyGraphics;

import Logic.Album;
import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterFrame extends JPanel {

    private ArrayList<Album> albums;
    private Library library;
    private final int SHOW_ALBUMS = 0;
    private ArrayList<AlbumArtwork> artworks;
    private PlayerGUI playerGUI;


    public CenterFrame (Library library , int type) throws IOException {
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.library = library;
        artworks = new ArrayList<AlbumArtwork>();
        try {
            library.makeAlbums();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type == 0){
            this.setLayout(new GridLayout());
            albums = library.getAllAlbums();
            for (Album album : albums){
                AlbumArtwork albumArtwork = new AlbumArtwork(album);
                artworks.add (albumArtwork);
                this.add (albumArtwork);
            }
        }
        this.setVisible(true);

    }

    public ArrayList<AlbumArtwork> getArtworks (){
        return artworks;
    }







}
