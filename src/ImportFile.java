import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ImportFile {
    ImportFile(String path) throws FileNotFoundException {
        File file = new File (path);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] arr = s.split(",");
            try{
                Integer[] intArr = new Integer[2];
                intArr[0] = Integer.parseInt(arr[0]);
                intArr[1] = Integer.parseInt(arr[1]);
                if (intArr[0] > Panel.maxRow || intArr[1] > Panel.maxCol){
                    error1();
                }
                Panel.setSolidNode(intArr[0], intArr[1]);
            }
            catch (Exception e){
                error2();
                break;
            }
        }
    }
    void error1(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Exceeds the maximum of row or columns.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    void error2(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Illegal Expressions. The format must be in '<int>,<int>'.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
}
