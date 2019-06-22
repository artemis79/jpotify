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
        this.library = library;
        artworks = new ArrayList<AlbumArtwork>();
        library.makeAlbums();
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
