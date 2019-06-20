package JpotifyGraphics;

import Logic.Album;
import Logic.Library;
import Logic.Person;
import Logic.Playlist;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryFrame extends JPanel {

    private JButton button;
    private Library library;
    private JList<String> libraryList;
    private ArrayList<Album> albums;


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
                    library = new Library("your Library" , "sldkjf");
                    try {
                        library.importSongsPathToLibraryFromPc(selectedFile.getAbsolutePath());
                        //System.out.println(library.getAllSongs().size());
                        albums = library.getAllAlbums();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        this.add(button);
        //this.add (libraryList);
        this.setVisible(true);

    }

    public Library getLibrary (){
        return library;
    }
}
