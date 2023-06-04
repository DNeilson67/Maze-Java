import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public SaveFile() throws IOException {
        while (true) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt", "text");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showSaveDialog(null);

            File create = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                if (create.createNewFile()){
                    FileWriter w = new FileWriter(fileChooser.getSelectedFile());

                    for (int i = 0; i < Panel.maxRow; i++) {
                        for (int j = 0; j < Panel.maxCol; j++) {
                            if (Panel.node[i][j].getForeground() == Color.black && Panel.node[i][j].getBackground() == Color.black) {
                                w.write(i + "," + j+"\n");
                            }
                            else if (Panel.node[i][j].getBackground() == Color.green){
                                w.write(i+"."+j+"\n");
                            }
                            else if (Panel.node[i][j].getBackground() == Color.blue){
                                w.write(i+"/"+j+"\n");
                            }
                        }
                    }
                    w.close();
                }
                else {
                    JOptionPane.showConfirmDialog(null,"An error has occurred.\nCAUSE OF ERROR: "+fileChooser.getSelectedFile()+" has already existed.","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception e) {
                JOptionPane.showConfirmDialog(null,"An error has occurred.\nCAUSE OF ERROR: "+fileChooser.getSelectedFile()+" has already existed.","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
            }
            break;
            }
    }
}
