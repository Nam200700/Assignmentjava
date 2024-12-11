/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Student2;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 */
public class StudentDAO2 {

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

    // Phương thức kết nối
    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // Phương thức thêm sinh viên
    public static boolean addStudent(Student2 student) {
        String sqlGetMaxId = "SELECT MAX(maSV) FROM SinhVien";  // Truy vấn để lấy mã sinh viên lớn nhất
        String sqlGetMaLop = "SELECT maLop FROM LopHoc WHERE tenLop = ?";  // Truy vấn để lấy mã lớp theo tên lớp
        String sqlInsert = "INSERT INTO SinhVien (maSV, tenSV, maLop, maNganh, gioiTinh, tuoi) VALUES (?, ?, ?, ?, ?, ?)";  // Thay maMon thành maNganh

        // Kiểm tra dữ liệu đầu vào
        if (student.getTensinhvien().isEmpty()
                || student.getMaNganh().isEmpty()
                || student.getMalop().isEmpty()
                || student.getTuoi() <= 0) {
            System.out.println("Thông tin không hợp lệ!");
            return false;
        }

        try (Connection conn = connection(); PreparedStatement psGetMax = conn.prepareStatement(sqlGetMaxId); PreparedStatement psGetMaLop = conn.prepareStatement(sqlGetMaLop); PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

            // Lấy mã sinh viên lớn nhất trong bảng
            ResultSet rs = psGetMax.executeQuery();
            String newMaSV = "TV00001";  // Mặc định nếu không có sinh viên nào trong bảng
            if (rs.next()) {
                String maxMaSV = rs.getString(1);
                if (maxMaSV != null) {
                    int nextId = Integer.parseInt(maxMaSV.substring(2)) + 1;  // Lấy số sau "TV"
                    newMaSV = String.format("TV%05d", nextId);  // Tạo mã sinh viên mới, ví dụ "TV00002"
                }
            }

            // Lấy mã lớp từ tên lớp của đối tượng student
            String tenLop = student.getTenLop();  // Lấy tên lớp từ đối tượng student
            if (tenLop == null || tenLop.trim().isEmpty()) {
                System.out.println("Tên lớp không hợp lệ!");
                return false;  // Nếu tên lớp không hợp lệ, trả về false
            }

            psGetMaLop.setString(1, tenLop);
            ResultSet rsLop = psGetMaLop.executeQuery();
            String maLop = null;
            if (rsLop.next()) {
                maLop = rsLop.getString("maLop");  // Lấy mã lớp từ kết quả truy vấn
            }

            if (maLop == null) {
                System.out.println("Không tìm thấy mã lớp cho tên lớp: " + tenLop);
                return false;  // Nếu không tìm thấy mã lớp, trả về false
            }

            // Cập nhật thông tin sinh viên mới
            psInsert.setString(1, newMaSV);  // Sử dụng mã sinh viên mới
            psInsert.setString(2, student.getTensinhvien());
            psInsert.setString(3, maLop);  // Sử dụng mã lớp đã tìm được
            psInsert.setString(4, student.getMaNganh());  // Sử dụng mã ngành
            psInsert.setBoolean(5, student.isGioitinh());
            psInsert.setInt(6, student.getTuoi());

            // Thực thi câu lệnh INSERT và kiểm tra kết quả
            return psInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // In ra thông báo lỗi chi tiết
        }
        return false;
    }

    public boolean updateStudent(Student2 student) {
        // Kiểm tra mã lớp tồn tại trong bảng lophoc
        if (!isMaLopExist(student.getMalop())) {
            System.out.println("Mã lớp không tồn tại!");
            return false;
        }

        // Kiểm tra thông tin hợp lệ
        if (student.getTensinhvien() == null || student.getTensinhvien().isEmpty()
                || student.getMaNganh() == null || student.getMaNganh().isEmpty()
                || // Thay maMon thành maNganh
                student.getMalop() == null || student.getMalop().isEmpty()
                || student.getMasinhvien() == null || student.getMasinhvien().isEmpty()) {
            System.out.println("Thông tin không hợp lệ!");
            return false;
        }

        String sql = "UPDATE SinhVien SET tenSV = ?, maLop = ?, maNganh = ?, gioiTinh = ?, tuoi = ? WHERE maSV = ?";  // Thay maMon thành maNganh
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getTensinhvien());
            ps.setString(2, student.getMalop());
            ps.setString(3, student.getMaNganh());  // Cập nhật mã ngành thay vì mã môn
            ps.setBoolean(4, student.isGioitinh());
            ps.setInt(5, student.getTuoi());
            ps.setString(6, student.getMasinhvien());

            return ps.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

// đếm xem có bao nhiêu mã lớp để xét điều kiện 
    public boolean isMaLopExist(String maLop) {
        String sql = "SELECT COUNT(*) FROM LopHoc WHERE maLop = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// Kiểm tra sinh viên có tồn tại không

    public static String getMaLopFromTenLop(String tenLop) {
        String maLop = null;
        // Truy vấn mã lớp từ bảng LopHoc theo tên lớp
        String sql = "SELECT maLop FROM LopHoc WHERE tenLop = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLop);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maLop = rs.getString("maLop");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maLop;
    }

    public boolean checkStudentExists(String maSV) {
        String sql = "SELECT COUNT(*) FROM SinhVien WHERE maSV = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra mã sinh viên: " + e.getMessage());
        }
        return false; // Không tồn tại hoặc lỗi
    }

    public static boolean deleteST(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE maSV = ?";

        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien);  // Đặt giá trị maMon cần xóa
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
}
