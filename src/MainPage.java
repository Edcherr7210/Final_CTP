import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class MainPage extends JFrame implements ActionListener {

    JButton calcBMR, creaPlan, addCals, checkInfo;
    JLabel bmr;
    String userOrEmail;

    // Custom rounded panel class
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false); // Makes the panel transparent so rounded corners show properly
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint the rounded background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Optional: Add a border
            g2.setColor(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(20));
            g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    MainPage(String user){
        JPanel lGBG = new JPanel();
        JPanel navP = new JPanel();
        this.userOrEmail = user;
        // Use the custom rounded panel instead of regular JPanel
        RoundedPanel preBMR = new RoundedPanel(100);
        RoundedPanel dailyCals = new RoundedPanel(100);

        ImageIcon corImage = new ImageIcon("pngtree-muscular-hand-holding-a-barbell-vector-illustration-concept-design-png-image_6371300-removebg-preview.png");
        JLabel corIMG = new JLabel(corImage);
        JLabel ctpLabel = new JLabel();
        bmr = new JLabel();
        calcBMR = new JButton();
        creaPlan = new JButton();
        addCals = new JButton();
        checkInfo = new JButton();

        bmr.setLayout(null);
        bmr.setText("Calculate BMR");
        bmr.setFont(new Font("Arial", Font.BOLD, 25));
        bmr.setBounds(95, 5, 300, 150);

        //This is the panel to present the remaining calories for the day
        dailyCals.setLayout(null);
        dailyCals.setBackground(Color.WHITE);
        dailyCals.setBounds(425, 280, 375, 150);
        dailyCals.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        //This is the panel to present the users BMR
        preBMR.setLayout(null);
        preBMR.setBackground(Color.WHITE);
        preBMR.setBounds(425, 100, 375, 150);
        preBMR.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        preBMR.add(bmr);

        //button to create calorie plan
        creaPlan.setLayout(null);
        creaPlan.setText("Create Your Calorie Plan");
        creaPlan.setBounds(65, 280, 260, 50);
        creaPlan.setFocusable(false);
        creaPlan.setFont(new Font("Arial", Font.BOLD, 14));
        creaPlan.setBackground(Color.WHITE);
        creaPlan.addActionListener(this);

        //Button to add calories
        addCals.setLayout(null);
        addCals.setText("Add Your Calories");
        addCals.setBounds(65, 195, 260, 50);
        addCals.setFocusable(false);
        addCals.setFont(new Font("Arial", Font.BOLD, 14));
        addCals.setBackground(Color.WHITE);
        addCals.addActionListener(this);

        //Button to check all info
        checkInfo.setLayout(null);
        checkInfo.setText("Check All Your Info");
        checkInfo.setBounds(65, 365, 260, 50);
        checkInfo.setFocusable(false);
        checkInfo.setFont(new Font("Arial", Font.BOLD, 14));
        checkInfo.setBackground(Color.WHITE);
        checkInfo.addActionListener(this);

        //Button to calculate users bmr
        calcBMR.setLayout(null);
        calcBMR.setText("Calculate BMR");
        calcBMR.setBounds(65, 110, 260, 50);
        calcBMR.setFocusable(false);
        calcBMR.setFont(new Font("Arial", Font.BOLD, 14));
        calcBMR.setBackground(Color.WHITE);
        calcBMR.addActionListener(this);

        ctpLabel.setLayout(null);
        ctpLabel.setBounds(355, 15, 200, 35);
        ctpLabel.setText("==CTP Main Menu==");
        ctpLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Fixed typo: "Ariel" -> "Arial"

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
        lGBG.add(preBMR);
        lGBG.add(dailyCals);

        //Sets the form for the info page
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(lGBG);
        this.setLocationRelativeTo(null);

        loadBMROnStartup();
    }

    // Load BMR when the page first opens
    void loadBMROnStartup() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC",
                    "root",
                    "Mynumberis#121"
            );

            String selectQuery = "SELECT BMR FROM INFO WHERE Username = ? OR Email = ?";
            PreparedStatement selectStmt = con.prepareStatement(selectQuery);
            selectStmt.setString(1, this.userOrEmail);
            selectStmt.setString(2, this.userOrEmail);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double bmrValue = rs.getDouble("BMR");
                if (bmrValue > 0) {
                    this.bmr.setText(String.format("BMR: %.0f cal/day", bmrValue));
                    this.bmr.setForeground(new Color(34, 139, 34)); // Green color
                } else {
                    this.bmr.setText("Click Calculate BMR");
                    this.bmr.setForeground(Color.GRAY);
                }
            } else {
                this.bmr.setText("User not found");
                this.bmr.setForeground(Color.RED);
            }

            rs.close();
            selectStmt.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error loading BMR: " + e.getMessage());
            this.bmr.setText("Error loading BMR");
            this.bmr.setForeground(Color.RED);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcBMR){
            // Calculate BMR first
            CreateBMR bmrCalculator = new CreateBMR(this.userOrEmail);

            // Then update the display (don't dispose yet!)
            UpdateBMR();

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "BMR calculated and updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            }
        else if (e.getSource() == addCals){
            new CalorieUpdate();
            dispose();
        }
        else if (e.getSource() == creaPlan){
            new CaloriePlan(this.userOrEmail);
            dispose();
        }
        else if (e.getSource() == checkInfo){
            new CheckInfo();
            dispose();
        }
    }

    void UpdateBMR(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC",
                    "root",
                    "Mynumberis#121"
            );

            String selectQuery = "SELECT BMR FROM INFO WHERE Username = ? OR Email = ?";
            PreparedStatement selectStmt = con.prepareStatement(selectQuery);
            selectStmt.setString(1, this.userOrEmail);
            selectStmt.setString(2, this.userOrEmail);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double bmrValue = rs.getDouble("BMR");
                if (bmrValue > 0) {
                    this.bmr.setText(String.format("BMR: %.0f cal/day", bmrValue));
                    this.bmr.setForeground(new Color(34, 139, 34)); // Green color
                    System.out.println("BMR updated on screen: " + bmrValue);
                } else {
                    this.bmr.setText("BMR not calculated");
                    this.bmr.setForeground(Color.GRAY);
                }
            } else {
                this.bmr.setText("User not found");
                this.bmr.setForeground(Color.RED);
            }

            rs.close();
            selectStmt.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error updating BMR: " + e.getMessage());
            this.bmr.setText("Error updating BMR");
            this.bmr.setForeground(Color.RED);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

}