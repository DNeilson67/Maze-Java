import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GUI extends JFrame{
    public GUI() {
        Panel panel = new Panel();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(panel);
        setVisible(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setSize(new Dimension(Panel.screenWidth, Panel.screenHeight));
        setTitle("Pathfinding Navigator");
        //Font
        Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 9);
        Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 8);
        Font f3 = new Font(Font.SANS_SERIF, Font.BOLD, 7);
        //LABELS
        JLabel benchmarkLabel = new JLabel("Time: N/A");
        JLabel delayLabel = new JLabel("Delay");
        //BUTTONS
        JRadioButton Dijkstra = new JRadioButton("Dijkstra", true);
        JRadioButton DijkstraF = new JRadioButton("DijkstraF");
        JRadioButton AStar = new JRadioButton("AStar");
        JRadioButton BFS = new JRadioButton("BFS");
        JRadioButton DFS = new JRadioButton("DFS");
        JRadioButton BestFS = new JRadioButton("BestFS");
        JButton Generate = new JButton("Maze");
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
        BG.add(BestFS);

        //INPUTS
        JSpinner inputDelay = new JSpinner();

        // Set Font
        Dijkstra.setFont(f1);
        AStar.setFont(f1);
        BFS.setFont(f1);
        DFS.setFont(f1);
        BestFS.setFont(f1);
        Generate.setFont(f1);
        Save.setFont(f1);
        Import.setFont(f3);
        Reset.setFont(f2);
        Start.setFont(f1);

        // ADDING THE BUTTONS TO THE FRAME
        this.add(Dijkstra);
        this.add(AStar);
        this.add(BFS);
        this.add(DFS);
        this.add(BestFS);
        this.add(new Container());
        this.add(benchmarkLabel);
        for (int i = 0; i < Panel.maxRow-14;i++){
            this.add(new Container());
        }
        this.add(delayLabel);
        this.add(inputDelay);
        this.add(Generate);
        this.add(Save);
        this.add(Import);
        this.add(Reset);
        this.add(Start);


        // ACTION LISTENERS
        benchmarkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                benchmarkLabel.setText("Time :"+Panel.totalTimer+"s");
            }
        });

        Save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            new SaveFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        ;
                    }
                });
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel.removeAllNode();
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
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to import " + j.getSelectedFile().getName() + "?", "Confirm?", 1, 3);
                if (answer == 0) {
                    Panel.removeAllNode();
                    try {
                        new ImportFile(j.getSelectedFile().getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        Generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panel.generateMaze(panel);
            }
        });
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Dijkstra.isSelected()) {
                    Panel.removeOrangeNode();
                    try {
                        if (Panel.startPoint != null && Panel.endPoint != null) {
                            benchmarkLabel.setText("Time: N/A");
                            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                                // Simulate some time-consuming task
                                Panel.Dijkstra(panel, panel.startPoint, panel.endPoint, (Integer) inputDelay.getValue());
                                return "Task completed!";
                            });

                            future.thenAccept(result -> {
                                // Task has completed, execute additional logic
                                benchmarkLabel.setText("Time :"+ Panel.totalTimer+"s");
                                // ... Your additional logic here ...
                            });

                            // Wait for the task to complete
                            future.get();
                        } else {
                            error();
                        }
                    }
                    catch (Exception a) {
                        error();
                    }
                    System.out.println(Panel.getTotalTimer());
                } else if (AStar.isSelected()) {
                    Panel.removeOrangeNode();
                    try {
                        if (Panel.startPoint != null && Panel.endPoint != null) {
                            benchmarkLabel.setText("Time: N/A");
                            Panel.AStar(panel, panel.startPoint, panel.endPoint, (Integer) inputDelay.getValue());
                        } else {
                            error();
                        }
                    }
                    catch (Exception a) {
                        error();
                    }
                } else if (BFS.isSelected()) {
                    Panel.removeOrangeNode();
                    try {
                        if (Panel.startPoint != null && Panel.endPoint != null) {
                            benchmarkLabel.setText("Time: N/A");
                            Panel.BFS(panel, panel.startPoint, panel.endPoint, (Integer) inputDelay.getValue());
                        } else {
                            error();
                        }
                    }
                    catch (Exception a){
                        error();
                    }
                } else if (DFS.isSelected()) {
                    Panel.removeOrangeNode();
                    try {
                        if (Panel.startPoint != null && Panel.endPoint != null) {
                            benchmarkLabel.setText("Time: N/A");
                            Panel.DFS(panel, panel.startPoint, panel.endPoint, (Integer) inputDelay.getValue());
                        } else {
                            error();
                        }
                    }
                    catch (Exception a){
                        error();
                    }
                }
                else if (BestFS.isSelected()){
                    Panel.removeOrangeNode();
                    try{
                        if (Panel.startPoint != null && Panel.endPoint != null){
                            benchmarkLabel.setText("Time: N/A");
                            Panel.BestFS(panel, panel.startPoint, panel.endPoint, (Integer) inputDelay.getValue());
                        }
                        else{
                            error();
                        }
                    } catch (Exception a) {
                        error();
                    }
                }
            }
        });
    }
    void error(){
        JOptionPane option = new JOptionPane();
        int answer = JOptionPane.showConfirmDialog(null, "An error occurred. Please try again. \nCAUSE OF ERROR: Start mark or Goal mark does not exist.","Error",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
}
