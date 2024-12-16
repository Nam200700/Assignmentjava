/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Major22;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class MajorDAO2 {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/qlsv"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "tranhainam123"; // Đổi mật khẩu của bạn nếu cần

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

    // add 
    public static void insertMajor(Major22 ma) {
        String sql = "INSERT INTO nganhhoc (maNganh, tenNganh, moTa) VALUES (?, ?, ?)";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ma.getMajorID());
            pstmt.setString(2, ma.getMajorName());
            pstmt.setString(3, ma.getNote());

            // Thực thi câu lệnh
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Thêm ngành học thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            // Kiểm tra lỗi trùng khóa chính (lỗi code: 1062 trong MySQL)
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Lỗi: Mã ngành học đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm ngành học: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }

    // update
    public static void updateMajor(Major22 ma) {
        String sql = "UPDATE nganhhoc SET tenNganh = ?, moTa = ? WHERE maNganh = ?";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ma.getMajorName());
            pstmt.setString(2, ma.getNote());
            pstmt.setString(3, ma.getMajorID());

            // Thực thi câu lệnh
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thông tin lớp học thành công!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin lớp học: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // delete
    public static boolean deleteMajor(String malop) {
        String sql = "DELETE FROM nganhhoc WHERE maNganh = ?";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, malop);  // Đặt giá trị maLop cần xóa
            int affectedRows = pstmt.executeUpdate();  // Thực hiện câu lệnh DELETE

            return affectedRows > 0;
        } catch (SQLException e) {
            if ("2300".equals(e.getSQLState())) {
                System.out.println("Lỗi khóa ngoại :Không thể xóa ngành học vì còn dữ liệu liên quan!");

            } else {
                System.err.println("SQL Exception: " + e.getMessage());
            }

            return false;
        }
    }

    public static List<Major22> getAllMajor() {
        List<Major22> major = new ArrayList<>();
        String sql = "SELECT maNganh, tenNganh, moTa FROM nganhhoc";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Major22 ma = new Major22();
                ma.MajorID = rs.getString("maNganh");
                ma.MajorName = rs.getString("tenNganh");
                ma.Note = rs.getString("moTa");
                major.add(ma);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        return major;
    }
}
