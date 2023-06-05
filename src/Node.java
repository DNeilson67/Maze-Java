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
    boolean start = false;
    boolean goal = false;
    boolean solid = false;
    boolean visited = false;
    boolean unvisitedNeighbor = false;

    public Node(int col, int row){
        this.col = col;
        this.row = row;

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        addActionListener(this);
    }

    public void setAsStart(){
        setBackground(Color.green);
        setForeground(Color.black);
        setText("Start");
        start = true;
        goal = false;
        visited = false;
        solid = false;
    }

    public void setAsDiscovered(){
        setBackground(Color.ORANGE);
        visited = true;
        start = false;
        goal = false;
        unvisitedNeighbor = false;
        solid = false;
    }

    public void setAsNeighbors(){
        setBackground(Color.blue);
        setForeground(Color.black);
        unvisitedNeighbor = true;
    }

    public void setAsGoal(){
        setBackground(Color.cyan);
        setForeground(Color.BLACK);
        setText("Goal");
        goal = true;
        visited = false;
        start = false;
        solid = false;
    }
    public void setAsSolid(){
        setBackground(Color.black);
        setForeground(Color.black);
        setText("");
        goal = false;
        visited = false;
        start = false;
        solid = true;

    }
    public void setAsPath(){
        setBackground(Color.red);
        setForeground(Color.black);
    }

    public void deselect(){
       setBackground(Color.WHITE);
       setForeground(Color.BLACK);
       setText("");
       solid = false;
       start = false;
       goal = false;
       visited = false;
       unvisitedNeighbor = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getBackground() == Color.WHITE && getForeground() == Color.BLACK){ // If Node = white, then Node = black
            Panel.setSolidNode(this.col, this.row);
        }
        else if (getBackground() == Color.orange || getBackground() == Color.blue){
            Panel.removeOrangeNode();
        }
        // if node = orange/red, then remove orange and red node
        else if (getBackground() == Color.BLACK && getForeground() == Color.BLACK && Panel.startPoint != null && Panel.endPoint != null){
            deselect();
        } // If node = black, start point and end point exist, then remove solid node.
        else if (getBackground() == Color.cyan && getForeground() == Color.BLACK && Panel.startPoint != null && Panel.endPoint != null) {
            Panel.removeGoalPoint();
            deselect();
        }
        // If node = blue, start point and end point exist, then remove goal node and endpoint.
        else if (getBackground() == Color.BLACK && getForeground() == Color.BLACK && Panel.startPoint != null && Panel.endPoint == null) {
            Panel.setGoalPoint(this.col, this.row);
        }
        // if node = green, start point exists, end point does not exist, then remove start point.
        else if (getBackground() == Color.green && getForeground() == Color.BLACK && Panel.startPoint != null && Panel.endPoint == null) {
            Panel.removeStartPoint();
            deselect();
        }
        // if node = black, start point and end point does not exist, then set start point
        else if (getBackground() == Color.BLACK && getForeground() == Color.BLACK && Panel.startPoint == null && Panel.endPoint == null) {
            Panel.setStartPoint(this.col, this.row);
        }
    }


    @Override
    public int compareTo(Node o) {
        return 0;
    }
}
