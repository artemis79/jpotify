package JpotifyGraphics;

import Logic.Album;
import Logic.Library;
import Logic.Song;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterFrame extends JPanel {

    private ArrayList<Album> albums;
    private Library library;
    private final int EMPTY_FRAME = -1;
    private final int SHOW_ALBUMS = 0;
    private final int SHOW_SONGS = 1;
    private ArrayList<AlbumArtwork> artworks;
    private PlayerGUI playerGUI;
    private final String ICON_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\icons8-spotify-filled-100.png";
    private int type ;


    public CenterFrame (Library library , int type) throws IOException {
        super();
        this.type = type;
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        if (type == EMPTY_FRAME){
            this.setLayout(new FlowLayout());
            JLabel label = new JLabel("Jpotify");
            label.setFont(new Font(label.getName(), Font.PLAIN, 100));
            //label.setPreferredSize(new Dimension(100 , 100));
            JLabel image = new JLabel();
            File icon = new File(ICON_PATH);
            BufferedImage bufferedImage = ImageIO.read(icon);
            bufferedImage = resize(bufferedImage , 100 , 100);
            image.setIcon(new ImageIcon(bufferedImage));
            label.setForeground(Color.LIGHT_GRAY);
            this.add (image);
            this.add(label);
        }
        else if (type == SHOW_ALBUMS){
            this.library = library;
            artworks = new ArrayList<AlbumArtwork>();
            JScrollPane scrollPane = new JScrollPane();
            Container container = new Container();

            try {
                library.makeAlbums();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.setLayout(new GridLayout(0 , 5));
            albums = library.getAllAlbums();
            for (Album album : albums){
                AlbumArtwork albumArtwork = new AlbumArtwork(album);
                artworks.add (albumArtwork);
                this.add (albumArtwork);
            }
        }
        else if (type == SHOW_SONGS){
            this.library = library;
            ArrayList<Song> songs = library.getAllSongs();
            JScrollPane scrollPane = new JScrollPane();
            Container container = new Container();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            //this.add (scrollPane);
            this.setLayout(new BoxLayout(this , BoxLayout.PAGE_AXIS));
            for (Song song : songs){
                SongFrame songFrame = new SongFrame(song , library);
                container.add(songFrame);
            }
            container.setLayout(new BoxLayout(container , BoxLayout.PAGE_AXIS));
            scrollPane.setViewportView(container);
            this.add (scrollPane);
        }
        this.setVisible(true);

    }

    public int getType (){
        return type;
    }

    public ArrayList<AlbumArtwork> getArtworks (){
        return artworks;
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
