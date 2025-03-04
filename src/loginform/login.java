/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginform;

import Model.User;
import Util.Auth;
import Util.jdbcHelper;
import assginmentjava3gd.view;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import raven.alerts.MessageAlerts;
import raven.popup.GlassPanePopup;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;

/**
 *
 * @author ME1
 */
public class login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public login() {
        initComponents();
        txtusername.setBackground(null);
        txtpassword.setBackground(null);
        jCheckBox1.setBackground(null);
        GlassPanePopup.install(this); // Dòng này cài đặt (install) GlassPanePopup vào JFrame hiện tại (this).
        FlatMacLightLaf.setup();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // nghĩa là chương trình sẽ thoát hoàn toàn khi đóng cửa sổ.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        disable = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        lblforget = new javax.swing.JLabel();
        btnlogin = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bg-register.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 450, 340));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, -20, 500, 440));

        jPanel2.setBackground(new java.awt.Color(132, 56, 220));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 40, 29));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Login");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 47, 420, 41));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hello! Let's get started");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 94, 420, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(221, 199, 255));
        jLabel5.setText("Username");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 341, -1));

        txtusername.setFont(txtusername.getFont().deriveFont(txtusername.getFont().getSize()+2f));
        txtusername.setForeground(new java.awt.Color(255, 255, 255));
        txtusername.setBorder(null);
        jPanel2.add(txtusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 140, 240, 30));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("_________________________________________");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 147, 290, 39));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_user_20px_1.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 40, 39));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(221, 199, 255));
        jLabel8.setText("Password");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 341, -1));

        txtpassword.setFont(txtpassword.getFont().deriveFont(txtpassword.getFont().getSize()+2f));
        txtpassword.setForeground(new java.awt.Color(255, 255, 255));
        txtpassword.setBorder(null);
        txtpassword.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 240, 30));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("_________________________________________");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 216, 290, 40));

        disable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_invisible_20px_1.png"))); // NOI18N
        disable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disableMouseClicked(evt);
            }
        });
        jPanel2.add(disable, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 40, 40));

        show.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_20px_1.png"))); // NOI18N
        show.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });
        jPanel2.add(show, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 40, 40));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(221, 199, 255));
        jCheckBox1.setText("Remember Password");
        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 261, -1, -1));

        lblforget.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblforget.setForeground(new java.awt.Color(221, 199, 255));
        lblforget.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblforget.setText("Forget Password?");
        lblforget.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblforget.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblforgetMouseClicked(evt);
            }
        });
        jPanel2.add(lblforget, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 261, 121, 27));

        btnlogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(132, 56, 220));
        btnlogin.setText("LOGIN");
        btnlogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });
        jPanel2.add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 306, 341, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(221, 199, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Don't have an account?");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 213, 30));

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Register");
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 80, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 420, 420));

        setSize(new java.awt.Dimension(861, 418));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        MessageAlerts.getInstance().showMessage("Bạn có muốn tắt hệ thống!!", "Descrition", MessageAlerts.MessageType.WARNING, MessageAlerts.YES_NO_OPTION, new PopupCallbackAction() {
            @Override
            public void action(PopupController pc, int i) {
                if (i == MessageAlerts.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }//GEN-LAST:event_jLabel2MouseClicked

    private void disableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disableMouseClicked
        txtpassword.setEchoChar((char) 0);
        disable.setVisible(false);
        disable.setEnabled(false);
        show.setEnabled(true);
        show.setEnabled(true);
    }//GEN-LAST:event_disableMouseClicked

    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
        txtpassword.setEchoChar((char) 8226);
        disable.setVisible(true);
        disable.setEnabled(true);
        show.setEnabled(false);
        show.setEnabled(false);
    }//GEN-LAST:event_showMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        for (double i = 0.0; i <= 1.0; i = i + 0.1) {
            String val = i + "";
            float f = Float.valueOf(val);
            this.setOpacity(f);
            try {
                Thread.sleep(50);
            } catch (Exception e) {

            }
        }
        //Đoạn mã bạn đưa ra là một hiệu ứng mờ dần (fade-in effect)
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        register r = new register();
        r.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        String username = txtusername.getText().trim();
        String password = new String(txtpassword.getPassword()).trim();

        if (username.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Thông báo", "Vui lòng nhập tên", MessageAlerts.MessageType.WARNING);
            return;
        }

        if (password.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Thông báo", "Vui lòng nhập mật khẩu", MessageAlerts.MessageType.WARNING);
            return;
        }

        // Mã hóa mật khẩu nếu cần (ví dụ với AES)
        String encryptedPassword = AES.encrypt(password);

        String sql = "SELECT id, email, id_role FROM users WHERE full_name = ? AND password = ?";

        try (ResultSet rs = jdbcHelper.executeQuery(sql, username, encryptedPassword)) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                int idrole = rs.getInt("id_role"); // Lấy role từ SQL

                // Gán thông tin vào Auth.user
                Auth.user = new User(id, username, email, idrole);

                // Phân biệt vai trò
                if (Auth.isAdmin()) {
                    JOptionPane.showMessageDialog(null, "Bạn là Admin!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else if (Auth.isTeacher()) {
                    JOptionPane.showMessageDialog(null, "Bạn là Giáo viên!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else if (Auth.isStudent()) {
                    JOptionPane.showMessageDialog(null, "Bạn là Sinh viên!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Vai trò không xác định!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Mở form chính
                view mainView = new view();
                mainView.setVisible(true);
                mainView.updateUserLabel(email);
                mainView.pack();
                mainView.setLocationRelativeTo(null);

                // Đóng form đăng nhập
                this.dispose();
            } else {
                MessageAlerts.getInstance().showMessage("Thông báo", "Username hoặc Password sai, vui lòng nhập lại!", MessageAlerts.MessageType.ERROR);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnloginActionPerformed

    private void lblforgetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblforgetMouseClicked
        forgot fg = new forgot();
        fg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblforgetMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JLabel disable;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblforget;
    private javax.swing.JLabel show;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
