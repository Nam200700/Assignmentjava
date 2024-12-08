/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.MajorandSubject;
/**
 *
 * @author ACER
 */
public class MajorandSubjectDAO2 {
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
    
    
     public static void insertDe(MajorandSubject mjas) {
    String sql = "INSERT INTO MonHocNganhHoc (maMon,maNganh) VALUES (?, ?)";
    
    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, mjas.mamon);            
        pstmt.setString(2, mjas.majorId);          
      
        // Thực thi câu lệnh
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Thêm môn học ngành học thành công!");
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi thêm môn học ngành học: " + e.getMessage());
        e.printStackTrace();
    }
}   
     
     public static List<MajorandSubject> getAllClasses() {
    List<MajorandSubject> classes = new ArrayList<>();
    String sql = "SELECT maMonnganh,maMon, maNganh FROM MonHocNganhHoc";

    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            MajorandSubject mj = new MajorandSubject();
            mj.MaMonHocNganhHoc = rs.getInt("maMonnganh");
            mj.mamon = rs.getString("maMon");
            mj.majorId = rs.getString("maNganh");
            classes.add(mj);
        }

    } catch (SQLException e) {
        System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        e.printStackTrace();
    }

    return classes;
}
}
