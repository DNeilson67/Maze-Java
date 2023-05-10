import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class GUI extends JFrame{
    private JButton djikstraAlgorithmButton;
    private JPanel panel1;
    private JButton AStarAlgorithmButton;
    private JLabel Title;
    private JButton importAMazeButton;
    private JButton BFSAlgorithmButton;
    private JButton DFSAlgorithmButton;

    public GUI(){
        setContentPane(panel1);
        setTitle("Main Menu");
        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Title.setFont(new Font("Roboto", Font.BOLD, 25));
        djikstraAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dijkstra();
            }
        });

        AStarAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AStar();
            }
        });
        BFSAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BFS();
            }
        });
        DFSAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DFS();
            }
        });
        importAMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    Import();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    static void Dijkstra() {
        JFrame window = new JFrame();
        Panel Panel = new Panel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(Panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(true);

        Panel.Dijkstra(Panel, Panel.startPoint, Panel.endPoint);
    }

    static void AStar() {
        JFrame window = new JFrame();
        Panel Panel = new Panel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(Panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(true);

        Panel.AStar(Panel, Panel.startPoint, Panel.endPoint);
    }
    static void BFS(){
        JDialog d = new JDialog((Frame) null, "Unavailable Features");
        JLabel l = new JLabel("kerjain git. BFS");
        JPanel p = new JPanel();
        p.add(l);
        d.add(p);
        d.setSize(200,200);
        d.setVisible(true);
    }

    static void DFS(){
        JDialog d = new JDialog((Frame) null, "Unavailable Features");
        JLabel l = new JLabel("kerjain git. DFS");
        JPanel p = new JPanel();
        p.add(l);
        d.add(p);
        d.setSize(100,100);
        d.setVisible(true);
    }
    void Import() throws FileNotFoundException {
        while (true) {
            JFileChooser j = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text documents (*.txt)", "txt", "text");
            j.setFileFilter(filter);
            int response = j.showOpenDialog(null);
            if (response != 0){
                new GUI();
                break;
            }
            JOptionPane option = new JOptionPane();
            int answer = JOptionPane.showConfirmDialog(this, "Do you want to import "+j.getSelectedFile().getName()+"?","Confirm?",1,3);
            if (answer == 0) {

                JFrame window = new JFrame();
                Panel Panel = new Panel();
                window.setDefaultCloseOperation(EXIT_ON_CLOSE);
                window.setResizable(false);
                window.setContentPane(Panel);

                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                window.setResizable(true);

                new ImportFile(j.getSelectedFile().getAbsolutePath());
                break;
            }
            else if (answer == 2 || answer == -1){
                new GUI();
                break;
            }
        }

    }

}
