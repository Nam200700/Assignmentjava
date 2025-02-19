/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.jdbcHelper;
import com.mysql.cj.jdbc.CallableStatement;
import com.raven.chart.ModelChart;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class ThongKeDao {

    public static double getXuatSacPercentage() {
        String sql = "SELECT COUNT(*) FROM Diem WHERE xepLoai = 'Xuất sắc'";
        String totalSql = "SELECT COUNT(*) FROM Diem";
        int countXuatSac = 0;
        int total = 1; // Để tránh chia cho 0

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            if (rs.next()) {
                countXuatSac = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet rs = jdbcHelper.executeQuery(totalSql)) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (countXuatSac * 100.0) / total;
    }

    public static double getGioiPercentage() {
        String sql = "SELECT COUNT(*) FROM Diem WHERE xepLoai = 'Giỏi'";
        String totalSql = "SELECT COUNT(*) FROM Diem";
        int countXuatSac = 0;
        int total = 1; // Để tránh chia cho 0

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            if (rs.next()) {
                countXuatSac = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet rs = jdbcHelper.executeQuery(totalSql)) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (countXuatSac * 100.0) / total;
    }

    public static double getKhaPercentage() {
        String sql = "SELECT COUNT(*) FROM Diem WHERE xepLoai = 'Khá'";
        String totalSql = "SELECT COUNT(*) FROM Diem";
        int countXuatSac = 0;
        int total = 1; // Để tránh chia cho 0

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            if (rs.next()) {
                countXuatSac = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet rs = jdbcHelper.executeQuery(totalSql)) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (countXuatSac * 100.0) / total;
    }

    public static double getTrungbinhYeuPercentage() {
        String sql = "SELECT COUNT(*) FROM Diem WHERE xepLoai IN ('Trung bình', 'Yếu')";
        String totalSql = "SELECT COUNT(*) FROM Diem";
        int countXuatSac = 0;
        int total = 1; // Để tránh chia cho 0

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            if (rs.next()) {
                countXuatSac = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (ResultSet rs = jdbcHelper.executeQuery(totalSql)) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (countXuatSac * 100.0) / total;
    }

    public static List<ModelChart> getChartData() {
        List<ModelChart> dataList = new ArrayList<>();

        String sql = "SELECT sv.maLop, "
                + "SUM(CASE WHEN d.xepLoai = 'Xuất sắc' THEN 1 ELSE 0 END) AS soLuongXuatSac, "
                + "SUM(CASE WHEN d.xepLoai = 'Giỏi' THEN 1 ELSE 0 END) AS soLuongGioi, "
                + "SUM(CASE WHEN d.xepLoai = 'Khá' THEN 1 ELSE 0 END) AS soLuongKha, "
                + "SUM(CASE WHEN d.xepLoai = 'Trung bình' THEN 1 ELSE 0 END) AS soLuongTrungBinh, "
                + "SUM(CASE WHEN d.xepLoai = 'Yếu' THEN 1 ELSE 0 END) AS soLuongYeu "
                + "FROM Diem d "
                + "JOIN SinhVien sv ON d.maSV = sv.maSV "
                + "GROUP BY sv.maLop "
                + "ORDER BY soLuongXuatSac DESC "
                + "LIMIT 4";

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                String maLop = rs.getString(1);
                double[] values = {
                    rs.getDouble(2), // Xuất sắc
                    rs.getDouble(3), // Giỏi
                    rs.getDouble(4), // Khá
                    rs.getDouble(5), // Trung bình
                    rs.getDouble(6) // Yếu
                };
                dataList.add(new ModelChart(maLop, values));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

}
