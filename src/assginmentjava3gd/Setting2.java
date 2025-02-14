/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.sql.*;
import java.util.Base64;
import java.security.Key;
import static javax.crypto.Cipher.SECRET_KEY;
import loginform.AES;
import static loginform.AES.encrypt;

/**
 *
 * @author ACER
 */
public class Setting2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form Setting2
     */
    public Setting2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

    }

    // Phương thức thay đổi mật khẩu
    private void changePassword() {
        // Lấy mật khẩu cũ, mật khẩu mới và xác nhận mật khẩu mới từ các trường nhập liệu
        char[] oldPassword = txtmkcu.getPassword();  // Mật khẩu cũ
        char[] newPassword = txtmkmoi.getPassword(); // Mật khẩu mới
        char[] confirmPassword = txtnhaplaimk.getPassword(); // Xác nhận mật khẩu mới

        // Chuyển mảng ký tự thành chuỗi
        String oldPasswordStr = new String(oldPassword);
        String newPasswordStr = new String(newPassword);
        String confirmPasswordStr = new String(confirmPassword);

        // Kiểm tra nếu mật khẩu mới và xác nhận mật khẩu mới không khớp
        if (!newPasswordStr.equals(confirmPasswordStr)) {
            // Hiển thị thông báo nếu mật khẩu không khớp
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp!");
            return;  // Dừng phương thức nếu mật khẩu không khớp
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assjava3", "root", "18102007")) {
            // Mã hóa mật khẩu cũ bằng AES
            String encryptedOldPassword = AES.encrypt(oldPasswordStr);

            // Kiểm tra mật khẩu cũ trong cơ sở dữ liệu
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE password = ?");
            checkStmt.setString(1, encryptedOldPassword);  // Truyền mật khẩu cũ đã mã hóa vào câu lệnh SQL
            ResultSet rs = checkStmt.executeQuery(); // Thực hiện truy vấn

            // Nếu mật khẩu cũ đúng
            if (rs.next()) {
                // Mã hóa mật khẩu mới bằng AES
                String encryptedNewPassword = AES.encrypt(newPasswordStr);

                // Cập nhật mật khẩu mới vào cơ sở dữ liệu
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
                updateStmt.setString(1, encryptedNewPassword);  // Truyền mật khẩu mới đã mã hóa vào câu lệnh SQL
                updateStmt.setInt(2, rs.getInt("id"));  // Cập nhật cho người dùng có id tương ứng
                updateStmt.executeUpdate();  // Thực hiện cập nhật

                // Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            } else {
                // Nếu mật khẩu cũ không đúng
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
            }
        } catch (Exception ex) {
            // In ra lỗi và hiển thị thông báo lỗi nếu có bất kỳ sự cố nào
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnthaydoi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtmkmoi = new javax.swing.JPasswordField();
        txtmkcu = new javax.swing.JPasswordField();
        txtnhaplaimk = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Mật khẩu cũ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Mật khẩu mới");

        btnthaydoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthaydoi.setText("Thay đổi");
        btnthaydoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthaydoiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("confirmpassword");

        txtmkmoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtmkcu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtnhaplaimk.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmkcu)
                            .addComponent(txtmkmoi)
                            .addComponent(txtnhaplaimk, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnthaydoi)))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtmkcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtmkmoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnhaplaimk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(btnthaydoi)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthaydoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthaydoiActionPerformed
        changePassword();
    }//GEN-LAST:event_btnthaydoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnthaydoi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtmkcu;
    private javax.swing.JPasswordField txtmkmoi;
    private javax.swing.JPasswordField txtnhaplaimk;
    // End of variables declaration//GEN-END:variables
}
