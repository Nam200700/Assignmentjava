/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import AESE.AESEncryptionDecryption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Class2;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import AESE.AESEncryptionDecryption;

/**
 *
 * @author ACER
 */
public class ClassDAO2 {

    private static final String JDBC_URL = "2UGbC90SoTIodhtfVryN6aV5vt+EANPUp+k0z5qvDhPn3MO8gPvigzE/cBlSJcM/"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "1HLnoUbwmPSnHbQTRzSBZA==";
    private static final String PASSWORD = " fXjMnti9OCy6eSgeESt1oA=="; // Đổi mật khẩu của bạn nếu cần

    static {
        try {
            // Đăng ký driver của MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }
    // code giải mã để kết nối sau khi mã hóa code vì connection không tự giải hóa được
    public static Connection connection() throws SQLException {
        AESEncryptionDecryption aes = new AESEncryptionDecryption();
        final String secretKey = "mySecretKey123";  // Khóa giải mã

        String decryptedJdbcUrl = aes.decrypt(JDBC_URL, secretKey);
        String decryptedUser = aes.decrypt(USER, secretKey);
        String decryptedPassword = aes.decrypt(PASSWORD.trim(), secretKey);  // Loại bỏ khoảng trắng thừa

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Đảm bảo driver được tải
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver không được tìm thấy!", e);
        }

        return DriverManager.getConnection(decryptedJdbcUrl, decryptedUser, decryptedPassword);
    }

    public static void insertDe(Class2 cl) {
        String sql = "INSERT INTO LopHoc (maLop, tenLop, moTa) VALUES (?, ?, ?)";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cl.getMalop());
            pstmt.setString(2, cl.getTenlop());
            pstmt.setString(3, cl.getMota());

            // Thực thi câu lệnh
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Thêm lớp học thành công!");
            }
        } catch (SQLException e) {
            // Kiểm tra lỗi trùng khóa chính (lỗi code: 1062 trong MySQL)
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Lỗi: Mã lớp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm lớp học: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }

    public static void updateDe(Class2 cl) {
        String sql = "UPDATE LopHoc SET tenLop = ?, moTa = ? WHERE maLop = ?";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cl.getTenlop());
            pstmt.setString(2, cl.getMota());
            pstmt.setString(3, cl.getMalop());

            // Thực thi câu lệnh
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin lớp học thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy lớp học để cập nhật!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) { // Kiểm tra mã SQLState cho lỗi khóa ngoại
                JOptionPane.showMessageDialog(null, "Lỗi khóa ngoại: Không thể cập nhật lớp học do vi phạm ràng buộc khóa ngoại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thông tin lớp học: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }

    public static boolean deleteSub(String malop) {
        String sql = "DELETE FROM LopHoc WHERE malop = ?";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, malop);  // Đặt giá trị maLop cần xóa
            int affectedRows = pstmt.executeUpdate();  // Thực hiện câu lệnh DELETE

            return affectedRows > 0;
        } catch (SQLException e) {
            if ("2300".equals(e.getSQLState())) {
                System.out.println("Lỗi khóa ngoại :Không thể xóa môn học vì còn dữ liệu liên quan!");

            } else {
                System.err.println("SQL Exception: " + e.getMessage());
            }

            return false;
        }
    }

    public static List<Class2> getAllClasses() {
        List<Class2> classes = new ArrayList<>();
        String sql = "SELECT maLop, tenLop, moTa FROM LopHoc";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Class2 cl = new Class2();
                cl.malop = rs.getString("maLop");
                cl.tenlop = rs.getString("tenLop");
                cl.mota = rs.getString("moTa");
                classes.add(cl);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        return classes;
    }

}
