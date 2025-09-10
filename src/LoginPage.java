import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class LoginPage extends JFrame implements ActionListener, MouseListener {
    JButton submit;
    JLabel signUpLink;
    String userOrEmail;
    String inPass;
    JPasswordField password;
    JTextField username;
    JLabel error;

    LoginPage(){
        JPanel dGBG = new JPanel();
        JPanel lBBG = new JPanel();
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.BOLD, 16);
        ImageIcon loginImage = new ImageIcon("LoginPicture-removebg-preview.png");
        JLabel imageLogin = new JLabel(loginImage);
        JLabel ctp = new JLabel();
        username = new JTextField(30);
        password = new JPasswordField(30);
        submit = new JButton();
        signUpLink = new JLabel();
        error = new JLabel();

        imageLogin.setBounds(67, 125, 215, 215);

        //submit button
        submit.setBounds(715, 400, 110, 35);
        submit.setFont(new Font("Arial", Font.PLAIN, 14));
        submit.setText("Next");
        submit.setFocusable(false);
        submit.setBackground(Color.WHITE);
        submit.addActionListener(this);

        //Username textbox design
        username.setBounds(500, 185, 220, 40);
        username.setLayout(null);
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setBackground(Color.WHITE);
        username.setText("Username Or Email");
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        //Password textbox design - FIXED: Remove placeholder text for security
        password.setBounds(500, 260, 220, 40);
        password.setLayout(null);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setBackground(Color.WHITE);
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        label.setText("SignIn Here");
        label.setBounds(564, 115, 200, 60);
        label.setLayout(null);
        label.setFont(font);
        label.setForeground(Color.WHITE);

        ctp.setText("CTP");
        ctp.setLayout(null);
        ctp.setFont(new Font("Roboto Mono", Font.BOLD, 55));
        ctp.setBounds(120, 45, 200, 80);

        //Sign up link - Fixed to be clickable
        signUpLink.setBounds(520, 300, 200, 30);
        signUpLink.setLayout(null);
        signUpLink.setFont(font);
        signUpLink.setText("<html><u>Don't Have An Account?</u></html>");
        signUpLink.addMouseListener(this);
        signUpLink.setForeground(Color.WHITE);
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        error.setBounds(520, 325, 200, 30);
        error.setFont(font);
        error.setForeground(Color.RED);
        error.setLayout(null);

        lBBG.setLayout(null);
        lBBG.setBackground(Color.CYAN);
        lBBG.setSize(350, 500);
        lBBG.add(imageLogin);
        lBBG.add(ctp);

        dGBG.setLayout(null);
        dGBG.setBackground(Color.darkGray);
        dGBG.setSize(850, 500);
        dGBG.add(lBBG);
        dGBG.add(label);
        dGBG.add(username);
        dGBG.add(password);
        dGBG.add(submit);
        dGBG.add(signUpLink);
        dGBG.add(error);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 500);
        this.setLayout(null);
        this.add(dGBG);
        this.setLocationRelativeTo(null);
    }

    //Connects to the database to get user info
    void CreateConnection() {
        boolean emUserFound = false;
        boolean passFound = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "Mynumberis#121");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM logOrSign;");

            while(rs.next()){
                //searches for email or username
                String user = rs.getString("Username");
                String email = rs.getString("Email");
                if(Objects.equals(user, this.userOrEmail) || Objects.equals(email, this.userOrEmail)){
                    System.out.println(user);
                    emUserFound = true;
                }

                //finds the password
                String password = rs.getString("Password");
                if (Objects.equals(password, this.inPass)){
                    System.out.println(password);
                    passFound = true;
                }

                //this checks if the password and email or username is good to proceed
                if (Objects.equals(emUserFound, true) && Objects.equals(passFound, true)){
                    break;
                }
            }

            if (Objects.equals(emUserFound, false) || Objects.equals(passFound, false)){
                userOrEmail = "";
                System.err.println("Username not found");
                this.error.setText("Wrong Input Information");
            } else {
                // Login successful
                System.out.println("Login successful!");
                new MainPage(userOrEmail);
                dispose();
            }

            // Close resources
            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            this.userOrEmail = this.username.getText();

            // FIXED: Proper password handling
            char[] passwordChars = this.password.getPassword();
            this.inPass = new String(passwordChars);
            Arrays.fill(passwordChars, ' '); // Clear from memory for security

            // Clear previous error messages
            this.error.setText("");

            // Updated validation - check for placeholder text and empty fields
            if (!this.userOrEmail.equals("Username Or Email") &&
                    !this.userOrEmail.trim().isEmpty() &&
                    this.inPass.length() > 0) {
                CreateConnection();
            } else {
                this.error.setText("Please fill in all fields");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getSource() == signUpLink) {
            try {
                new SignupPage();
                dispose();
            } catch (Exception ex) {
                System.err.println("Error opening signup page: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == signUpLink) {
            signUpLink.setForeground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == signUpLink) {
            signUpLink.setForeground(Color.WHITE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == signUpLink) {
            signUpLink.setForeground(Color.GRAY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == signUpLink) {
            signUpLink.setForeground(Color.WHITE);
        }
    }
}