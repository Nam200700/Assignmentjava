/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import Util.jdbcHelper;
import javax.swing.JOptionPane;
import org.jfree.chart.plot.PiePlot;

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
        String query = """
            SELECT n.tenNganh, COUNT(sv.maSV) AS soLuongSinhVien
            FROM SinhVien sv
            JOIN NganhHoc n ON sv.maNganh = n.maNganh
            GROUP BY n.tenNganh
        """;

        try (ResultSet rs = jdbcHelper.executeQuery(query)) {
            while (rs.next()) {
                String tenNganh = rs.getString("tenNganh");
                int soLuong = rs.getInt("soLuongSinhVien");
                pieDataset.setValue(tenNganh, soLuong);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu biểu đồ!");
            return;
        }

        // Tạo biểu đồ hình tròn
        JFreeChart chart = ChartFactory.createPieChart(
                "Biểu đồ số lượng sinh viên theo ngành",
                pieDataset,
                true,
                true,
                false
        );

        // Tùy chỉnh nhãn hiển thị
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} sinh viên",
                new DecimalFormat("0"),
                new DecimalFormat("0%")
        ));

        // Hiển thị biểu đồ trên JPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(380, 400));

        piechart.removeAll();
        piechart.setLayout(new BorderLayout());
        piechart.add(chartPanel, BorderLayout.CENTER);
        piechart.revalidate();
        piechart.repaint();
    }

}
