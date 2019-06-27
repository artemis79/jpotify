
package Logic;
import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Library {

    private String libraryName;
    private String userName;
    private ArrayList<Song> allSongs;
    private ArrayList<String> allSongsPath;
    private ArrayList<Album> allAlbums;
    private ArrayList<Playlist> allPlayLists;


    public Library(String libraryName, String userName) {

        this.libraryName = libraryName;
        this.userName = userName;
        allSongs =  new ArrayList<>();
        allAlbums = new ArrayList<>();
        allSongsPath=new ArrayList<>();
    }

    public void  importSongsPathToLibraryFromPc(String pathName) throws IOException, JavaLayerException {

        File f= new File(pathName);

        File[] allFiles=f.listFiles();

        for (File x : allFiles) {

            allSongsPath.add(x.getPath());

        }

        for (String s:allSongsPath) {

            Song song= null;
            try {
                song = new Song(s);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            allSongs.add(song);

        }


    }

    private Album hasThisAlbum (Song song) {

        if (allAlbums != null){
            for (Album album : allAlbums) {
                if (album.getAlbumName().equals(song.getAlbumName()))
                    return album;
            }
         }
        return null;
    }

    public ArrayList<String> getAllSongsPath() {
        return allSongsPath;
    }

    public ArrayList<Song> getAllSongs() {
        return allSongs;
    }

    public ArrayList<Album> getAllAlbums() {
        return allAlbums;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void makeAlbums() throws Exception {

        for (Song s: allSongs) {
            if (hasThisAlbum(s) == null) {

                Album album = new Album(s.getAlbumName(), s.getArtist(), s.getSongImage());

                album.getAlbumSongs().add(s);

                allAlbums.add(album);
            }
            else {
                Album album = hasThisAlbum(s);
                album.getAlbumSongs().add(s);
            }

        }

    }

    public void setAllSongs (ArrayList<Song> songs){
        this.allSongs = songs;
    }
}
