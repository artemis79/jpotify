package Logic;
import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {

    private String playlistName;
    private ArrayList<Song> playlistSongs;


    public Playlist(String playlistName) {

        this.playlistName = playlistName;
        playlistSongs=new ArrayList<>();

    }


    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void addSongToPlaylist(Song s){

        playlistSongs.add(s);

    }

    public void removeSongFromPlaylist(Song s){

        playlistSongs.remove(s);

    }

    public void setPlaylistSongs (ArrayList<Song> songs){
        this.playlistSongs = songs;
    }



}