/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Subject2;
import Util.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class SubjectDAO2 {

    public static void insertSub(Subject2 sb) {
        String sql = "INSERT INTO MonHoc (maMon, tenMon, moTa, diemQuaMon) VALUES (?, ?, ?, ?)";

        jdbcHelper.executeUpdate(sql, sb.getMamon(), sb.getTenmon(), sb.getMota(), sb.getDiemQuaMon()); // Kiểm tra lỗi trùng khóa chính (lỗi code: 1062 trong MySQL)
        JOptionPane.showMessageDialog(null, "Thêm môn học thành công!");
    }

    public static void updateSub(Subject2 sb) {
        String sql = "UPDATE MonHoc SET tenMon = ?, moTa = ?, diemQuaMon = ? WHERE maMon = ?";

        jdbcHelper.executeUpdate(sql, sb.getTenmon(), sb.getMota(), sb.getDiemQuaMon(), sb.getMamon());
        System.out.println("Cập nhật thông tin môn học thành công!");
    }

    public static boolean deleteSub(String mamon) {
        String sql = "DELETE FROM MonHoc WHERE maMon = ?";

        int affectedRows = jdbcHelper.executeUpdate(sql, mamon);  // Thực hiện câu lệnh DELETE
        return affectedRows > 0;
    }

    public static List<Subject2> getAllSubject() {
        String sql = "SELECT maMon, tenMon, moTa, diemQuaMon FROM MonHoc";
        List<Subject2> subjects = new ArrayList<>();

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                // Lấy các giá trị từ ResultSet
                Subject2 subject = new Subject2();
                subject.setMamon(rs.getString("maMon"));
                subject.setTenmon(rs.getString("tenMon"));
                subject.setMota(rs.getString("moTa"));
                subject.setDiemQuaMon(rs.getFloat("diemQuaMon"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        return subjects;
    }

}
