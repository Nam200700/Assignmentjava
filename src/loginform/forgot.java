/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginform;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class forgot extends javax.swing.JFrame {

    /**
     * Creates new form forgot
     */
    public forgot() {
        initComponents();
        this.setLocationRelativeTo(this);
    }
    private static final String url = "jdbc:mysql://localhost:3306/assjava3";
    private static final String user = "root";
    private static final String password = "18102007";

    // Đăng ký driver MySQL khi class được nạp
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static boolean verifyUser(String email, String username) {
//        String url = "jdbc:mysql://localhost:3306/assjava3";
//        String user = "root";
//        String password = "18102007";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ? AND full_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, username);

            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private static void sendEmail(String recipient, String otp) {
        final String senderEmail = "nghiatttv00104@fpt.edu.vn";
        final String senderPassword = "qrzd hmib axkr ydlw"; // app password của mail bạn

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Hệ Thống OTP"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Mã OTP của bạn");
            message.setText("Mã OTP của bạn là: " + otp);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean updatePasswordInDB(String email, String newPassword) {
//        String url = "jdbc:mysql://localhost:3306/assjava3";
//        String user = "root";
//        String password = "18102007";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String hashedPassword = encrypt(newPassword);
            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, email);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static final String SECRET_KEY = "MySecretKey12345";  // Khóa bí mật cho AES phải có độ dài 16 ký tự

// Hàm mã hóa AES
    public static String encrypt(String input) {
        try {
            // Tạo đối tượng khóa AES từ chuỗi khóa bí mật
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            // Khởi tạo đối tượng Cipher với thuật toán AES
            Cipher cipher = Cipher.getInstance("AES");

            // Thiết lập Cipher để mã hóa (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Mã hóa dữ liệu đầu vào
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            // Trả về dữ liệu đã mã hóa dưới dạng chuỗi Base64
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Nếu có lỗi trong quá trình mã hóa, ném ra một ngoại lệ với thông báo lỗi
            throw new RuntimeException("Error during AES encryption", e);
        }
    }
    public static String OTP;
    public static String userEmail;
    public static String userUsername;

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
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtnhapmaotp = new javax.swing.JTextField();
        btnSendotp = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnxacnhanmaotp = new javax.swing.JButton();
        btnpassword = new javax.swing.JButton();
        txtnewpassword = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nhập email");

        jLabel2.setText("Nhập tên đăng nhập");

        jLabel3.setText("Nhập mật khẩu mới");

        btnSendotp.setText("Gửi mã otp");
        btnSendotp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendotpMouseClicked(evt);
            }
        });
        btnSendotp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendotpActionPerformed(evt);
            }
        });

        jLabel4.setText("Nhập mã otp");

        btnxacnhanmaotp.setText("Xác nhận mã otp");
        btnxacnhanmaotp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnxacnhanmaotpMouseClicked(evt);
            }
        });

        btnpassword.setText("Đổi mật khẩu");
        btnpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpasswordMouseClicked(evt);
            }
        });
        btnpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpasswordActionPerformed(evt);
            }
        });

        txtnewpassword.setText("jPasswordField1");

        jLabel5.setText("Quên mật khẩu");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Back");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnpassword)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(btnxacnhanmaotp)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(btnSendotp)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnhapmaotp)
                                    .addComponent(txtnewpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(btnSendotp)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(btnxacnhanmaotp)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtnewpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(txtnhapmaotp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(btnpassword)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendotpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendotpMouseClicked
        userEmail = txtEmail.getText().trim();
        userUsername = txtName.getText().trim();

        // Kiểm tra nếu email hoặc tên đăng nhập bị trống
        if (userEmail.isEmpty() || userUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Email và Tên đăng nhập!");
            return;
        } else if (verifyUser(userEmail, userUsername)) {
            OTP = generateOTP();
            sendEmail(userEmail, OTP);
            JOptionPane.showMessageDialog(this, "Mã OTP đã được gửi tới " + userEmail);
        } else {
            JOptionPane.showMessageDialog(this, "Email hoặc tên đăng nhập không hợp lệ!");
        }

    }//GEN-LAST:event_btnSendotpMouseClicked

    private void btnSendotpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendotpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSendotpActionPerformed

    private void btnxacnhanmaotpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxacnhanmaotpMouseClicked
        if(txtnhapmaotp.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã OTP!!.");
        }
        else if (txtnhapmaotp.getText().equals(OTP)) {
            JOptionPane.showMessageDialog(this, "OTP hợp lệ! Bạn có thể đổi mật khẩu.");
        } else {
            JOptionPane.showMessageDialog(this, "Mã OTP không đúng!");
        }
    }//GEN-LAST:event_btnxacnhanmaotpMouseClicked

    private void btnpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpasswordMouseClicked
        if (txtnhapmaotp.getText().equals(OTP)) {
            String newPassword = new String(txtnewpassword.getPassword());
            if (updatePasswordInDB(userEmail, newPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu đã được thay đổi thành công!");
                login lg = new login();
                lg.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật mật khẩu.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mã OTP không đúng!");
        }
    }//GEN-LAST:event_btnpasswordMouseClicked

    private void btnpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnpasswordActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        login lg = new login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendotp;
    private javax.swing.JButton btnpassword;
    private javax.swing.JButton btnxacnhanmaotp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtnewpassword;
    private javax.swing.JTextField txtnhapmaotp;
    // End of variables declaration//GEN-END:variables
}
