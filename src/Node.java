import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Node extends JButton implements ActionListener, Comparable<Node> {

    Node parent;
    int col;
    int row;
    public int distance; //DISTANCE OF START AND CURRENT POINT
    int rootDistance; //DISTANCE OF CURRENT POINT AND END POINT
    int manhattanDistance; //TOTAL COST OF G AND H
    boolean start;
    boolean goal;
    boolean solid;
    boolean visited;


    public Node(int col, int row){
        this.col = col;
        this.row = row;

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        addActionListener(this);
    }

    public void setAsStart(){
        setBackground(Color.green);
        setForeground(Color.WHITE);
        setText("Start");
        start = true;
    }

    public void setAsPath(){
        setBackground(Color.red);
        setForeground(Color.black);
    }

    public void setAsDiscovered(){
        setBackground(Color.ORANGE);
        visited = true;
    }

    public void setAsGoal(){
        setBackground(Color.blue);
        setForeground(Color.BLACK);
        setText("Goal");
        goal = true;
    }
    public void setAsSolid(){
        setBackground(Color.black);
        setForeground(Color.black);
        solid = true;

    }

    public void deselect(){
       setBackground(Color.WHITE);
       setForeground(Color.BLACK);
       solid = false;
       start = false;
       goal = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getBackground() == Color.WHITE && getForeground() == Color.BLACK){
            setAsSolid();
        }
        else{
            deselect();
        }
    }


    @Override
    public int compareTo(Node o) {
        return 0;
    }
}
