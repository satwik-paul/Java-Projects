import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentForm extends JFrame {
    JTextField nameField, emailField, deptField;
    JButton submitBtn;
    Connection con;

    StudentForm() {
        setTitle("Student Registration Form");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        submitBtn = new JButton("Submit");
        add(submitBtn);

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "your_password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        submitBtn.addActionListener(e -> saveStudent());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void saveStudent() {
        try {
            String q = "INSERT INTO students(name,email,department) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, nameField.getText());
            ps.setString(2, emailField.getText());
            ps.setString(3, deptField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentForm();
    }
}
