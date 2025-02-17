/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Major22;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import Util.jdbcHelper;

/**
 *
 * @author ACER
 */
public class MajorDAO2 {

    public static void insert(Major22 ma) {
        String sql = "INSERT INTO nganhhoc (maNganh, tenNganh, moTa) VALUES (?, ?, ?)";
        int result = jdbcHelper.executeUpdate(sql, ma.getMajorID(), ma.getMajorName(), ma.getNote());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm ngành học thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm ngành học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(Major22 ma) {
        String sql = "UPDATE nganhhoc SET tenNganh = ?, moTa = ? WHERE maNganh = ?";
        int result = jdbcHelper.executeUpdate(sql, ma.getMajorName(), ma.getNote(), ma.getMajorID());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin ngành học thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy ngành học!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String maNganh) {
        String sql = "DELETE FROM nganhhoc WHERE maNganh = ?";
        int result = jdbcHelper.executeUpdate(sql, maNganh);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa ngành học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Major22> getAll() {
        List<Major22> majors = new ArrayList<>();
        String sql = "SELECT maNganh, tenNganh, moTa FROM nganhhoc";

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                Major22 ma = new Major22();
                ma.setMajorID(rs.getString("maNganh"));
                ma.setMajorName(rs.getString("tenNganh"));
                ma.setNote(rs.getString("moTa"));
                majors.add(ma);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return majors;
    }

}
