/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Class2;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class ClassDAO2 {

    public static void insert(Class2 cl) {
        String sql = "INSERT INTO LopHoc (maLop, tenLop, moTa) VALUES (?, ?, ?)";
        int result = jdbcHelper.executeUpdate(sql, cl.getMalop(), cl.getTenlop(), cl.getMota());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Thêm lớp học thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm lớp học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void update(Class2 cl) {
        String sql = "UPDATE LopHoc SET tenLop = ?, moTa = ? WHERE maLop = ?";
        int result = jdbcHelper.executeUpdate(sql, cl.getTenlop(), cl.getMota(), cl.getMalop());

        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy lớp học!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean delete(String malop) {
        String sql = "DELETE FROM LopHoc WHERE maLop = ?";
        int result = jdbcHelper.executeUpdate(sql, malop);

        if (result > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không thể xóa lớp học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static List<Class2> getAll() {
        List<Class2> classes = new ArrayList<>();
        String sql = "SELECT maLop, tenLop, moTa FROM LopHoc";

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                Class2 cl = new Class2();
                cl.malop = rs.getString("maLop");
                cl.tenlop = rs.getString("tenLop");
                cl.mota = rs.getString("moTa");
                classes.add(cl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

}
