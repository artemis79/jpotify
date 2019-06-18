import java.util.ArrayList;

public class Person {

    private String userName;
    private String password;
    private Library library;
    private ArrayList<Playlist> allPlayLists;
    private Favourites favouriteSongs;
    private SharedPlaylist sharedPlaylist;

    public Person(String userName, String password, Library library, ArrayList<Playlist> allPlayLists, Favourites favouriteSongs, SharedPlaylist sharedPlaylist) {

        this.userName = userName;
        this.password = password;
        this.library = library;
        this.allPlayLists = allPlayLists;
        this.favouriteSongs = favouriteSongs;
        this.sharedPlaylist = sharedPlaylist;

    }


}
