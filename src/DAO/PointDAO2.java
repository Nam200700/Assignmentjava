/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Point2;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Util.jdbcHelper;

/**
 *
 * @author ACER
 */
public class PointDAO2 {

    // Thêm điểm
    public boolean addPoint(Point2 point) {
        String sqlGetDiemQuaMon = "SELECT diemQuaMon FROM MonHoc WHERE maMon = ?";
        String sqlInsert = "INSERT INTO Diem (maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, xepLoai, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Lấy điểm qua môn
            ResultSet rs = jdbcHelper.executeQuery(sqlGetDiemQuaMon, point.getMaMon());
            double diemQuaMon = 0;
            if (rs.next()) {
                diemQuaMon = rs.getDouble("diemQuaMon");
            }

            // Tính điểm trung bình
            double diemTrungBinh = (point.getDiemThuongXuyen() * 2 + point.getDiemLab() * 3 + point.getDiemAssignment() * 5) / 10;

            // Xác định trạng thái
            String trangThai = diemTrungBinh >= diemQuaMon ? "Đạt" : "Rớt";

            // Thêm điểm vào bảng
            int result = jdbcHelper.executeUpdate(sqlInsert, point.getMaDiem(), point.getMaSV(), point.getMaMon(),
                    point.getDiemThuongXuyen(), point.getDiemLab(), point.getDiemAssignment(), point.getXepLoai(), trangThai);

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePoint(Point2 point) {
        String sql = "UPDATE Diem SET maSV = ?, maMon = ?, diemThuongXuyen = ?, diemLab = ?, diemAssignment = ?, xepLoai = ?, trangThai = ? WHERE maDiem = ?";
        int result = jdbcHelper.executeUpdate(sql, point.getMaSV(), point.getMaMon(), point.getDiemThuongXuyen(),
                point.getDiemLab(), point.getDiemAssignment(), point.getXepLoai(), point.getTrangThai(), point.getMaDiem());
        if (result > 0) {
            System.out.println("Cập nhật điểm thành công!");
            return true;
        } else {
            System.out.println("Không tìm thấy điểm cần cập nhật!");
            return false;
        }
    }

    public boolean deletePoint(String madiem) {
        String sql = "DELETE FROM Diem WHERE maDiem = ?";
        int result = jdbcHelper.executeUpdate(sql, madiem);
        if (result > 0) {
            return true;
        } else {
            System.out.println("Không tìm thấy điểm cần xóa!");
            return false;
        }
    }

    // Kiểm tra xem điểm có tồn tại không
    public boolean checkPointExists(String maDiem) {
        String sql = "SELECT COUNT(*) FROM Diem WHERE maDiem = ?";
        try (ResultSet rs = jdbcHelper.executeQuery(sql, maDiem)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy tất cả điểm
    public List<Point2> getAllPoints() {
        String sql = "SELECT maDiem, maSV, maMon, diemTrungBinh, xepLoai, trangThai FROM Diem";
        List<Point2> points = new ArrayList<>();
        List<Point2> result = jdbcHelper.executeQuery(sql, rs -> {
            Point2 point = new Point2();
            point.setMaDiem(rs.getString("maDiem"));
            point.setMaSV(rs.getString("maSV"));
            point.setMaMon(rs.getString("maMon"));
            point.setDiemTrungBinh(rs.getDouble("diemTrungBinh"));
            point.setXepLoai(rs.getString("xepLoai"));
            point.setTrangThai(rs.getString("trangThai"));
            return point;
        });
        return result;
    }

    // Lấy chi tiết điểm
    public Map<String, Double> getDetailPoints(String maDiem) {
        Map<String, Double> detailPoints = new HashMap<>();
        String query = "SELECT diemThuongXuyen, diemLab, diemAssignment FROM diem WHERE maDiem = ?";
        try {
            ResultSet rs = jdbcHelper.executeQuery(query, maDiem);
            if (rs.next()) {
                detailPoints.put("diemThuongXuyen", rs.getDouble("diemThuongXuyen"));
                detailPoints.put("diemLab", rs.getDouble("diemLab"));
                detailPoints.put("diemAssignment", rs.getDouble("diemAssignment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailPoints;
    }

    // Lấy điểm qua môn
    public double getDiemQuaMon(String maMon) throws Exception {
        String query = "SELECT diemQuaMon FROM MonHoc WHERE maMon = ?";
        try {
            ResultSet rs = jdbcHelper.executeQuery(query, maMon);
            if (rs.next()) {
                return rs.getDouble("diemQuaMon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 5.0; // Trả về mặc định là 5.0 nếu không tìm thấy hoặc có lỗi
    }
}
