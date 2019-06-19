package Logic;

import Logic.Song;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            Song song = new Song("C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Logic\\mysong.mp3");
            song.play();
            Scanner reader = new Scanner(System.in);
            String str;
            while (true){
                str = reader.next();
                switch (str) {
                    case "pause":
                        song.pause();
                        break;
                    case "resume":
                        song.resume();
                        break;
                    case "terminate":
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
