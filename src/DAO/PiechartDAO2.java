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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ACER
 */
public class PiechartDAO2 {

    private JPanel piechart;

    public PiechartDAO2() {
        this.piechart = piechart;
    }

    public static void showPiechart(JPanel piechart) {
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        // Thông tin kết nối cơ sở dữ liệu
        String url = "jdbc:mysql://localhost:3306/assjava3"; // Tên database của bạn
        String user = "root"; // Username của MySQL
        String password = "0359910800"; // Password của MySQL

        try {
            // Kết nối cơ sở dữ liệu
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            // Truy vấn dữ liệu - Lấy mã ngành và tên ngành cùng số lượng sinh viên
            String query = "SELECT n.tenNganh, COUNT(sv.maSV) AS soLuongSinhVien "
                    + "FROM SinhVien sv "
                    + "JOIN NganhHoc n ON sv.maNganh = n.maNganh "
                    + "GROUP BY n.tenNganh";
            ResultSet rs = stmt.executeQuery(query);

            // Thêm dữ liệu từ ResultSet vào dataset
            while (rs.next()) {
                String tenNganh = rs.getString("tenNganh"); // Tên ngành
                int soLuong = rs.getInt("soLuongSinhVien"); // Số lượng sinh viên
                pieDataset.setValue("Ngành: " + tenNganh, soLuong); // Thêm dữ liệu vào PieDataset
            }

            // Đóng tài nguyên
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Tạo biểu đồ hình tròn
        JFreeChart chart = ChartFactory.createPieChart(
                "Biểu đồ số lượng sinh viên theo ngành", // Tiêu đề biểu đồ
                pieDataset, // Dataset
                true, // Hiển thị chú thích
                true, // Hiển thị công cụ
                false // Không sử dụng URL
        );

        // Tùy chỉnh nhãn hiển thị: hiển thị tên ngành và số lượng sinh viên
        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {1} sinh viên", // {0} = Tên ngành, {1} = Số lượng, {2} = Phần trăm
                new DecimalFormat("0"), // Định dạng số lượng
                new DecimalFormat("0%") // Định dạng phần trăm (không dùng nhưng cần thiết)
        );
        ((org.jfree.chart.plot.PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Hiển thị biểu đồ trong ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 400));

        // Cập nhật biểu đồ vào panel có sẵn
        piechart.removeAll(); // Xóa nội dung hiện tại của piechart
        piechart.setLayout(new BorderLayout()); // Đặt layout cho piechart
        piechart.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ vào piechart
        piechart.revalidate(); // Làm mới piechart
        piechart.repaint(); // Vẽ lại piechart
    }

}
