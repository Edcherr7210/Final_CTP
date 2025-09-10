import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CaloriePlan extends JFrame implements ActionListener {
    JTextField weiL;
    JButton next;
    JButton home;
    int weiLAmt;
    String user;

    CaloriePlan(String user){
        this.user = user;

        // Initialize ALL components first - this is very important!
        weiL = new JTextField(30);
        next = new JButton("Next");
        home = new JButton("Home");
        JLabel statement = new JLabel("Desired Weight Loss");
        JPanel body = new JPanel();
        JPanel header = new JPanel();

        // Setup the main frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Setup body panel (the main background panel)
        body.setLayout(null);
        body.setBounds(0, 0, 850, 500);  // IMPORTANT: set bounds when using null layout
        body.setBackground(Color.DARK_GRAY);

        // Setup header panel (the top gray bar)
        header.setLayout(null);
        header.setBounds(0, 0, 850, 65);  // IMPORTANT: set bounds when using null layout
        header.setBackground(Color.LIGHT_GRAY);

        // Configure the weight loss input field
        weiL.setBounds(340, 240, 180, 40);
        weiL.setFont(new Font("Arial", Font.PLAIN, 14));
        weiL.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Configure the label
        statement.setFont(new Font("Arial", Font.PLAIN, 24));
        statement.setBounds(322, 200, 400, 40);
        statement.setForeground(Color.WHITE);

        // Configure the Next button
        next.setBounds(715, 15, 110, 35);
        next.setFont(new Font("Arial", Font.PLAIN, 14));
        next.setFocusable(false);
        next.setBackground(Color.WHITE);
        next.addActionListener(this);

        // Configure the Home button
        home.setBounds(30, 15, 110, 35);
        home.setFont(new Font("Arial", Font.PLAIN, 14));
        home.setFocusable(false);
        home.setBackground(Color.WHITE);
        home.addActionListener(this);

        // NOW add components to their containers (order matters!)
        header.add(next);
        header.add(home);

        body.add(header);
        body.add(weiL);
        body.add(statement);

        this.add(body);

        // Make the frame visible LAST
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home){
            new MainPage(this.user);
            dispose();
        }
        else if(e.getSource() == next){
            try {
                weiLAmt = Integer.parseInt(weiL.getText());
                createConnection();
                new ActivityFactor(this.user);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for weight loss.");
            }
        }
    }

    void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "Mynumberis#121");

            // Fixed SQL query - proper INSERT syntax
            String input = "UPDATE weightLoss SET DesiredWeight = ? Where Username = ? ";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setInt(1, weiLAmt);
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