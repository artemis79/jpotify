package Logic;

import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.ArrayList;

public class Person implements Serializable {

    private String userName;
    private transient String password;
    private transient Library library;
    private transient ArrayList<Playlist> allPlayLists;
    private transient Favourites favouriteSongs;
    private transient SharedPlaylist sharedPlaylist;
    private String Status;
    private transient Song isPlayingSong;
    private ArrayList<byte[]> sharedPlaylistSongsByteArrays;
    private ArrayList<String> sharedPlaylistSongsName;
    private static int i=0;

    public Person(String userName) throws IOException, JavaLayerException {
        System.out.println("in constructor");
        this.userName = userName;
        sharedPlaylistSongsByteArrays = new ArrayList<>();
        sharedPlaylistSongsName = new ArrayList<>();
        //for test
        sharedPlaylist = new SharedPlaylist("my playlist");
        Song s1=new Song("C:\\Users\\Kiana\\Music\\Jpotify\\1.mp3");
        Song s2=new Song("C:\\Users\\Kiana\\Music\\Jpotify\\2.mp3");
        System.out.println("songs found");
        sharedPlaylist.addSongToPlaylist(s1);
        sharedPlaylist.addSongToPlaylist(s2);
        System.out.println("into method");
        getSharedPlaylistSongsBytes();
        System.out.println("method finished");
        System.out.println(sharedPlaylistSongsByteArrays.size());
    }

    public ArrayList<String> getSharedPlaylistSongsName() {
        return sharedPlaylistSongsName;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Song getIsPlayingSong() {
        return isPlayingSong;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Library getLibrary() {
        return library;
    }

    public ArrayList<Playlist> getAllPlayLists() {
        return allPlayLists;
    }

    public Favourites getFavouriteSongs() {
        return favouriteSongs;
    }

    public SharedPlaylist getSharedPlaylist() {
        return sharedPlaylist;
    }

    //for test

    public void addSongToSharedPlaylist(Song s) {

        sharedPlaylist.addSongToPlaylist(s);
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Person) {

            Person p = (Person) o;

            if (p.getUserName().equals(this.userName))
                return true;

        }

        return false;
    }

    public void getSharedPlaylistSongsBytes() throws IOException {
        System.out.println("PLayList Size is=");
        System.out.println(sharedPlaylist.getPlaylistSongs().size());

        for (Song s : sharedPlaylist.getPlaylistSongs()) {
            System.out.println("Shared Playlist is not null");
            sharedPlaylistSongsName.add(s.getSongName());
            sharedPlaylistSongsByteArrays.add(s.getSongBytes());

        }


    }

    public ArrayList<Song> convetBytesToMp3Songs() throws IOException, JavaLayerException {

        ArrayList<Song> songArrayList=new ArrayList<>();
        int i=0;
        for (byte[] byteArray:sharedPlaylistSongsByteArrays) {

            File f = new File("C:\\Users\\Kiana\\Desktop\\FinalProject\\writeSongs\\"+sharedPlaylistSongsName.get(i)+".mp3");
            i++;
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(byteArray);
            fos.flush();
            fos.close();
            Song s=new Song(f.getPath());
            songArrayList.add(s);
            i++;
        }
        return songArrayList;
    }


    public ArrayList<byte[]> getSharedPlaylistSongsByteArrays() {
        return sharedPlaylistSongsByteArrays;
    }
}