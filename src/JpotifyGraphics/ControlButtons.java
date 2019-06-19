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

    public ControlButtons (Song song){
        super();
        pauseOrResume = new JButton("pause");
        pauseOrResume.addActionListener(new PauseORResumeSong());
        pauseOrResume.setBackground(Color.CYAN);
        nextSong = new JButton("  next  ");
        nextSong.setBackground(Color.CYAN);
        previousSong = new JButton("previous");
        previousSong.setBackground(Color.CYAN);
        this.song = song;
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        this.add(previousSong);
        this.add (pauseOrResume);
        this.add(nextSong);
        this.setVisible(true);

    }


    private class PauseORResumeSong implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //if the song is in PLAYING state its status is equal to 2
                if (song.getSongStatus() == 2){
                    song.pause();
                    pauseOrResume.setText("resume");
                }
                //if the song is in PAUSED state its status is equal to 1
                else if (song.getSongStatus() == 1){
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
