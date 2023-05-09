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

    public void Dijkstra(Panel maze, Node start, Node end) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        // Init all distances with infinity
        for (Node node : maze.node) {
            node.distance = Integer.MAX_VALUE;
        }

        // Distance to the root itself is zero
        start.distance = 0;

        // Init queue with the root node
        queue.add(start);

        // Iterate over the priority queue until it is empty.
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();  // Fetch next closest node
            curNode.visited = true;  // Mark as discovered

            // Iterate over unvisited neighbors
            for (Node neighbor : curNode.GetUnvisitedNeighbors()) {
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
        for (Node node : maze.node) {
            node.distance = Integer.MAX_VALUE;
            node.rootDistance = Integer.MAX_VALUE;

            // Note: we may reinforce the manhattan heuristic by using a factor.
            node.manhattanDistance = 2 * (Math.abs(end.row - node.row) + Math.abs(end.col - node.col));
        }
        // Distance to the root itself is zero
        start.rootDistance = 0;

        // Init queue with the root node
        queue.add(start);

        // Iterate over the priority queue until it is empty.
        while (!queue.isEmpty()) {
            Node curNode = queue.poll(); // Fetch next closest node
            curNode.visited = true; // Mark as discovered

            // Iterate over unvisited neighbors
            for (Node neighbor : curNode.getUnvisitedNeighbors()) {
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
