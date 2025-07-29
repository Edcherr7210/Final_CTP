import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {

    JButton calcBMR, creaPlan, addCals, checkInfo;


    MainPage(String user){
        JPanel lGBG = new JPanel();
        JPanel navP = new JPanel();
        ImageIcon corImage = new ImageIcon("pngtree-muscular-hand-holding-a-barbell-vector-illustration-concept-design-png-image_6371300-removebg-preview.png");
        JLabel corIMG = new JLabel(corImage);
        JLabel ctpLabel = new JLabel();
        calcBMR = new JButton();
        creaPlan = new JButton();
        addCals = new JButton();
        checkInfo = new JButton();

        //Little corner where the image is going to be clicked to go back to this screen


        //button to create calorie plan
        creaPlan.setLayout(null);
        creaPlan.setText("Create Your Calorie Plan");
        creaPlan.setBounds(65, 280, 260, 50);
        creaPlan.setFocusable(false);
        creaPlan.setFont(new Font("Arial", Font.BOLD, 14));
        creaPlan.setBackground(Color.WHITE);

        //Button to add calories
        addCals.setLayout(null);
        addCals.setText("Add Your Calories");
        addCals.setBounds(65, 195, 260, 50);
        addCals.setFocusable(false);
        addCals.setFont(new Font("Arial", Font.BOLD, 14));
        addCals.setBackground(Color.WHITE);

        //Button to check all info
        checkInfo.setLayout(null);
        checkInfo.setText("Check All Your Info");
        checkInfo.setBounds(65, 365, 260, 50);
        checkInfo.setFocusable(false);
        checkInfo.setFont(new Font("Arial", Font.BOLD, 14));
        checkInfo.setBackground(Color.WHITE);

        //Button to calculate users bmr
        calcBMR.setLayout(null);
        calcBMR.setText("Calculate BMR");
        calcBMR.setBounds(65, 110, 260, 50);
        calcBMR.setFocusable(false);
        calcBMR.setFont(new Font("Arial", Font.BOLD, 14));
        calcBMR.setBackground(Color.WHITE);



        ctpLabel.setLayout(null);
        ctpLabel.setBounds(355, 15, 200, 35);
        ctpLabel.setText("==CTP Main Menu==");
        ctpLabel.setFont(new Font("Ariel", Font.BOLD, 16));

        //This is the navigation panel to go home
        navP.setLayout(null);
        navP.setBackground(Color.lightGray);
        navP.setBounds(0, 0, 850, 65);
        navP.add(ctpLabel);
        navP.add(corIMG);


        //Main background panel
        lGBG.setLayout(null);
        lGBG.setBackground(Color.DARK_GRAY);
        lGBG.setSize(850, 500);
        lGBG.add(navP);
        lGBG.add(calcBMR);
        lGBG.add(addCals);
        lGBG.add(creaPlan);
        lGBG.add(checkInfo);

        //Sets the form for the info page
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(lGBG);
        this.setLocationRelativeTo(null);

    }
}
