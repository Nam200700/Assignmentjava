/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.MajorandSubject;
import Util.jdbcHelper;

/**
 *
 * @author ACER
 */
public class MajorandSubjectDAO2 {

    public static void insert(MajorandSubject mjas) {
        String sql = "INSERT INTO MonHocNganhHoc (maMon, maNganh) VALUES (?, ?)";
        int result = jdbcHelper.executeUpdate(sql, mjas.getMamon(), mjas.getMajorId());

        if (result > 0) {
            System.out.println("Thêm môn học ngành học thành công!");
        } else {
            System.out.println("Lỗi khi thêm môn học ngành học!");
        }
    }

    public static void update(MajorandSubject ma) {
        String sql = "UPDATE MonHocNganhHoc SET maMon = ?, maNganh = ? WHERE maMonnganh = ?";
        int result = jdbcHelper.executeUpdate(sql, ma.getMamon(), ma.getMajorId(), ma.getMaMonHocNganhHoc());

        if (result > 0) {
            System.out.println("Cập nhật thông tin môn học ngành học thành công!");
        } else {
            System.out.println("Không tìm thấy môn học ngành học cần cập nhật!");
        }
    }

    public static boolean delete(String maMonnganh) {
        String sql = "DELETE FROM MonHocNganhHoc WHERE maMonnganh = ?";
        int result = jdbcHelper.executeUpdate(sql, maMonnganh);

        if (result > 0) {
            return true;
        } else {
            System.out.println("Không thể xóa môn học ngành học!");
            return false;
        }
    }

    public static List<MajorandSubject> getAll() {
        List<MajorandSubject> classes = new ArrayList<>();
        String sql = "SELECT maMonnganh, maMon, maNganh FROM MonHocNganhHoc";

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                MajorandSubject mj = new MajorandSubject();
                mj.setMaMonHocNganhHoc(rs.getInt("maMonnganh"));
                mj.setMamon(rs.getString("maMon"));
                mj.setMajorId(rs.getString("maNganh"));
                classes.add(mj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

}
