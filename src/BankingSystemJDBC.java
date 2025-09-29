import java.sql.*;
import java.util.Scanner;

public class BankingSystemJDBC {
    // Database connection details
    static final String URL = "jdbc:mysql://127.0.0.1:3306/bank";
    static final String USER = "root";
    static final String PASSWORD = "tarang";

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL.");

            int choice;
            do {
                System.out.println("\n1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                if (choice == 1) createAccount();
                else if (choice == 2) deposit();
                else if (choice == 3) withdraw();
                else if (choice == 4) checkBalance();
            } while (choice != 5);

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Create new account
    static void createAccount() throws SQLException {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();
        sc.nextLine(); // clear buffer
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Balance: ");
        double bal = sc.nextDouble();

        String sql = "INSERT INTO account(acco_no, name, balance) VALUES(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accNo);
        ps.setString(2, name);
        ps.setDouble(3, bal);
        ps.executeUpdate();
        System.out.println("Account Created.");
    }

    // Deposit money
    static void deposit() throws SQLException {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        String sql = "UPDATE account SET balance = balance + ? WHERE acco_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, amt);
        ps.setInt(2, accNo);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Deposit Done.");
        else System.out.println("Account Not Found.");
    }

    // Withdraw money
    static void withdraw() throws SQLException {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        String check = "SELECT balance FROM account WHERE acco_no = ?";
        PreparedStatement ps1 = conn.prepareStatement(check);
        ps1.setInt(1, accNo);
        ResultSet rs = ps1.executeQuery();

        if (rs.next()) {
            double bal = rs.getDouble("balance");
            if (amt <= bal) {
                String sql = "UPDATE account SET balance = balance - ? WHERE acco_no = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql);
                ps2.setDouble(1, amt);
                ps2.setInt(2, accNo);
                ps2.executeUpdate();
                System.out.println("Withdraw Done.");
            } else {
                System.out.println("Not Enough Balance.");
            }
        } else {
            System.out.println("Account Not Found.");
        }
    }

    // Check balance
    static void checkBalance() throws SQLException {
        System.out.print("Enter Account No: ");
        int accNo = sc.nextInt();

        String sql = "SELECT name, balance FROM account WHERE acco_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accNo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Balance: " + rs.getDouble("balance"));
        } else {
            System.out.println("Account Not Found.");
        }
    }
}
