package Logic;

import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class Main  {
    public static void main(String[] args) throws FileNotFoundException, JavaLayerException, InterruptedException {

        Library library=new Library(" your Library "," kianahs ");
        try {
            library.importSongsPathToLibraryFromPc("C:\\Users\\Kiana\\Music\\Jpotify");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Song> allSongs=library.getAllSongs();


        for (Song song : allSongs) {

            try {
                song.play();
                Scanner reader = new Scanner(System.in);
                String str;
               while (true){
                    str = reader.next();
                    if (str.equals("pause"))
                        song.pause();
                    else if (str.equals("resume"))
                        song.resume();
                    else if (str.equals("next")){
                        song.stop();
                        int num=allSongs.indexOf(song);
                        num++;
                        song=allSongs.get(num);
                        song.play();

                    }
                    else if(str.equals("previous")){
                        song.stop();
                        int num=allSongs.indexOf(song);

                        if (num!=0)
                        num--;

                         System.out.println(num);
                        song=allSongs.get(num);
                        song.play();

                    }
                    else if (str.equals("terminate")) {
                        song.stop();
                        return;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("could not pause the music");
            }

        }





        }





    }
