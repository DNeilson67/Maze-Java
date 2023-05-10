import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                Import();
            }
        });
    }

    void Dijkstra () {
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

    void AStar () {
        JFrame window = new JFrame();
        Panel Panel = new Panel();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(Panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(true);

        Panel.AStar(Panel, Panel.startPoint, Panel.endPoint);
    }
    void BFS(){
        JDialog d = new JDialog(this, "Unavailable Features");
        JLabel l = new JLabel("kerjain git. BFS");
        JPanel p = new JPanel();
        p.add(l);
        d.add(p);
        d.setSize(200,200);
        d.setVisible(true);
    }

    void DFS(){
        JDialog d = new JDialog(this, "Unavailable Features");
        JLabel l = new JLabel("kerjain git. DFS");
        JPanel p = new JPanel();
        p.add(l);
        d.add(p);
        d.setSize(100,100);
        d.setVisible(true);
    }

    void Import(){
        JFrame window = new JFrame();
        Panel Panel = new Panel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(Panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(true);
    }
}
