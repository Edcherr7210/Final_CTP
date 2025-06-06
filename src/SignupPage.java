import javax.swing.*;
import java.awt.*;

public class SignupPage extends JFrame {
//current code for just testing

    SignupPage(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        frame.setVisible(true);
        frame.setLayout(null);
        frame.setSize(850, 500);
        frame.add(panel);

        panel.setSize(850, 500);
        panel.setLayout(null);
        panel.setBackground(Color.darkGray);
        panel.add(label);

        label.setBounds(200, 200, 200, 40);
        label.setText("Hello People");


    }
    

}
