/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import javax.swing.JLabel;
import Util.jdbcHelper;

/**
 *
 * @author huynh
 */
public class CountClassandStudentDAO {
    // Cập nhật số lượng sinh viên

    public static void updateStudentCount(JLabel label) {
        int count = jdbcHelper.getCountFromTable("sinhvien", "tongsv");
        label.setText(count > 0 ? "Số lượng sinh viên: " + count : "Lỗi kết nối cơ sở dữ liệu!");
    }

    // Cập nhật số lượng lớp học
    public static void updateClassCount(JLabel label) {
        int count = jdbcHelper.getCountFromTable("lophoc", "tonglophoc");
        label.setText(count > 0 ? "Số lượng lớp: " + count : "Lỗi kết nối cơ sở dữ liệu!");
    }
}
