import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

//Signup page to create account if needed
public class SignupPage extends JFrame implements ActionListener {

    JTextField creUser, creEmail, crePass;
    JButton create;
    String user, email, pass;
    Boolean proceed;
    JLabel error;

    SignupPage(){
        JLabel userLabel = new JLabel();
        JLabel emailLabel = new JLabel();
        JLabel passLabel = new JLabel();
        JLabel title = new JLabel();
        JPanel dGBG = new JPanel();
        JPanel lBBG = new JPanel();
        ImageIcon loginImage = new ImageIcon("LoginPicture-removebg-preview.png");
        JLabel imageLogin = new JLabel(loginImage);
        creUser = new JTextField();
        creEmail = new JTextField();
        crePass = new JTextField();
        Font font = new Font("Arial", Font.BOLD, 16);
        create = new JButton();
        error = new JLabel(); // Initialize error label

        //This button saves and creates the account
        create.setBackground(Color.WHITE);
        create.setText("Create");
        create.setFocusable(false);
        create.setFont(new Font("Arial", Font.BOLD, 14));
        create.addActionListener(this);
        create.setBounds(715, 400, 110, 35);

        userLabel.setLayout(null);
        userLabel.setText("Please Create Username");
        userLabel.setBounds(500, 50, 250, 50);
        userLabel.setFont(font);
        userLabel.setForeground(Color.WHITE);

        emailLabel.setLayout(null);
        emailLabel.setText("Please Create Email");
        emailLabel.setBounds(500, 155, 250, 50);
        emailLabel.setFont(font);
        emailLabel.setForeground(Color.WHITE);

        passLabel.setLayout(null);
        passLabel.setText("Please Create Password");
        passLabel.setBounds(500, 260, 250, 50);
        passLabel.setFont(font);
        passLabel.setForeground(Color.WHITE);

        //Create Username
        creUser.setLayout(null);
        creUser.setBounds(500, 90, 220, 50);
        creUser.setFont(font);
        creUser.setBackground(Color.WHITE);
        creUser.setText("Create Username Here!");
        creUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Create Email
        creEmail.setLayout(null);
        creEmail.setBounds(500, 195, 220, 50);
        creEmail.setFont(font);
        creEmail.setBackground(Color.WHITE);
        creEmail.setText("Input Email Here!");
        creEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Create Password
        crePass.setLayout(null);
        crePass.setBounds(500, 300, 220, 50);
        crePass.setFont(font);
        crePass.setBackground(Color.WHITE);
        crePass.setText("Create Password Here!");
        crePass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Error label
        error.setLayout(null);
        error.setForeground(Color.RED);
        error.setFont(new Font("Arial", Font.BOLD, 12));
        error.setBounds(450, 360, 400, 30);
        error.setText("");

        //Set the login picture
        imageLogin.setBounds(67, 125, 215, 215);

        title.setText("CTP");
        title.setLayout(null);
        title.setFont(new Font("Roboto Mono", Font.BOLD, 55));
        title.setBounds(120, 45, 200, 80);

        //Left background panel
        lBBG.setLayout(null);
        lBBG.setBackground(Color.CYAN);
        lBBG.setSize(350, 500);
        lBBG.add(imageLogin);
        lBBG.add(title);

        //Main background panel
        dGBG.setLayout(null);
        dGBG.setBackground(Color.darkGray);
        dGBG.setSize(850, 500);
        dGBG.add(lBBG);
        dGBG.add(creUser);
        dGBG.add(creEmail);
        dGBG.add(crePass);
        dGBG.add(passLabel);
        dGBG.add(emailLabel);
        dGBG.add(userLabel);
        dGBG.add(create);
        dGBG.add(error);

        //Sets the form for the signup page
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(dGBG);
        this.setLocationRelativeTo(null); // Center the window
    }

    //Create Connection for database to create and save account
    void CreateConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC", "root", "Mynumberis#121");
            Statement stmt = con.createStatement();

            // Fixed SQL query - proper column specification
            String input = "INSERT INTO logOrSign (Username, Email, Password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setString(1, this.user);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.pass);

            pstmt.executeUpdate();

            // Close resources
            pstmt.close();
            stmt.close();
            con.close();

            System.out.println("Account created successfully!");
            error.setText("Account created successfully!");
            error.setForeground(Color.GREEN);
            new Info(user);
            dispose();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            error.setText("Database error occurred");
            error.setForeground(Color.RED);
            e.printStackTrace();
        }
    }

    //Create connection to try to check if user, email, or password was already used
    void CheckInformation(){
        proceed = true; // Initialize as true

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC", "root", "Mynumberis#121");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM logOrSign;");

            while(rs.next()){
                String existingUser = rs.getString("Username");
                String existingEmail = rs.getString("Email");

                //Checks if username is already used
                if (Objects.equals(existingUser, this.user)){
                    proceed = false;
                    error.setText("Username already exists!");
                    error.setForeground(Color.RED);
                    break;
                }

                //Checks if email is already used
                if(Objects.equals(existingEmail, this.email)){
                    proceed = false;
                    error.setText("Email already exists!");
                    error.setForeground(Color.RED);
                    break;
                }
            }

            // Close resources
            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            error.setText("Database connection error");
            error.setForeground(Color.RED);
            proceed = false;
            e.printStackTrace();
        }
    }

    //Get create button working
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create){
            this.user = this.creUser.getText();
            this.email = this.creEmail.getText();
            this.pass = this.crePass.getText();

            // Clear previous error messages
            error.setText("");

            // Validate input
            if (user.equals("Create Username Here!") || user.trim().isEmpty()) {
                error.setText("Please enter a valid username");
                error.setForeground(Color.RED);
                return;
            }

            if (email.equals("Input Email Here!") || email.trim().isEmpty()) {
                error.setText("Please enter a valid email");
                error.setForeground(Color.RED);
                return;
            }

            if (pass.equals("Create Password Here!") || pass.trim().isEmpty()) {
                error.setText("Please enter a valid password");
                error.setForeground(Color.RED);
                return;
            }

            System.out.println("Attempting to create account...");
            CheckInformation();

            //If email or user isn't already created before then the account will be created
            if (Objects.equals(proceed, true)){
                CreateConnection();
            }
        }
    }
}