/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;

/**
 *
 * @author huynh
 */
public class CountClassandStudentDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/assjava3"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "0359910800"; // Đổi mật khẩu của bạn nếu cần

    static {
        try {
            // Đăng ký driver của MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
    
    public static void updateStudentCount(JLabel label) {
      
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            // Câu lệnh SQL để đếm số lượng sinh viên
            String sql = "SELECT COUNT(*) AS tongsv FROM sinhvien ";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int count = resultSet.getInt("tongsv");
                    // Gán số lượng sinh viên vào JLabel
                    label.setText("Số lượng sinh viên: " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            label.setText("Lỗi kết nối cơ sở dữ liệu!");
        }
    }
    public static void updateClassCount(JLabel label) {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            // Câu lệnh SQL để đếm số lượng sinh viên
            String sql = "SELECT COUNT(*) AS tonglophoc FROM lophoc ";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int count = resultSet.getInt("tonglophoc");
                    // Gán số lượng sinh viên vào JLabel
                    label.setText("Số lượng lớp : " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            label.setText("Lỗi kết nối cơ sở dữ liệu!");
        }
    }
}
