import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ImportFile {
    ImportFile(String path) throws FileNotFoundException {
        File file = new File (path);
        Scanner sc = new Scanner(file);
        int startOnce = 0;
        int endOnce = 0;
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            String[] arr;
            //Checking Expressions
            if(s.contains(",")){
                try{
                    arr = s.split(",");
                    Integer[] intArr = Convert(arr);
                    Panel.setSolidNode(intArr[0], intArr[1]);
                }
                catch (Exception e){
                    error2();
                }
            }
            else if (s.contains("//")){
                try{
                    startOnce++;
                    arr = s.split("//");
                    System.out.print(arr);
                    Integer[] intArr = Convert(arr);
                    Panel.setStartPoint(intArr[0], intArr[1]);
                }
                catch (Exception e){
                    error2();
                }
            }
            else if (s.contains("##")){
                try{
                    endOnce++;
                    arr = s.split("##");
                    Integer[] intArr = Convert(arr);
                    Panel.setGoalPoint(intArr[0], intArr[1]);
                }
                catch (Exception e){
                    error2();
                }
            }
            else{
                error2();
            }
        }
        if (Panel.startPoint == null && Panel.endPoint != null){
            error3();
        }
        if (startOnce > 1 || endOnce > 1){
            error4();
        }
    }
    void error1(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Exceeds the maximum of row or columns.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    void error2(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Illegal Expressions.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
    void error3(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Goal mark exists first but start mark does not exist.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }

    void error4(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: There is more than one start or goal mark.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
    Integer[] Convert(String[] arr){
        try {
            Integer[] intArr = new Integer[2];
            intArr[0] = Integer.parseInt(arr[0]);
            intArr[1] = Integer.parseInt(arr[1]);
            if (intArr[0] > Panel.maxRow || intArr[1] > Panel.maxCol) {
                error1();
            }
            return intArr;
        }
        catch (Exception e){
            error2();
        }
        return null;
    }
}
