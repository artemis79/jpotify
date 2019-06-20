package Logic;

import java.util.ArrayList;

public class Person {

    private String userName;
    private String password;
    private Library library;
    private ArrayList<Playlist> allPlayLists;
    private Favourites favouriteSongs;
    private SharedPlaylist sharedPlaylist;

    public Person(String userName){
        this.userName = userName;

    }


}
