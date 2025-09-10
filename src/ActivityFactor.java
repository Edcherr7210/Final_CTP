import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityFactor extends JFrame implements ActionListener {
    JButton next, back, home;
    String user;

    ActivityFactor(String user){
        this.user = user;

        // Initialize ALL components first - this prevents null errors!
        next = new JButton("Next");
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
        body.setBounds(0, 0, 850, 500);  // IMPORTANT: set bounds for null layout
        body.setBackground(Color.DARK_GRAY);

        // Setup header panel
        header.setLayout(null);
        header.setBounds(0, 0, 850, 65);  // IMPORTANT: set bounds for null layout
        header.setBackground(Color.LIGHT_GRAY);

        // Configure Next button
        next.setBounds(715, 15, 110, 35);
        next.setFont(new Font("Arial", Font.PLAIN, 14));
        next.setFocusable(false);
        next.setBackground(Color.WHITE);
        next.addActionListener(this);

        // Configure Home button
        home.setBounds(30, 15, 110, 35);
        home.setFont(new Font("Arial", Font.PLAIN, 14));
        home.setFocusable(false);
        home.setBackground(Color.WHITE);
        home.addActionListener(this);

        // Configure Back button (I fixed the positioning so it doesn't overlap with home)
        back.setBounds(150, 15, 110, 35);  // Changed position so it doesn't overlap
        back.setFont(new Font("Arial", Font.PLAIN, 14));
        back.setFocusable(false);
        back.setBackground(Color.WHITE);
        back.addActionListener(this);

        // NOW add components to containers (after they're all initialized)
        header.add(next);
        header.add(home);
        header.add(back);

        body.add(header);
        this.add(body);

        // Make visible last
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            new MainPage(this.user);
            dispose();
        }
        else if (e.getSource() == back) {
            new CaloriePlan(this.user);
            dispose();
        }
        else if (e.getSource() == next) {
            // Add your next page navigation here
            // new NextPage(this.user);
            // dispose();
        }
    }
}