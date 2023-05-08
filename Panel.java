import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    final int maxCol = 10;
    final int maxRow = 15;
    final int nodeScale = 70;
    final int screenWidth = nodeScale * maxCol;
    final int screenHeight = nodeScale * maxRow;

    Node[][] node = new Node[maxCol][maxRow];
    Node startPoint, endPoint, currentPoint;

    public Panel(){
        //SETTING ROW AND COLUMNS OF MAP
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));


        //PlACING NODES
        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow){
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }

        //SET START AND END
        setStartPoint(3, 6);
        setGoalPoint(9,3);

        //PLACE SOLID NODES
        setSolidNode(9,1);
        setSolidNode(9,2);
        setSolidNode(5,3);
        setSolidNode(9,4);
        setSolidNode(9,5);
        setSolidNode(9,6);
        setSolidNode(7,7);
        

    }
    private void setStartPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[row][col].setAsStart();
        startPoint = node[col][row];
        currentPoint = startPoint;
    }

    private void setGoalPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[row][col].setAsGoal();
        endPoint = node[col][row];
    }

    private void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }


}
