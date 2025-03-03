/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Class2;
import Model.Major22;
import Model.Student2;
import Model.Subject2;
import java.util.List;
import Util.jdbcHelper;

public class ListDAO {

    public List<Student2> searchStudents(String keyword) {
        String query = "SELECT sv.maSV, sv.tenSV, sv.maNganh, sv.gioiTinh, sv.tuoi, lh.tenLop "
                + "FROM SinhVien sv "
                + "JOIN LopHoc lh ON sv.maLop = lh.maLop "
                + "WHERE sv.maSV LIKE ? OR sv.tenSV LIKE ?";
        return jdbcHelper.executeQuery(query, rs -> new Student2(
                rs.getString("maSV"),
                rs.getString("tenSV"),
                rs.getString("maNganh"),
                rs.getBoolean("gioiTinh"),
                rs.getInt("tuoi"),
                rs.getString("tenLop")
        ), keyword, keyword);
    }

    // Tìm kiếm ngành học
    public List<Major22> searchMajor(String keyword) {
        String query = "SELECT maNganh, tenNganh, moTa FROM nganhhoc "
                + "WHERE maNganh LIKE ? OR tenNganh LIKE ?";
        return jdbcHelper.executeQuery(query, rs -> new Major22(
                rs.getString("maNganh"),
                rs.getString("tenNganh"),
                rs.getString("moTa")
        ), keyword, keyword);
    }

    // Tìm kiếm lớp học
    public List<Class2> searchClass(String keyword) {
        String query = "SELECT maLop, tenLop, moTa FROM lophoc "
                + "WHERE maLop LIKE ? OR tenLop LIKE ?";
        return jdbcHelper.executeQuery(query, rs -> new Class2(
                rs.getString("maLop"),
                rs.getString("tenLop"),
                rs.getString("moTa")
        ), keyword, keyword);
    }

    // Tìm kiếm môn học
    public List<Subject2> searchSubject(String keyword) {
        String query = "SELECT maMon, tenMon, moTa, diemQuaMon FROM monhoc "
                + "WHERE maMon LIKE ? OR tenMon LIKE ?";
        return jdbcHelper.executeQuery(query, rs -> new Subject2(
                rs.getString("maMon"),
                rs.getString("tenMon"),
                rs.getString("moTa"),
                rs.getFloat("diemQuaMon")
        ), keyword, keyword);
    }

//    public List<Point2> searchPoint(String keyword) {
//        List<Point2> point = new ArrayList<>();
//        String query = "SELECT d.maSV AS 'Student ID', "
//             + "sv.tenSV AS 'Student Name', "
//             + "d.maMon AS 'Subject ID', "
//             + "d.diemTrungBinh AS 'Avg Point', "
//             + "d.xepLoai AS 'Classification', "
//             + "d.trangThai AS 'Status' "
//             + "FROM diem d "
//             + "JOIN sinhvien sv ON d.maSV = sv.maSV "
//             + "WHERE d.maSV LIKE ? "
//             + "OR sv.tenSV LIKE ? "
//             + "OR d.maMon LIKE ?";
//        try (Connection conn = connection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, "%" + keyword + "%");
//            stmt.setString(2, "%" + keyword + "%");
//
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Point2 po = new Point2(
//                        rs.getString("maMon"),
//                        rs.getString("tenMon"),
//                        rs.getString("moTa"),
//                        rs.getFloat("diemQuaMon")
//                );
//                point.add(po);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return point;
//    }
}
