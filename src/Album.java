import java.util.ArrayList;

public class Album {

    private String albumName;
    private String artist;
    private ArrayList<Song> albumSongs;
    private byte[] albumArtwork;

    public Album(String albumName , String artist , ArrayList<Song> albumSongs , byte[] albumArtwork ){  ///mire az kole song ha ke esme albumeshoon yekie peida mikone mirize too list ye album misaze

        this.albumName=albumName;
        this.artist=artist;
        this.albumSongs=albumSongs;
        this.albumArtwork=albumArtwork;

    }


    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }


    public byte[] getAlbumArtwork() {
        return albumArtwork;
    }

}
