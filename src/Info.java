//Gathering Info From User For CTP

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Info extends JFrame implements ActionListener {
    String gender, weight, height, age, username, email;
    JTextField inGen, inWeight, inHeight, inAge;
    JButton submit;


    Info(String user, String email){
        this.username = user;
        this.email = email;
        JPanel dGBG = new JPanel();
        JPanel lBBG = new JPanel();
        JLabel title = new JLabel();
        Font font = new Font("Arial", Font.BOLD, 16);
        ImageIcon loginImage = new ImageIcon("LoginPicture-removebg-preview.png");
        JLabel imageLogin = new JLabel(loginImage);
        JLabel gen = new JLabel();
        JLabel weight = new JLabel();
        JLabel height = new JLabel();
        JLabel age = new JLabel();
        inGen = new JTextField();
        inWeight = new JTextField();
        inHeight = new JTextField();
        inAge = new JTextField();
        submit = new JButton();

        //This button saves and creates the account
        submit.setLayout(null);
        submit.setBackground(Color.WHITE);
        submit.setText("Submit");
        submit.setFocusable(false);
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.addActionListener(this);
        submit.setBounds(715, 420, 110, 35);

        //Create Weight
        inWeight.setLayout(null);
        inWeight.setBounds(500, 65, 220, 50);
        inWeight.setFont(font);
        inWeight.setBackground(Color.WHITE);
        inWeight.setText("Weight");
        inWeight.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Weight label
        weight.setLayout(null);
        weight.setText("Please Insert Your Weight In lbs");
        weight.setBounds(500, 25, 250, 50);
        weight.setFont(font);
        weight.setForeground(Color.WHITE);

        //Create Height
        inHeight.setLayout(null);
        inHeight.setBounds(500, 170, 220, 50);
        inHeight.setFont(font);
        inHeight.setBackground(Color.WHITE);
        inHeight.setText("Height");
        inHeight.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Height label
        height.setLayout(null);
        height.setText("Add Your Height In Inches");
        height.setBounds(500, 130, 250, 50);
        height.setFont(font);
        height.setForeground(Color.WHITE);

        //Create Age
        inAge.setLayout(null);
        inAge.setBounds(500, 270, 220, 50);
        inAge.setFont(font);
        inAge.setBackground(Color.WHITE);
        inAge.setText("Age");
        inAge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Age label
        age.setLayout(null);
        age.setText("Put In Your Age");
        age.setBounds(500, 230, 250, 50);
        age.setFont(font);
        age.setForeground(Color.WHITE);

        //Create Gender
        inGen.setLayout(null);
        inGen.setBounds(500, 365, 220, 50);
        inGen.setFont(font);
        inGen.setBackground(Color.WHITE);
        inGen.setText("Gender");
        inGen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Weight gender
        gen.setLayout(null);
        gen.setText("Put In Your Gender");
        gen.setBounds(500, 325, 250, 50);
        gen.setFont(font);
        gen.setForeground(Color.WHITE);

        //Set the login picture
        imageLogin.setBounds(67, 125, 215, 215);

        //Main background panel
        dGBG.setLayout(null);
        dGBG.setBackground(Color.darkGray);
        dGBG.setSize(850, 500);
        dGBG.add(lBBG);
        dGBG.add(inAge);
        dGBG.add(inWeight);
        dGBG.add(inHeight);
        dGBG.add(inGen);
        dGBG.add(height);
        dGBG.add(weight);
        dGBG.add(age);
        dGBG.add(gen);
        dGBG.add(submit);

        //Left background panel
        lBBG.setLayout(null);
        lBBG.setBackground(Color.CYAN);
        lBBG.setSize(350, 500);
        lBBG.add(imageLogin);
        lBBG.add(title);

        title.setText("CTP");
        title.setLayout(null);
        title.setFont(new Font("Roboto Mono", Font.BOLD, 55));
        title.setBounds(120, 45, 200, 80);

        //Sets the form for the info page
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(dGBG);
        this.setLocationRelativeTo(null);

    }

    void CreateConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC", "root", "Mynumberis#121");
            Statement stmt = con.createStatement();

            String input = "INSERT INTO info (Username, email, Weight, Height, age, gender) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.email);
            pstmt.setInt(3, Integer.parseInt(this.weight));
            pstmt.setInt(4, Integer.parseInt(this.height));
            pstmt.setInt(5, Integer.parseInt(this.age));
            pstmt.setString(6, this.gender);

            pstmt.executeUpdate();

            pstmt.close();
            stmt.close();
            con.close();

            System.out.println("Submitted Information");
            new MainPage(this.username);
            dispose();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            this.gender = inGen.getText();
            this.weight = inWeight.getText();
            this.age = inAge.getText();
            this.height = inHeight.getText();

            CreateConnection();
        }
    }
}
