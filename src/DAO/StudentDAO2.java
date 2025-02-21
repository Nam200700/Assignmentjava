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
        String sqlGetMaxId = "SELECT MAX(maSV) FROM SinhVien";  // Lấy mã sinh viên lớn nhất
        String sqlGetMaLop = "SELECT maLop FROM LopHoc WHERE tenLop = ?";  // Lấy mã lớp theo tên lớp
        String sqlInsert = "INSERT INTO SinhVien (maSV, tenSV, maLop, maNganh, gioiTinh, tuoi) VALUES (?, ?, ?, ?, ?, ?)";

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
        try (ResultSet rs = jdbcHelper.executeQuery(sqlGetMaxId)) {
            if (rs.next()) {
                String maxMaSV = rs.getString(1);
                if (maxMaSV != null) {
                    int nextId = Integer.parseInt(maxMaSV.substring(2)) + 1;
                    newMaSV = String.format("TV%05d", nextId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Lấy mã lớp từ tên lớp của đối tượng student
        String tenLop = student.getTenLop();
        if (tenLop == null || tenLop.trim().isEmpty()) {
            System.out.println("Tên lớp không hợp lệ!");
            return false;
        }

        String maLop = null;
        try (ResultSet rs = jdbcHelper.executeQuery(sqlGetMaLop, tenLop)) {
            if (rs.next()) {
                maLop = rs.getString("maLop");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (maLop == null) {
            System.out.println("Không tìm thấy mã lớp cho tên lớp: " + tenLop);
            return false;
        }

        // Cập nhật thông tin sinh viên mới
        return jdbcHelper.executeUpdate(sqlInsert, newMaSV, student.getTensinhvien(), maLop, student.getMaNganh(), student.isGioitinh(), student.getTuoi()) > 0;
    }

    public static boolean updateStudent(Student2 student) {
        // Kiểm tra thông tin hợp lệ
        if (student.getTensinhvien() == null || student.getTensinhvien().isEmpty()
                || student.getMaNganh() == null || student.getMaNganh().isEmpty()
                || student.getMalop() == null || student.getMalop().isEmpty()
                || student.getMasinhvien() == null || student.getMasinhvien().isEmpty()) {
            System.out.println("Thông tin không hợp lệ!");
            return false;
        }

        // Kiểm tra mã lớp tồn tại
        String sqlCheckMaLop = "SELECT 1 FROM LopHoc WHERE maLop = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sqlCheckMaLop, student.getMalop())) {
            if (!rs.next()) {
                System.out.println("Mã lớp không tồn tại!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Cập nhật thông tin sinh viên
        String sql = "UPDATE SinhVien SET tenSV = ?, maLop = ?, maNganh = ?, gioiTinh = ?, tuoi = ? WHERE maSV = ?";
        return jdbcHelper.executeUpdate(sql, student.getTensinhvien(), student.getMalop(), student.getMaNganh(), student.isGioitinh(), student.getTuoi(), student.getMasinhvien()) > 0;
    }

    public boolean isMaLopExist(String maLop) {
        String sql = "SELECT COUNT(*) FROM LopHoc WHERE maLop = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, maLop)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Lấy giá trị COUNT(*) từ cột đầu tiên
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getMaLopFromTenLop(String tenLop) {
        String sql = "SELECT maLop FROM LopHoc WHERE tenLop = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, tenLop)) {
            if (rs.next()) {
                return rs.getString("maLop");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy mã lớp
    }

    public boolean checkStudentExists(String maSV) {
        String sql = "SELECT COUNT(*) FROM SinhVien WHERE maSV = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, maSV)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Lấy giá trị COUNT(*) từ cột đầu tiên
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteST(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE maSV = ?";

        return jdbcHelper.executeUpdate(sql, maSinhVien) > 0;  // Đặt giá trị maMon cần xóa
    }
}
