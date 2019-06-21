package Logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Map;

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
    private String songName;
    private String albumName;
    private String artistName;
    private String releaseYear;
    private byte [] image;
    private final String FILE_PATH ;
    private Player playMP3;
    private int songStatus;
    private static final int NOT_STARTED = 0;
    private static final int PAUSED = 1;
    private static final int PLAYING = 2;
    private static final int FINISHED = 3;
    private Time trackDuration;
    private final Object playerBlock;
    private FileInputStream fileInputStream;
    private Thread thread;

    public Song (String filePath) throws IOException, JavaLayerException, UnsupportedAudioFileException {
        FILE_PATH = filePath;
        fileInputStream = new FileInputStream(FILE_PATH);
        trackDuration = new Time (0 , 0);
        playMP3 = new Player(fileInputStream);
        songStatus = NOT_STARTED;
        playerBlock = new Object();
        thread = new Thread(new PlayTillFinished ());
        getArtwork();
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

    public void skipSong (long value) throws IOException {
        fileInputStream.skip(value);
    }

    public int getSongStatus (){
        return songStatus;
    }

    public void getArtwork () throws IOException {
        RandomAccessFile songFile = new RandomAccessFile(FILE_PATH , "r");
        songFile.seek(songFile.length() - 128);
        byte [] Tag = new byte[3];
        songFile.read(Tag , 0 , 3);
        byte [] songName = new byte[30];
        songFile.read (songName , 0 , 30);
        this.songName = new String(songName);
        byte [] artistName = new byte[30];
        songFile.read (artistName , 0 , 30);
        this.artistName = new String(artistName);
        byte [] albumName = new byte[30];
        songFile.read (albumName , 0 , 30);
        this.albumName = new String(albumName);
        byte [] releaseYear = new byte [5];
        songFile.read(releaseYear , 0 , 5);
        this.releaseYear = new String(releaseYear);
        this.image = new byte[31];
        songFile.read(this.image, 0 , 31);
        songFile.close();

    }

    public String getAlbumName (){
        return albumName;
    }

    public String getArtist(){
        return artistName;
    }

    public byte [] getImage (){
        return image;
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
                playMP3.play();
                songStatus = FINISHED;
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSongName (){
        return songName;
    }

    public Player getPlayMP3 (){
        return playMP3;
    }

    public String getFilePath (){
        return FILE_PATH;
    }

    public int getRemaining () throws IOException {
        return fileInputStream.available();
    }

    public void skip (long frames) throws IOException {
        fileInputStream.skip(frames);
    }

    public void calTrackDuration() throws UnsupportedAudioFileException, IOException {
        try{

            File file=new File(FILE_PATH);

            BufferedInputStream bis = new BufferedInputStream(fileInputStream);

            int duration = 0;

            AudioFile audioFile = AudioFileIO.read(file);
            duration= audioFile.getAudioHeader().getTrackLength();
            System.out.println(duration);

            trackDuration = new Time (duration / 60 , duration % 60);

        }catch(Exception e){

            System.out.print("ERROR "+ e);
        }

    }

    public Time getTrackDuration (){
        return trackDuration;
    }

    /*private ImageData extractImageFromFile(String srcFile) throws Exception {
        ImageData imageData = null;
        File sourceFile = new File(srcFile);
        MP3File mp3file = new MP3File(sourceFile);
        FilenameTag fileNameTag = mp3file.getFilenameTag();
        AbstractID3v2 id3v2 = mp3file.getID3v2Tag();
        if (id3v2 != null) {
            AbstractID3v2Frame apic = id3v2.getFrame(PICTURE_TAG);
            if (apic != null) {
                AbstractMP3FragmentBody apicBody = apic.getBody();
                String mimeType = (String) apicBody.getObject(MIME_TAG);
                String fileExtension = getFileExtensionFromMimeType(mimeType);
                if (fileExtension == null)
                    fileExtension = "jpg";
                if (fileExtension.charAt(0) == '.')
                    fileExtension = fileExtension.substring(1);
                Object bytes = apicBody.getObject(PICTURE_DATA_TAG);
                if (bytes != null) {
                    imageData = new ImageData();
                    imageData.bytes = (byte[]) bytes;
                    imageData.fileExtension = fileExtension;
                }
            }
        }
            return imageData;
    }*/


}


