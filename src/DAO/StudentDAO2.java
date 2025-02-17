/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Student2;
import Util.jdbcHelper;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class StudentDAO2 {

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

        // Lấy mã sinh viên lớn nhất trong bảng
        String newMaSV = "TV00001";  // Mặc định nếu không có sinh viên nào trong bảng
        List<Map<String, Object>> result = (List<Map<String, Object>>) jdbcHelper.executeQuery(sqlGetMaxId);  // Giả sử executeQuery trả về List<Map<String, Object>>
        if (result != null && !result.isEmpty()) {
            String maxMaSV = (String) result.get(0).get("MAX(maSV)"); // Lấy giá trị cột "MAX(maSV)"
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
        List<Map<String, Object>> maLopResult = (List<Map<String, Object>>) jdbcHelper.executeQuery(sqlGetMaLop, tenLop); // Truy vấn mã lớp
        if (maLopResult == null || maLopResult.isEmpty()) {
            System.out.println("Không tìm thấy mã lớp cho tên lớp: " + tenLop);
            return false;  // Nếu không tìm thấy mã lớp, trả về false
        }
        String maLop = (String) maLopResult.get(0).get("maLop"); // Lấy mã lớp từ kết quả
        // Cập nhật thông tin sinh viên mới
        return jdbcHelper.executeUpdate(sqlInsert, newMaSV, student.getTensinhvien(), maLop, student.getMaNganh(), student.isGioitinh(), student.getTuoi()) > 0;

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
                || student.getMalop() == null || student.getMalop().isEmpty()
                || student.getMasinhvien() == null || student.getMasinhvien().isEmpty()) {
            System.out.println("Thông tin không hợp lệ!");
            return false;
        }

        String sql = "UPDATE SinhVien SET tenSV = ?, maLop = ?, maNganh = ?, gioiTinh = ?, tuoi = ? WHERE maSV = ?";  // Thay maMon thành maNganh
        return jdbcHelper.executeUpdate(sql, student.getTensinhvien(), student.getMalop(), student.getMaNganh(), student.isGioitinh(), student.getTuoi(), student.getMasinhvien()) > 0; // Trả về false nếu có lỗi
    }

    public static String getMaLopFromTenLop(String tenLop) {
        String sql = "SELECT maLop FROM LopHoc WHERE tenLop = ?";
        List<Map<String, Object>> result = (List<Map<String, Object>>) jdbcHelper.executeQuery(sql, tenLop);

        if (result != null && !result.isEmpty()) {
            return (String) result.get(0).get("maLop");
        }

        return null;  // Trả về null nếu không tìm thấy mã lớp
    }

    public boolean isMaLopExist(String maLop) {
        String sql = "SELECT COUNT(*) FROM LopHoc WHERE maLop = ?";
        // Kiểm tra xem kết quả có đúng và số lượng dòng trả về có lớn hơn 0 không
        List<Map<String, Object>> result = (List<Map<String, Object>>) jdbcHelper.executeQuery(sql, maLop);
        if (result != null && !result.isEmpty()) {
            // Lấy giá trị của COUNT(*) từ kết quả trả về (giả sử là kết quả đầu tiên và duy nhất)
            int count = (int) result.get(0).get("COUNT(*)");  // Hoặc tên cột COUNT(*) tùy theo cấu trúc của JDBC helper
            return count > 0;
        }
        return false;
    }

    public boolean checkStudentExists(String maSV) {
        String sql = "SELECT COUNT(*) FROM SinhVien WHERE maSV = ?";
        // Kiểm tra xem kết quả có đúng và số lượng dòng trả về có lớn hơn 0 không
        List<Map<String, Object>> result = (List<Map<String, Object>>) jdbcHelper.executeQuery(sql, maSV);
        if (result != null && !result.isEmpty()) {
            // Lấy giá trị của COUNT(*) từ kết quả trả về (giả sử là kết quả đầu tiên và duy nhất)
            int count = (int) result.get(0).get("COUNT(*)");  // Hoặc tên cột COUNT(*) tùy theo cấu trúc của JDBC helper
            return count > 0;
        }
        return false;  // Không tồn tại hoặc lỗi
    }

    public static boolean deleteST(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE maSV = ?";

        return jdbcHelper.executeUpdate(sql, maSinhVien) > 0;  // Đặt giá trị maMon cần xóa
    }

}
