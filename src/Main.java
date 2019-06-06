import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            Song song = new Song("C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\mysong.mp3");
            song.play();
            Scanner reader = new Scanner(System.in);
            String str;
            while (true){
                str = reader.next();
                if (str.equals("pause"))
                    song.pause();
                else if (str.equals("resume"))
                    song.resume();
                else if (str.equals("terminate")) {
                    song.stop();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file does not exist");
        } catch (JavaLayerException e) {
            e.printStackTrace();
            System.out.println("could not play");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("could not pause the music");
        }


    }
}
