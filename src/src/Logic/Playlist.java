package Logic;
import java.util.ArrayList;

public class Playlist implements Sort {

    private String playlistName;
    private ArrayList<Song> playlistSongs;
    private SongComparator songComparator;

    public Playlist(String playlistName) {

        this.playlistName = playlistName;
        playlistSongs=new ArrayList<>();
        songComparator=new SongComparator();
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


    @Override
    public void sortSongs() {

        playlistSongs.sort(songComparator);
    }
}
