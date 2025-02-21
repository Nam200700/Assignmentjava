/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.jdbcHelper;
import Model.LichSuLopHoc;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 */
public class HistoryClassDAO {

    public static List<LichSuLopHoc> getLichSuLopHoc() {
        List<LichSuLopHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM LichSuLopHoc ORDER BY ngayChuyen DESC"; // Bỏ WHERE maSV = ?

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) { // Không cần truyền tham số
            while (rs.next()) {
                LichSuLopHoc hcl = new LichSuLopHoc();
                hcl.setId(rs.getString("id"));
                hcl.setMaSV(rs.getString("maSV"));
                hcl.setMaLopCu(rs.getString("maLopCu"));
                hcl.setMaLopMoi(rs.getString("maLopMoi"));
                hcl.setNgaychuyen(rs.getString("ngayChuyen")); // Giữ nguyên kiểu String
                hcl.setGhichu(rs.getString("ghiChu"));

                list.add(hcl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa lịch sử lớp học của một sinh viên
    public void deleteLichSuByMaSV(String maSV) {
        String sql = "DELETE FROM LichSuLopHoc WHERE maSV = ?";
        jdbcHelper.executeUpdate(sql, maSV);
    }
}
