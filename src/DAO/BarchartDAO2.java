/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ACER
 */
public class BarchartDAO2 {

    private JPanel barchart;

    public BarchartDAO2() {
        this.barchart = barchart;
    }

    public static void showbarchart(JPanel barchart, String major) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Kết nối đến cơ sở dữ liệu MySQL
        String url = "jdbc:mysql://localhost:3306/assjava3";
        String user = "root";
        String password = "0359910800";

        try {
            // Kết nối đến cơ sở dữ liệu
            Connection con = DriverManager.getConnection(url, user, password);

            // Câu lệnh SQL với tham số ngành học
            String query = """
               SELECT 
                            nh.maNganh AS MaNganh,
                            nh.tenNganh AS TenNganh,
                            COUNT(CASE WHEN d.diemTrungBinh < 5 THEN 1 END) * 100 / COUNT(*) AS PhanTramDuoi5,
                            COUNT(CASE WHEN d.diemTrungBinh >= 5 AND d.diemTrungBinh < 6.5 THEN 1 END) * 100 / COUNT(*) AS PhanTramTu5Den6_5,
                            COUNT(CASE WHEN d.diemTrungBinh >= 6.5 AND d.diemTrungBinh < 8 THEN 1 END) * 100 / COUNT(*) AS PhanTramTu6_5Den8,
                            COUNT(CASE WHEN d.diemTrungBinh >= 8 AND d.diemTrungBinh < 9 THEN 1 END) * 100 / COUNT(*) AS PhanTramTu8Den9,
                            COUNT(CASE WHEN d.diemTrungBinh >= 9 THEN 1 END) * 100 / COUNT(*) AS PhanTramTren9,
                            SUM(CASE WHEN mh.maMon IN (
                                SELECT DISTINCT mh2.maMon 
                                FROM MonHoc mh2 
                                JOIN NganhHoc_MonHoc nhmh ON mh2.maMon = nhmh.maMon
                                WHERE nhmh.maNganh = nh.maNganh
                            ) THEN d.diemTrungBinh ELSE 0 END) AS TongDiemTheoNganh
                        FROM 
                            NganhHoc nh
                        JOIN 
                            SinhVien sv ON nh.maNganh = sv.maNganh
                        JOIN 
                            Diem d ON sv.maSV = d.maSV
                        JOIN 
                            MonHoc mh ON d.maMon = mh.maMon
                        GROUP BY 
                            nh.maNganh, nh.tenNganh;
            """;

            // Tạo PreparedStatement và truyền tham số ngành học
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, major);  // Truyền giá trị ngành học vào câu lệnh SQL

            // Thực thi câu truy vấn và lấy kết quả
            ResultSet rs = pstmt.executeQuery();

            // Xử lý kết quả
            if (rs.next()) {
                double phanTramDuoi5 = rs.getDouble("PhanTramDuoi5");
                double phanTramTren65 = rs.getDouble("PhanTramTren65");
                double phanTramTren8 = rs.getDouble("PhanTramTren8");
                double phanTramXuatSac = rs.getDouble("PhanTramXuatSac");

                // Thêm dữ liệu vào dataset
                dataset.addValue(phanTramDuoi5, "Phần trăm", "Dưới 5");
                dataset.addValue(phanTramTren65, "Phần trăm", "Trên 6.5");
                dataset.addValue(phanTramTren8, "Phần trăm", "Trên 8");
                dataset.addValue(phanTramXuatSac, "Phần trăm", "Xuất sắc (trên 9)");
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Tạo biểu đồ cột từ dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê phần trăm sinh viên theo mức điểm",
                "Mức điểm",
                "Phần trăm (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Tạo ChartPanel để hiển thị biểu đồ
        ChartPanel chartPanelToDisplay = new ChartPanel(barChart);
        chartPanelToDisplay.setPreferredSize(new Dimension(380, 400));

        // Cập nhật lại JPanel được truyền vào
        barchart.removeAll();
        barchart.setLayout(new BorderLayout());
        barchart.add(chartPanelToDisplay, BorderLayout.CENTER);
        barchart.revalidate();
        barchart.repaint();
    }
    // Hiển thị biểu đồ dựa trên dữ liệu thống kê

    public static void showbarchartWithData(JPanel barchartPanel, double under5, double between5and6_5, double between6_5and8, double between8and9, double above9) {
        // Tạo một dataset để lưu dữ liệu cho biểu đồ
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Thêm dữ liệu vào dataset với các nhóm điểm tương ứng
        dataset.addValue(under5, "Phần trăm", "< 5"); // Nhóm điểm dưới 5
        dataset.addValue(between5and6_5, "Phần trăm", "5 - 6.5"); // Nhóm điểm từ 5 đến 6.5
        dataset.addValue(between6_5and8, "Phần trăm", "6.5 - 8"); // Nhóm điểm từ 6.5 đến 8
        dataset.addValue(between8and9, "Phần trăm", "8 - 9"); // Nhóm điểm từ 8 đến 9
        dataset.addValue(above9, "Phần trăm", "> 9"); // Nhóm điểm trên 9

        // Tạo biểu đồ cột (Bar Chart) từ dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê điểm theo ngành", // Tiêu đề biểu đồ
                "Nhóm điểm", // Trục X
                "Phần trăm", // Trục Y
                dataset, // Dữ liệu
                PlotOrientation.VERTICAL, // Hướng biểu đồ
                true, // Hiển thị chú thích (legend)
                true, // Hiển thị công cụ (tooltips)
                false // Không cần URL
        );

        // Tạo một ChartPanel để chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(500, 400)); // Kích thước của biểu đồ

        // Cập nhật JPanel để hiển thị biểu đồ
        barchartPanel.removeAll(); // Xóa tất cả nội dung cũ của JPanel
        barchartPanel.setLayout(new BorderLayout()); // Đặt layout là BorderLayout
        barchartPanel.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ vào giữa JPanel
        barchartPanel.revalidate(); // Làm mới lại giao diện JPanel
        barchartPanel.repaint(); // Vẽ lại JPanel để hiển thị biểu đồ
    }
}
