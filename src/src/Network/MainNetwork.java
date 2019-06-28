package Network;

import NewIdea.MainFrame;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.net.ServerSocket;

public class MainNetwork {

    public static void main(String[] args) throws IOException, JavaLayerException {

        try {
            ServerSocket serverSocket = new ServerSocket(6500);
            Server t = new Server(serverSocket);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainFrame myFrame=new MainFrame();


    }
}
