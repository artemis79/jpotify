package Logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private String userName;
    private transient String password;
    private transient Library library;
    private transient ArrayList<Playlist> allPlayLists;
    private transient Favourites favouriteSongs;
    private SharedPlaylist sharedPlaylist;
    private String Status;
    private Song isPlayingSong;

    public Person(String userName){
        this.userName = userName;
        //for test
        sharedPlaylist=new SharedPlaylist("my playlist");

    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public void addSongToSharedPlaylist(Song s){

        sharedPlaylist.addSongToPlaylist(s);
    }

    @Override
    public boolean equals(Object o){

        if (o instanceof Person){

            Person p=(Person) o;

            if(p.getUserName().equals(this.userName))
                return true;

        }

        return false;
    }

}
