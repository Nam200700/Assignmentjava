/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Util.jdbcHelper;
import Model.LichSuNganhHoc;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author ACER
 */
public class HistoryMajorDAO {
    public static List<LichSuNganhHoc> getLichSuNganhHoc() {
        List<LichSuNganhHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM LichSuNganhHoc ORDER BY ngayChuyen DESC"; // Bỏ WHERE maSV = ?

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) { // Không cần truyền tham số
            while (rs.next()) {
                LichSuNganhHoc lsn = new LichSuNganhHoc();
                lsn.setId(rs.getString("id"));
                lsn.setMaSV(rs.getString("maSV"));
                lsn.setMaNganhCu(rs.getString("maNganhCu"));
                lsn.setMaNganhMoi(rs.getString("maNganhMoi"));
                lsn.setNgaychuyen(rs.getString("ngayChuyen")); // Giữ nguyên kiểu String
                lsn.setGhichu(rs.getString("ghiChu"));

                list.add(lsn);
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
