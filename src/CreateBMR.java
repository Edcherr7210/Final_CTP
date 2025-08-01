import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class CreateBMR extends JFrame {
    private String gender;
    private int weight;
    private int height;
    private int age;
    private double bmr;
    private String userOrEmail;

    CreateBMR(String userOrEmail){
        this.userOrEmail = userOrEmail;
        CreateConnection();

        new MainPage(userOrEmail);
    }

    void CreateConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC",
                    "root",
                    "Mynumberis#121"
            );

            // First, retrieve user data
            String selectQuery = "SELECT * FROM INFO WHERE Username = ? OR Email = ?";
            PreparedStatement selectStmt = con.prepareStatement(selectQuery);
            selectStmt.setString(1, this.userOrEmail);
            selectStmt.setString(2, this.userOrEmail);

            ResultSet rs = selectStmt.executeQuery();

            boolean userFound = false;
            while(rs.next()){
                String user = rs.getString("Username");
                String email = rs.getString("Email");

                if (Objects.equals(user, this.userOrEmail) || Objects.equals(email, this.userOrEmail)){
                    this.weight = rs.getInt("Weight");
                    this.height = rs.getInt("Height");
                    this.age = rs.getInt("age");
                    this.gender = rs.getString("gender");
                    userFound = true;
                    break; // Exit loop once user is found
                }
            }

            if (!userFound) {
                System.err.println("User not found: " + this.userOrEmail);
                return;
            }

            // Calculate BMR
            calculateBMR();

            // Update BMR in database
            String updateQuery = "UPDATE INFO SET BMR = ? WHERE Username = ? OR Email = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setDouble(1, this.bmr);
            updateStmt.setString(2, this.userOrEmail);
            updateStmt.setString(3, this.userOrEmail);

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("BMR updated successfully: " + Math.round(this.bmr));
            } else {
                System.err.println("Failed to update BMR");
            }

            // Close resources
            rs.close();
            selectStmt.close();
            updateStmt.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure connection is closed
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private void calculateBMR() {
        // Converting the height from inches to cm
        double cm = this.height * 2.54;

        // Converting the weight from pounds to kg
        double kg = this.weight * 0.453592;

        // Harris-Benedict BMR calculation
        //Male BMR
        if (Objects.equals(this.gender, "Male") || Objects.equals(this.gender, "male")) {
            this.bmr = 88.362 + (13.397 * kg) + (4.799 * cm) - (5.677 * this.age);
        }
        //Female BMR
        else if (Objects.equals(this.gender, "Female") || Objects.equals(this.gender, "female")) {
            this.bmr = 447.593 + (9.247 * kg) + (3.098 * cm) - (4.330 * this.age);
        }
        else {
            System.err.println("Invalid gender: " + this.gender);
        }
    }

    // Getter method to access BMR value
    public double getBMR() {
        return this.bmr;
    }
}