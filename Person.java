package Logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private String userName;
    private String password;
    private Library library;
    private ArrayList<Playlist> allPlayLists;
    private Favourites favouriteSongs;
    private SharedPlaylist sharedPlaylist;
    private String Status;

    public Person(String userName){
        this.userName = userName;

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


}
