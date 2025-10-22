//Creating Calorie Plan
//Will Establish A Plan For Daily Consumption Of Calories

import java.awt.*;
import java.sql.*;


public class CreatePlan {
    int dW, bMR;
    double aF, rOL, duration, cDaily, TDEE;
    String user;

    CreatePlan(String user) throws SQLException, ClassNotFoundException {
        this.user = user;

        CheckInformation();

        Plan();

        updateDB();
    }

    //grabbing info to create weekly calorie plan
    void CheckInformation() throws ClassNotFoundException, SQLException {
        Connection con = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC",
                "root",
                "Mynumberis#121"
        );

        String selectQuery = "SELECT * FROM weightLoss WHERE Username = ?";
        PreparedStatement selectStmt = con.prepareStatement(selectQuery);
        selectStmt.setString(1, this.user);

        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()){
            this.dW = rs.getInt("DesiredWeight");
            this.aF = rs.getDouble("ActivityFactor");
            this.rOL = rs.getDouble("RateOfLoss");
            this.bMR = rs.getInt("BMR");
        }

        rs.close();
        selectStmt.close();

    }

    void Plan(){
        TDEE = bMR * aF;

        cDaily = TDEE - (500 * rOL);

        duration = (dW * 3500) / (500 * rOL);
    }

    /*Macros will come later
    void getMacros(){}
    */
    void updateDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "Mynumberis#121");

            // Fixed SQL query - proper INSERT syntax
            String input = "UPDATE weightLoss SET CalorieDaily = ?, duration = ? Where Username = ? ";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setDouble(1, cDaily);
            pstmt.setDouble(2, duration);
            pstmt.setString(3, this.user);

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
