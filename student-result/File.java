import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studb";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("1. Add Student\n2. View All Students");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter name: ");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.print("Enter marks: ");
                int marks = scanner.nextInt();
                String grade = (marks >= 90) ? "A" : (marks >= 75) ? "B" : "C";

                PreparedStatement ps = conn.prepareStatement("INSERT INTO students(name, marks, grade) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setInt(2, marks);
                ps.setString(3, grade);
                ps.executeUpdate();
                System.out.println("Student added!");
            } else {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " +
                                       rs.getInt("marks") + " " + rs.getString("grade"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

