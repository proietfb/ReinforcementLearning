import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by proietfb on 8/22/16.
 */
public class View {
    private JPanel panel1;
    private JSpinner xyValues;
    private JSpinner nNodes;
    private JSpinner nAgents;
    private JSpinner nWalls;
    private JButton run;

    Model model;

    public View() {
        //model = new Model();
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //model.runModel();
                JOptionPane.showMessageDialog(null,"Hello World");
            }
        });
    }

    public static void main(String[] args) {

        JFrame jFrame = new JFrame("View");

        jFrame.setContentPane(new View().panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        jFrame.pack();
        jFrame.setVisible(true);

    }
}
