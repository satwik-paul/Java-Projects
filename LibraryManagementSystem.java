import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {
    static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    static final String USER = "root";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== LIBRARY MENU ===");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Delete Book");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {
                    case 1 -> addBook(con, sc);
                    case 2 -> viewBooks(con);
                    case 3 -> deleteBook(con, sc);
                    case 4 -> System.exit(0);
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addBook(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Book Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        String q = "INSERT INTO books(name, author) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, name);
        ps.setString(2, author);
        ps.executeUpdate();
        System.out.println(" Book added successfully!");
    }

    static void viewBooks(Connection con) throws SQLException {
        String q = "SELECT * FROM books";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(q);
        System.out.println("=== BOOK LIST ===");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " by " + rs.getString("author"));
        }
    }

    static void deleteBook(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        String q = "DELETE FROM books WHERE id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("🗑️ Book deleted successfully!");
    }
}
