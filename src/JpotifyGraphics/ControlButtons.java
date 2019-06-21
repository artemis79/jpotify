package JpotifyGraphics;

import Logic.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButtons extends JPanel {

    private JButton pauseOrResume;
    private JButton nextSong;
    private JButton previousSong;
    private Song song;

    public ControlButtons (){
        super();
        pauseOrResume = new JButton("pause");
        pauseOrResume.setBackground(Color.CYAN);
        pauseOrResume.addActionListener(new PauseOrResumeSong());
        nextSong = new JButton("  next  ");
        nextSong.setBackground(Color.CYAN);
        previousSong = new JButton("previous");
        previousSong.setBackground(Color.CYAN);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        this.add(previousSong);
        this.add (pauseOrResume);
        this.add(nextSong);
        this.setVisible(true);

    }

    public void pauseResumeSong (Song song){
        this.song = song;
    }


    private class PauseOrResumeSong implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (song != null) {
                try {
                    //if the song is in PLAYING state its status is equal to 2
                    if (song.getSongStatus() == 2) {
                        song.pause();
                        pauseOrResume.setText("resume");
                    }
                    //if the song is in PAUSED state its status is equal to 1
                    else if (song.getSongStatus() == 1) {
                        song.resume();
                        pauseOrResume.setText("pause");
                    }
                } catch (InterruptedException e1) {
                    System.out.println("Sorry the song could not be paused");
                    e1.printStackTrace();
                }
            }
        }
    }

}
