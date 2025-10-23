//Creating Calorie Plan
//Will Establish A Plan For Daily Consumption Of Calories

import java.awt.*;
import java.sql.*;


public class CreatePlan {
    int dW, bMR, weight;
    double aF, rOL, duration, cDaily, TDEE, protein, kCalP, fats, kCalF, carbs, kCalC;
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

        selectQuery = "SELECT * FROM INFO WHERE Username = ?";
        selectStmt = con.prepareStatement(selectQuery);
        selectStmt.setString(1, this.user);

        rs = selectStmt.executeQuery();
        if(rs.next()){
            this.weight = rs.getInt("Weight");
        }

        rs.close();
        selectStmt.close();

    }

    void Plan(){
        //This Creates Calorie Plan
        TDEE = bMR * aF;
        cDaily = TDEE - (500 * rOL);

        //This Creates Calorie Plan Duration
        duration = (dW * 3500) / (500 * rOL);

        //This Does The Math And Get Macros: Protein, Fats, And Carbs
        protein = weight * 1.0; //0.8-1.0 grams per body of weight, I'll just use 1.0 since I can just set it equal to weight
        kCalP = protein * 4; //KCalories for protein, each gram of protein = 4 calories, protein * 4

        kCalF = cDaily * 0.25; //Should be 20%-30% of total cals, 25% is a good middle
        fats = kCalF / 9; //Each Gram Of Fat = 9 calories

        kCalC = cDaily - (kCalF + kCalP); //The rest of calories go to Carbs;
        carbs = kCalC / 4; // Each gram of carbs = 4 calories;
    }


    void updateDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calorieTracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root",
                    "Mynumberis#121"
            );

            // Update weightLoss
            String input = "UPDATE weightLoss SET CalorieDaily = ?, duration = ? WHERE Username = ?";
            PreparedStatement pstmt = con.prepareStatement(input);
            pstmt.setDouble(1, cDaily);
            pstmt.setDouble(2, duration);
            pstmt.setString(3, user);
            pstmt.executeUpdate();
            pstmt.close();

            // Check if user exists in Macros
            String checkQuery = "SELECT 1 FROM Macros WHERE Username = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, user);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                input = "UPDATE Macros SET Protein = ?, Fat = ?, Carbs = ? WHERE Username = ?";
                pstmt = con.prepareStatement(input);
                pstmt.setDouble(1, protein);
                pstmt.setDouble(2, fats);
                pstmt.setDouble(3, carbs);
                pstmt.setString(4, user);
            } else {
                input = "INSERT INTO Macros (Username, Protein, Fat, Carbs) VALUES (?, ?, ?, ?)";
                pstmt = con.prepareStatement(input);
                pstmt.setString(1, user);
                pstmt.setDouble(2, protein);
                pstmt.setDouble(3, fats);
                pstmt.setDouble(4, carbs);
            }

            pstmt.executeUpdate();
            pstmt.close();
            checkStmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
