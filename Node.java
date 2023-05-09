import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener, Comparable<Node> {

    int priority;
    Node parent;
    int col;
    int row;
    public int distance; //DISTANCE OF START AND CURRENT POINT
    int rootDistance; //DISTANCE OF CURRENT POINT AND END POINT
    int manhattanDistance; //TOTAL COST OF G AND H
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        setAsSolid();
    }


    @Override
    public int compareTo(Node o) {
        return 0;
    }
}
