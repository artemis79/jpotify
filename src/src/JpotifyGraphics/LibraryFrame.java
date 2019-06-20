package JpotifyGraphics;

import Logic.Library;
import Logic.Playlist;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LibraryFrame extends JPanel {

    private JButton button;
    private Library library;


    public LibraryFrame (){
        super();
        this.button = new JButton("Library");
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                }
            }
        });
        this.add(button);
        this.setVisible(true);

    }
}
