//Creating Calorie Plan
//Will Allow User To Decide How Much Weight Loss Weekly To Occur

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RateOfLoss extends JFrame implements ActionListener {

    JButton finish, back, home;
    String user;
    JRadioButton first, second, third;
    ButtonGroup rOL;

    RateOfLoss(String user){
        this.user = user;

        // Initialize buttons
        finish = new JButton("Finish");
        back = new JButton("Back");
        home = new JButton("Home");
        JPanel body = new JPanel();
        JPanel header = new JPanel();

        // Setup the main frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Setup body panel
        body.setLayout(null);
        body.setBounds(0, 0, 850, 500);
        body.setBackground(Color.DARK_GRAY);



        // Setup header panel
        header.setLayout(null);
        header.setBounds(0, 0, 850, 65);
        header.setBackground(Color.LIGHT_GRAY);

        // Configure navigation buttons
        finish.setBounds(715, 15, 110, 35);
        finish.setFont(new Font("Arial", Font.PLAIN, 14));
        finish.setFocusable(false);
        finish.setBackground(Color.WHITE);
        finish.addActionListener(this);

        home.setBounds(30, 15, 110, 35);
        home.setFont(new Font("Arial", Font.PLAIN, 14));
        home.setFocusable(false);
        home.setBackground(Color.WHITE);
        home.addActionListener(this);

        back.setBounds(150, 15, 110, 35);
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.setFocusable(false);
        back.setBackground(Color.WHITE);
        back.addActionListener(this);

        // Add navigation buttons to header
        header.add(finish);
        header.add(home);
        header.add(back);

        JLabel title = new JLabel("Select Your Weekly Weight Loss Goal");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(240, 100, 400, 30);

        first = new JRadioButton("0.5 Lbs/Week");
        second = new JRadioButton("1 Lbs/Week");
        third = new JRadioButton("2 Lbs/Week");

        JRadioButton[] allButtons = {first, second, third};
        int y = 150;
        for (JRadioButton rb : allButtons) {
            rb.setFont(new Font("Arial", Font.PLAIN, 16));
            rb.setForeground(Color.WHITE);
            rb.setBackground(Color.DARK_GRAY);
            rb.setBounds(350, y, 400, 30);
            y += 40;
        }

        rOL = new ButtonGroup();
        rOL.add(first);
        rOL.add(second);
        rOL.add(third);


        body.add(header);
        body.add(first);
        body.add(second);
        body.add(third);
        body.add(title);
        this.add(body);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home){
            new MainPage(this.user);
            dispose();
        }
        else if(e.getSource() == back){
            new ActivityFactor(this.user);
            dispose();
        }
        else if(e.getSource() == finish){
            double selected = 1.25;
            if (first.isSelected()) selected = 0.5;
            else if (second.isSelected()) selected = 1.0;
            else if (third.isSelected()) selected = 2.0;
            createConnection(selected);

            try {
                new CreatePlan(this.user);
                new MainPage(this.user);
                dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    void createConnection(double selected){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "Mynumberis#121");

            // Fixed SQL query - proper INSERT syntax
            String input = "UPDATE weightLoss SET RateOfLoss = ? Where Username = ? ";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setDouble(1, selected);
            pstmt.setString(2, this.user);

            pstmt.executeUpdate();

            // Close resources
            pstmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
