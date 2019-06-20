package JpotifyGraphics;

import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainGUI {
    public static void main(String[] args) {
        UsernameFrame usernameFrame = new UsernameFrame();
        try {
            MainFrame mainFrame = new MainFrame();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
