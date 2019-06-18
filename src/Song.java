import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The class song holds information about a song
 * and can play, pause and resume a song
 *
 * @author mahshid rahmani
 * @version 1.0
 * @since 2019-06-06
 */

public class Song
{

    private final String FILE_PATH ;
    private Player playMP3;
    private int songStatus;
    private static final int NOT_STARTED = 0;
    private static final int PAUSED = 1;
    private static final int PLAYING = 2;
    private static final int FINISHED = 3;
    private final Object playerBlock;
    private FileInputStream fileInputStream;
    private Thread thread;
    private boolean isFavourite=false;

    public Song (String filePath) throws FileNotFoundException, JavaLayerException {

        FILE_PATH = filePath;
        fileInputStream = new FileInputStream(FILE_PATH);
        playMP3 = new Player(fileInputStream);
        songStatus = NOT_STARTED;
        playerBlock = new Object();
        thread = new Thread(new PlayTillFinished());

    }

    /**
     * Checks the state of a song if it is not paused
     * starts a thread to play the song on
     *
     * @throws InterruptedException
     */

    public void play () throws InterruptedException {
        synchronized (playerBlock) {
            if (songStatus == NOT_STARTED || songStatus == PLAYING || songStatus == FINISHED) {
                try {
                    thread.start();
                    songStatus = PLAYING;
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.out.println("Failed to play the file.");
                }
            }
            else if (songStatus == PAUSED){
                thread.wait();
            }
        }

    }

    /**
     * Checks if the state of a song is PLAYING it pauses the song
     * and allows for another song to be played
     *
     * @throws InterruptedException
     */

    public void pause () throws InterruptedException {
        synchronized (playerBlock) {
            if (songStatus == PLAYING) {
                songStatus = PAUSED;
                thread.suspend();
                playerBlock.notifyAll();
            }
        }
    }

    /**
     * This method checks if the status of a song is PAUSED it resumes
     * the song
     *
     */

    public void resume (){
        synchronized (playerBlock){
            if (songStatus == PAUSED){
                songStatus = PLAYING;
                thread.resume();
            }
        }
    }

    /**
     * This method checks if the status of a song is PLAYING
     * and changes the status to FINISHED and stops the song from playing
     * and allows another song to be played
     */

    public void stop (){
        synchronized (playerBlock){
            if (songStatus == PLAYING){
                thread.stop();
                songStatus = FINISHED;
                playerBlock.notifyAll();
            }
        }
    }

    /**
     * The class PlayTillFinished implements Runnable
     * it plays the song on a thread until it is finished
     *
     */

    private class PlayTillFinished implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    if (!playMP3.play(1)) {
                        songStatus = FINISHED;
                        break;
                    }
                }
            }
            catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }


    public void addToFavourites(Favourites f){

        isFavourite=true;
        f.addSongToPlaylist(this);

    }

}
