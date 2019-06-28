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
        convertSharedPlaylsitToByteArray();

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

    public void convertSharedPlaylsitToByteArray() throws IOException {

        for (Song s : sharedPlaylist.getPlaylistSongs()) {         //read from file and write on byte array//
            System.out.println("not null");
            sharedPlaylistSongsName.add(s.getSongName());
           /* File file = new File(s.getFILE_PATH());
            long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            InputStream ins = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

            int count;
            while ((count = ins.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, count);
            }

            byte[] songBytes=byteArrayOutputStream.toByteArray();
            sharedPlaylistSongsByteArrays.add(songBytes);
            byteArrayOutputStream.close();
            ins.close();*/


        }


    }

    public ArrayList<Song> convertByteArrayToMp3ForEachPerson() throws IOException, UnsupportedAudioFileException, JavaLayerException {

        ArrayList<File> songsFiles=new ArrayList<>();
        ArrayList<Song> songArrayList=new ArrayList<>();
        int i=0;

        for (byte[] byteArray:sharedPlaylistSongsByteArrays) {

            File file = new File(sharedPlaylistSongsName.get(i));
            AudioInputStream in= AudioSystem.getAudioInputStream(file);
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            songsFiles.add(file);
            i++;
        }
        int j=0;
        for (File x:songsFiles) {

            Song s=new Song(x.getPath());
            songArrayList.add(s);

        }


        return songArrayList;

    }

    public ArrayList<byte[]> getSharedPlaylistSongsByteArrays() {
        return sharedPlaylistSongsByteArrays;
    }
}