import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    MainPage(String user){
        JPanel lGBG = new JPanel();



        //Main background panel
        lGBG.setLayout(null);
        lGBG.setBackground(Color.lightGray);
        lGBG.setSize(850, 500);

        //Sets the form for the info page
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(lGBG);
        this.setLocationRelativeTo(null);

    }
}
