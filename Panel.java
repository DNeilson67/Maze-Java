import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    final int maxCol = 15;
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
        AStar(this, startPoint, endPoint);

        setSolidNode(5, 3);
        setSolidNode(5, 4);
        setSolidNode(5, 5);
        setSolidNode(5, 6);
        setSolidNode(5, 7);
        setSolidNode(5, 8);
        setSolidNode(5, 9);

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

    private void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }

    public ArrayList<Node> GetUnvisitedNeighbors(Node n){
        ArrayList<Node> neighbors = new ArrayList<>();
        if(n.row-1>=0 && node[n.col][n.row-1].solid == false && node[n.col][n.row-1].visited == false) neighbors.add(node[n.col][n.row-1]);
        if(n.col-1>=0 && node[n.col-1][n.row].solid == false && node[n.col-1][n.row].visited == false) neighbors.add(node[n.col-1][n.row]);
        if(n.row+1<maxRow && node[n.col][n.row+1].solid == false && node[n.col][n.row+1].visited == false) neighbors.add(node[n.col][n.row+1]);
        if(n.col+1<maxCol && node[n.col+1][n.row].solid == false && node[n.col+1][n.row].visited == false) neighbors.add(node[n.col+1][n.row]);
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
        // Done! At this point we just have to walk back from the end using the parent
        // If end does not have a parent, it means that it has not been found.
    }

}
