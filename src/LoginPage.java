import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Objects;


public class LoginPage extends JFrame implements ActionListener, MouseListener {
    JButton submit;
    JLabel signUpLink;
    String userOrEmail;
    String inPass;
    JTextField password;
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
        password = new JTextField(30);
        submit = new JButton();
        signUpLink = new JLabel();
        error = new JLabel();


        imageLogin.setBounds(67, 125, 215, 215); //set the login picture

        //submit button
        submit.setBounds(715, 400, 110, 35);
        submit.setFont(new Font("Arial", Font.PLAIN, 14));
        submit.setText("Next");
        submit.setFocusable(false);
        submit.setBackground(Color.WHITE);
        submit.addActionListener(this);

        //Textbox design
        username.setBounds(500, 185, 220, 40);
        username.setLayout(null);
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setBackground(Color.WHITE);
        username.setText("Username Or Email");
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));


        //Textbox design
        password.setBounds(500, 260, 220, 40);
        password.setLayout(null);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setBackground(Color.WHITE);
        password.setText("Password");
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

        //will take to signup
        signUpLink.setBounds(520, 300, 200, 30);
        signUpLink.setLayout(null);
        signUpLink.setFont(font);
        signUpLink.setText("<html><u>Don't Have An Account?</u></html>");
        signUpLink.addMouseListener(this);
        signUpLink.setForeground(Color.WHITE);

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
    }

    //Connects to the database to get user info
    void CreateConnection() {
        boolean emUserFound = false;
        boolean passFound = false;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC", "root", "Mynumberis#121");
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

                //finds the pass word
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
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Button
        if (e.getSource() == submit){
            this.userOrEmail = this.username.getText();
            this.inPass = this.password.getText();
            CreateConnection();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){ //Clicked onto link
        new SignupPage();
        dispose();
    }


    @Override
    public void mouseEntered(MouseEvent e) { //Hovering over link
        signUpLink.setForeground(Color.LIGHT_GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e) { //When Clicked off link
        // Empty - we don't need this
    }

    @Override
    public void mousePressed(MouseEvent e) {
        signUpLink.setForeground(Color.WHITE);
        // Empty - we don't need this
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Empty - we don't need this
    }
}