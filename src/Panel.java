import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Panel extends JPanel {
    static final int maxCol = 15;
    static final int maxRow = 15;
    static final int nodeScale = 70;
    static final int screenWidth = nodeScale * maxCol;
    static final int screenHeight = nodeScale * maxRow;
    static double totalTimer;
    static Node[][] node = new Node[maxCol][maxRow];
    static Node startPoint;
    static Node endPoint;
    static Node currentPoint;

    public static void setTotalTimer(double totalTimer) {
        Panel.totalTimer = totalTimer;
    }

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
        //SET START AND END
    }
    static void removeStartPoint(){
        startPoint = null;
        currentPoint = null;
    }

    static void removeGoalPoint(){
        endPoint = null;
    }

    static void setStartPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[col][row].setAsStart();
        startPoint = node[col][row];
        currentPoint = startPoint;
    }

    static void setGoalPoint(int col, int row){

        //BEGINNING OF PROGRAM CURRENT NODE IS EQUAL TO THE STARTING NODE
        node[col][row].setAsGoal();
        endPoint = node[col][row];
    }

    public static void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }

    public static void backtrackPath(int delay) throws InterruptedException {
        Timer timer = new Timer (delay, null);
        final Node[] n = {endPoint};
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (n[0] == startPoint){
                    timer.stop();
                }
                n[0] = n[0].parent;
                if (n[0] != startPoint){
                    n[0].setAsPath();
                }
            }
        });
        timer.start();
    }

    public static void removeAllNode(){
        for (int i = 0; i < maxRow; i++){
            for(int j = 0; j < maxCol; j++){
                node[i][j].deselect();
            }
        }
        removeStartPoint();
        removeGoalPoint();
    }

    public static void removeOrangeNode(){
        for (int i = 0; i < maxRow; i++){
            for(int j = 0; j < maxCol; j++){
                if (node[i][j].getBackground() == Color.orange || node[i][j].getBackground() == Color.blue || node[i][j].getBackground() == Color.red) {
                    node[i][j].deselect();
                }
            }
        }
    }

    public static ArrayList<Node> GetUnvisitedNeighbors(Node n){
        ArrayList<Node> neighbors = new ArrayList<>();
        if(n.row-1>=0 && !node[n.col][n.row - 1].solid && !node[n.col][n.row - 1].visited) neighbors.add(node[n.col][n.row-1]);
        if(n.col-1>=0 && !node[n.col - 1][n.row].solid && !node[n.col - 1][n.row].visited) neighbors.add(node[n.col-1][n.row]);
        if(n.row+1<maxRow && !node[n.col][n.row + 1].solid && !node[n.col][n.row + 1].visited) neighbors.add(node[n.col][n.row+1]);
        if(n.col+1<maxCol && !node[n.col + 1][n.row].solid && !node[n.col + 1][n.row].visited) neighbors.add(node[n.col+1][n.row]);
        if(n.row+1<maxRow && n.col-1>=0 && !node[n.col-1][n.row + 1].solid && !node[n.col-1][n.row + 1].visited) neighbors.add(node[n.col-1][n.row+1]);
        //kanan bawah
        if(n.row+1<maxRow && n.col+1<maxCol && !node[n.col + 1][n.row + 1].solid && !node[n.col + 1][n.row + 1].visited) neighbors.add(node[n.col+1][n.row+1]);
        //kiri atas
        if(n.row-1>=0 && n.col-1>=0 && !node[n.col -1 ][n.row - 1].solid && !node[n.col - 1][n.row - 1].visited) neighbors.add(node[n.col - 1][n.row-1]);
        //kanan atas
        if(n.row-1>=0 && n.col+1<maxCol && !node[n.col + 1][n.row - 1].solid && !node[n.col + 1][n.row - 1].visited) neighbors.add(node[n.col + 1][n.row-1]);
        for(Node l:neighbors){
            if(!(l == startPoint) && !(l == endPoint)) l.setAsNeighbors();
        }
        return neighbors;
    }

    public static double getTotalTimer() {
        return totalTimer;
    }

    public static void Dijkstra(Panel maze, Node start, Node end, int delay) {
        totalTimer = 0;
        double startTimer = System.currentTimeMillis();
        Timer timer = new Timer(delay, null);
        PQ<Node> queue = new PQ<Node>();

        // distance all nodes to infinity
        for (Node[] node : maze.node) {
            for(Node n : node){
                n.distance = Integer.MAX_VALUE;
            }
        }

        start.distance = 0;

        // start queue with start node
        queue.add(start);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!queue.isEmpty()) {
                    Node curNode = queue.poll();  // get the next closest node
                    if (curNode != end && curNode != start) curNode.setAsDiscovered(); // set as visited
                    if (curNode == end) {
                        timer.stop();
                        try {
                            backtrackPath(delay);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        long endTimer = System.currentTimeMillis();
                        double differences = (endTimer-startTimer)/1000;
                        setTotalTimer(differences);
                    }
                    // get univisited neighbors
                    for (Node neighbor : GetUnvisitedNeighbors(curNode)) {
                        // update the minimal distance to neighbor
                        // side note: the distance between to adjacent nodes is always constant & equal to 1 in our maze    
                        int minDistance = Math.min(neighbor.distance, curNode.distance + 1);
                        if (minDistance != neighbor.distance) {
                            neighbor.distance = minDistance;  // update minimal distance
                            neighbor.parent = curNode;        // update the parent

                            // change priority to neighbor since it's closer noW
                            if (queue.contains(neighbor)) {
                                queue.remove(neighbor);
                            }
                            queue.add(neighbor);
                        }
                    }
                }
                else{
                    timer.stop();
                    JOptionPane.showConfirmDialog(null, "An error has occurred.\n CAUSE OF ERROR: Path not found.","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }

    public static void AStar(Panel maze, Node start, Node end, int delay) throws InterruptedException {
        totalTimer = 0;
        PQ<Node> queue = new PQ<Node>();
        Timer timer = new Timer(delay, null);
        double startTimer = System.currentTimeMillis();

        for (Node[] node : maze.node) {
            for(Node n : node){
                // distance all nodes to infinity
                n.distance = Integer.MAX_VALUE;
                n.rootDistance = Integer.MAX_VALUE;

                n.manhattanDistance = 2* (Math.abs(end.col - n.col) + Math.abs(end.row - n.row));
            }
        }
        start.distance = 0;

        // start queue with start node
        queue.add(start);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    Node curNode = queue.poll(); // get closest node
                    if(curNode != end && curNode != start) curNode.setAsDiscovered(); // set as visited
                    if(curNode == end) {
                        timer.stop();
                        try {
                            backtrackPath(delay);
                            double endTimer = System.currentTimeMillis();
                            totalTimer = (endTimer-startTimer)/1000;
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // get unvisited neighbors
                    for (Node neighbor : GetUnvisitedNeighbors(curNode)) {
                        // initiate root minimal distance to the neighbor node added our manhattan distance
                        neighbor.rootDistance = Math.min(neighbor.rootDistance, curNode.rootDistance + 1);
                        int minDistance = Math.min(neighbor.distance, neighbor.rootDistance + neighbor.manhattanDistance);
                        if (minDistance != neighbor.distance) {
                            neighbor.distance = minDistance; // update mininmal distance
                            neighbor.parent = curNode; // update the parent
                            // change priority to neighbor since it's closer now
                            if (queue.contains(neighbor)) {
                                queue.remove(neighbor);
                                queue.add(neighbor);
                            }
                        }
                        // visit further nodes
                        if (!queue.contains(neighbor)) {
                            queue.add(neighbor);
                        }
                    }
                }else {
                    timer.stop();
                    JOptionPane.showConfirmDialog(null, "An error has occurred.\n CAUSE OF ERROR: Path not found.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        timer.start();
    }

    public static void BFS(Panel maze, Node start, Node end, int delay) {
        totalTimer = 0;
        Timer timer = new Timer(delay, null);
        double startTimer = System.currentTimeMillis();
        if (start == null || end == null) {
            // Handle invalid inputs
            return;
        }
        for (Node[] node : maze.node) {
            for(Node n : node){
                n.distance = Integer.MAX_VALUE;
            }
        }
        Queue<Node> queue = new LinkedList();
 
        boolean[][] visited = new boolean[maxRow][maxCol];

        start.distance = 0;
        start.visited = true;
        queue.add(start);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    Node currentNode = queue.poll();
                    if(currentNode != start &&  currentNode != end) currentNode.setAsDiscovered();
                    if (currentNode == end) {
                        timer.stop();
                        try {
                            backtrackPath(delay);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        double endTimer = System.currentTimeMillis();
                        totalTimer = (endTimer-startTimer)/1000;
                    }

                    for (Node neighbor : GetUnvisitedNeighbors(currentNode)) {
                        //store the neighbor row and col in neighborRow and neighborCol
                        int neighborRow = neighbor.row;
                        int neighborCol = neighbor.col;

                        if(neighborRow >= 0 && neighborRow < maxRow && neighborCol >= 0 && neighborCol < maxCol){
                            if(!visited[neighborRow][neighborCol]){
                                visited[neighborRow][neighborCol] = true;
                                neighbor.parent = currentNode;
                                neighbor.distance = currentNode.distance+1;
                                queue.add(neighbor);
                            }
                        }
                    }
                }
                else {
                    timer.stop();
                    try {
                        backtrackPath(delay);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showConfirmDialog(null, "An error has occurred.\n CAUSE OF ERROR: Path not found.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }

    public static void DFS(Panel maze, Node start, Node end, int delay) throws InterruptedException {
        Timer timer = new Timer(delay, null);
        for (Node[] nodes : maze.node) {
            for (Node n : nodes) {
                n.distance = Integer.MAX_VALUE;
                n.visited = false;
            }
        }

        Stack<Node> stack = new Stack<>();
        start.visited = true;
        stack.push(start);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stack.isEmpty()) {
                    Node currentNode = stack.pop();

                    if(currentNode != start && currentNode != end) currentNode.setAsDiscovered();
                    if(currentNode == end) {
                        timer.stop();
                        try {
                            backtrackPath(delay);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    for(Node neighbor : GetUnvisitedNeighbors(currentNode)){
                        if (!neighbor.visited) {
                            neighbor.visited = true; // Mark the neighbor as visited
                            neighbor.parent = currentNode;
                            stack.push(neighbor);
                        }
                    }
                }
                else {
                    timer.stop();
                    try {
                        backtrackPath(delay);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showConfirmDialog(null, "An error has occurred.\n CAUSE OF ERROR: Path not found.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }
    public static void BestFS(Panel maze, Node start, Node end, int delay) {
        totalTimer = 0;
        double startTimer = System.currentTimeMillis();
        if (start == null || end == null) {
            // Handle invalid inputs
            return;
        }

        for (Node[] node : maze.node) {
            for(Node n : node){
                n.distance = Integer.MAX_VALUE;
                n.manhattanDistance = 2* (Math.abs(end.col - n.col) + Math.abs(end.row - n.row));

            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        boolean[][] visited = new boolean[maxRow][maxCol];

        start.distance = 0;
        start.visited = true;
        queue.add(start);
        Timer timer = new Timer(100, null);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    Node currentNode = queue.poll();
                    if(currentNode != start &&  currentNode != end) currentNode.setAsDiscovered();
                    if (currentNode == end) {
                        timer.stop();
                        try {
                            backtrackPath(delay);
                            double endTimer = System.currentTimeMillis();
                            totalTimer = (endTimer-startTimer)/1000;
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    ArrayList<Node> neighbors = GetUnvisitedNeighbors(currentNode);
                    for (Node neighbor : neighbors) {
                        //store the neighbor row and col in neighborRow and neighborCol
                        int neighborRow = neighbor.row;
                        int neighborCol = neighbor.col;

                        if(neighborRow >= 0 && neighborRow < maxRow && neighborCol >= 0 && neighborCol < maxCol){if (!visited[neighborRow][neighborCol]) {
                            if (!visited[neighborRow][neighborCol] && !neighbor.solid) {
                                visited[neighborRow][neighborCol] = true;
                                neighbor.parent = currentNode;
                                neighbor.distance = neighbor.manhattanDistance;
                                queue.add(neighbor);
                            }
                        }
                        }
                    }
                }
                else {
                    timer.stop();
                    try {
                        backtrackPath(delay);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showConfirmDialog(null, "An error has occurred.\n CAUSE OF ERROR: Path not found.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        timer.start();
    }

    public static void generateMaze(Panel maze) {
        Random random = new Random();

        Panel.removeAllNode();



        // Create a grid with all walls
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {

                setSolidNode(j, i);
            }
        }

        // Choose a random starting cell
        int startX = random.nextInt(maxRow);
        int startY = random.nextInt(maxCol);

        generateMazeRecursive(startX, startY, maze);
    }

    private static void generateMazeRecursive(int x, int y, Panel maze) {
        Random random = new Random();
        int[] movements = { 1, 2, 3, 4, 5, 6, 7, 8 }; // Randomize the order of directions
        int hash = Objects.hash(x,y,maze);

        shuffleArray(movements,hash);

        for (int direction : movements) {
            int newX = x;
            int newY = y;

            if (direction == 1 && newY - 2 >= 0) {
                newY -= 2;//up
            } else if (direction == 2 && newX + 2 < Panel.maxCol) {
                newX += 2;//right
            } else if (direction == 3 && newY + 2 < Panel.maxRow) {
                newY += 2;//down
            } else if (direction == 4 && newX - 2 >= 0) {
                newX -= 2;//left
            }
//            else if (direction == 5 && newY - 2 >= 0 && newX +2 < maxCol) {
//                newY -= 2;//up-right
//                newX += 2;
//            }
//            else if (direction == 6 && newY + 2 < maxRow && newX + 2 < maxCol) {
//                newY += 2;//down-right
//                newX += 2;
//            }
//            else if (direction == 7 && newY + 2 < maxRow && newX - 2 >= 0) {
//                newY += 2;//down-left
//                newX -= 2;
//            }
//            else if (direction == 8 && newY - 2 >= 0 && newX - 2 >= 0) {
//                newY -= 2;//up-left
//                newX -= 2;
//            }

            if (isSolidNode(newX, newY)) {
                setPathNode(newX, newY);
                setPathNode((x + newX) / 2, (y + newY) / 2);

                generateMazeRecursive(newX, newY, maze);
            }
        }
    }

    private static int[] shuffleArray(int[] array, long hash) {
        Random random = new Random();

        for(int i = 0; i<array.length; i++){
            int index = random.nextInt(array.length);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static void setPathNode(int col, int row) {
        node[col][row].deselect();
    }

    public static boolean isSolidNode(int col, int row) {
        return node[col][row].solid;
    }


}
