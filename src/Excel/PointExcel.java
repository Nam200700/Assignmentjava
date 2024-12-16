/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class PointExcel {

    public static void exportToExcel(JTable table) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            File file = new File(filePath);

            if (file.exists()) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "File đã tồn tại. Bạn có muốn ghi đè không?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất.");
                return;
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Danh sách");

                // Tạo tiêu đề báo cáo
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Báo Cáo Điểm Môn Học Sinh Viên");

                CellStyle titleStyle = workbook.createCellStyle();
                XSSFFont titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleCell.setCellStyle(titleStyle);

                // Gộp các ô cho tiêu đề
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, table.getColumnCount() - 1));

                // Tạo dòng tiêu đề cột
                Row headerRow = sheet.createRow(1);
                CellStyle headerStyle = workbook.createCellStyle();
                XSSFFont headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setAlignment(HorizontalAlignment.CENTER);

                for (int col = 0; col < table.getColumnCount(); col++) {
                    Cell headerCell = headerRow.createCell(col);
                    headerCell.setCellValue(table.getColumnName(col));
                    headerCell.setCellStyle(headerStyle);
                }

                // Ghi dữ liệu từ JTable vào các ô
                for (int row = 0; row < table.getRowCount(); row++) {
                    Row dataRow = sheet.createRow(row + 2);
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        Cell cell = dataRow.createCell(col);
                        Object value = table.getValueAt(row, col);

                        // Xử lý kiểu dữ liệu cho từng ô
                        if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                        } else {
                            cell.setCellValue(value != null ? value.toString() : "");
                        }
                    }
                }

                // Định dạng cho các cột dữ liệu số (cột điểm chẳng hạn)
                for (int col = 3; col < table.getColumnCount(); col++) {
                    if (table.getColumnClass(col) == Double.class || table.getColumnClass(col) == Float.class) {
                        CellStyle numberStyle = workbook.createCellStyle();
                        XSSFDataFormat format = workbook.createDataFormat();
                        numberStyle.setDataFormat(format.getFormat("0.00")); // Định dạng số với 2 chữ số sau dấu thập phân
                        for (int row = 2; row < table.getRowCount() + 2; row++) {
                            Row dataRow = sheet.getRow(row);
                            Cell cell = dataRow.getCell(col);
                            if (cell != null) {
                                cell.setCellStyle(numberStyle);
                            }
                        }
                    }
                }

                // Tự động điều chỉnh chiều rộng cột
                for (int col = 0; col < table.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                // Lưu file Excel
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất ra file Excel thành công!");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xuất file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
