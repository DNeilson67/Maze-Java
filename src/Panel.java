import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Panel extends JPanel {
    static final int maxCol = 15;
    static final int maxRow = 15;
    final int nodeScale = 70;
    final int screenWidth = nodeScale * maxCol;
    final int screenHeight = nodeScale * maxRow;

    static Node[][] node = new Node[maxCol][maxRow];
    Node startPoint, endPoint, currentPoint;

    public Panel(){
        //SETTING ROW AND COLUMNS OF MAP
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(new GridLayout(maxRow+1, maxCol));


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


        //BUTTONS
        JRadioButton Dijkstra = new JRadioButton("Dijkstra");
        JRadioButton AStar = new JRadioButton("AStar");
        JRadioButton BFS = new JRadioButton("BFS");
        JRadioButton DFS = new JRadioButton("DFS");
        JCheckBox StartB = new JCheckBox("Start");
        JCheckBox GoalB = new JCheckBox("Goal");
        JCheckBox WallB = new JCheckBox("Wall");
        JButton Import = new JButton("Import");
        JButton Reset = new JButton("Reset");
        JButton Start = new JButton("Start");
        JButton Save = new JButton("Save");

        // GROUP BUTTONS
        ButtonGroup BG = new ButtonGroup();
        BG.add(Dijkstra);
        BG.add(AStar);
        BG.add(BFS);
        BG.add(DFS);

        ButtonGroup BG2 = new ButtonGroup();
        BG2.add(StartB);
        BG2.add(GoalB);
        BG2.add(WallB);

        // ADDING THE BUTTONS TO THE FRAME
        this.add(Dijkstra);
        this.add(AStar);
        this.add(BFS);
        this.add(DFS);
        this.add(new Container());
        this.add(StartB);
        this.add(GoalB);
        this.add(WallB);
        for (int i = 0; i < 3; i++){
            this.add(new Container());
        }
        this.add(Save);
        this.add(Import);
        this.add(Reset);
        this.add(Start);

        // ACTION LISTENERS
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SaveFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllNode();
            }
        });
        Import.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt", "text");
                j.setFileFilter(filter);
                int response = j.showOpenDialog(null);
                JOptionPane option = new JOptionPane();
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to import "+j.getSelectedFile().getName()+"?","Confirm?",1,3);
                if (answer == 0) {
                    removeAllNode();
                    try {
                        new ImportFile(j.getSelectedFile().getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Dijkstra.isSelected()){
                    GUI.Dijkstra();
                }
                else if (AStar.isSelected()){
                    GUI.AStar();
                }
                else if (BFS.isSelected()){
                    GUI.BFS();
                }
                else if (DFS.isSelected()){
                    GUI.DFS();
                }
            }
        });
        //SET START AND END
        setStartPoint(2, 7);
        setGoalPoint(11,4);
    }

    private void setStartPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[col][row].setAsStart();
        startPoint = node[col][row];
        currentPoint = startPoint;
    }

    private void setGoalPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[col][row].setAsGoal();
        endPoint = node[col][row];
    }

    public static void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }

    public static void removeAllNode(){
        for (int i = 0; i < maxRow; i++){
            for(int j = 0; j < maxCol; j++){
                node[i][j].deselect();
            }
        }
    }
    public void backtrackPath(){
        Node n = endPoint;
        while(n != startPoint){
            n = n.parent;

            if(n != startPoint){
                n.setAsPath();
            }
        }
    }
    public ArrayList<Node> GetUnvisitedNeighbors(Node n){
        ArrayList<Node> neighbors = new ArrayList<>();
        if(n.row-1>=0 && !node[n.col][n.row - 1].solid && !node[n.col][n.row - 1].visited) neighbors.add(node[n.col][n.row-1]);
        if(n.col-1>=0 && !node[n.col - 1][n.row].solid && !node[n.col - 1][n.row].visited) neighbors.add(node[n.col-1][n.row]);
        if(n.row+1<maxRow && !node[n.col][n.row + 1].solid && !node[n.col][n.row + 1].visited) neighbors.add(node[n.col][n.row+1]);
        if(n.col+1<maxCol && !node[n.col + 1][n.row].solid && !node[n.col + 1][n.row].visited) neighbors.add(node[n.col+1][n.row]);
        return neighbors;
    }
    public void Dijkstra(Panel maze, Node start, Node end) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        // Init all distances with infinity
        for (Node[] node : maze.node) {
            for(Node n : node){
                n.distance = Integer.MAX_VALUE;
            }
        }

        // Distance to the root itself is zero
        start.distance = 0;

        // Init queue with the root node
        queue.add(start);

        // Iterate over the priority queue until it is empty.
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();  // Fetch next closest node
            if(curNode != end && curNode != start) curNode.setAsDiscovered(); // Mark as discovered
            if(curNode == end) break;

            // Iterate over unvisited neighbors
            for (Node neighbor : GetUnvisitedNeighbors(curNode)) {
                // Update minimal distance to neighbor
                // Note: distance between to adjacent node is constant and equal 1 in our grid
                int minDistance = Math.min(neighbor.distance, curNode.distance + 1);
                if (minDistance != neighbor.distance) {
                    neighbor.distance = minDistance;  // update minimal distance
                    neighbor.parent = curNode;        // update best parent

                    // Change queue priority of the neighbor since it has become closer.
                    if (queue.contains(neighbor)) {
                        queue.remove(neighbor);
                    }
                    queue.add(neighbor);
                }
            }
        }
        backtrackPath();
        // Done! At this point, we just have to walk back from the end using the parent
        // If end does not have a parent, it means that it has not been found.
    }

    public void AStar(Panel maze, Node start, Node end) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        // Init all distances with infinity

        for (Node[] node : maze.node) {
            for(Node n : node){
                n.distance = Integer.MAX_VALUE;
                n.rootDistance = Integer.MAX_VALUE;

                n.manhattanDistance = 2* (Math.abs(end.col - n.col) + Math.abs(end.row - n.row));
            }
        }
        // Distance to the root itself is zero
        start.distance = 0;

        // Init queue with the root node
        queue.add(start);

        // Iterate over the priority queue until it is empty.
        while (!queue.isEmpty()) {
            Node curNode = queue.poll(); // Fetch next closest node
            if(curNode != end && curNode != start) curNode.setAsDiscovered(); // Mark as discovered
            if(curNode == end) break;

            // Iterate over unvisited neighbors
            for (Node neighbor : GetUnvisitedNeighbors(curNode)) {
                // Update root minimal distance to neighbor including manhattan distance
                neighbor.rootDistance = Math.min(neighbor.rootDistance, curNode.rootDistance + 1);
                int minDistance = Math.min(neighbor.distance, neighbor.rootDistance + neighbor.manhattanDistance);
                if (minDistance != neighbor.distance) {
                    neighbor.distance = minDistance; // update mininmal distance
                    neighbor.parent = curNode; // update best parent
                    // Change queue priority of the neighbor since it have became closer.
                    if (queue.contains(neighbor)) {
                        queue.remove(neighbor);
                        queue.add(neighbor);
                    }
                }
                // Add neighbor to the queue for further visiting.
                if (!queue.contains(neighbor)) {
                    queue.add(neighbor);

                }

            }
        }

        backtrackPath();
        // Done! At this point we just have to walk back from the end using the parent
        // If end does not have a parent, it means that it has not been found.
    }

}
