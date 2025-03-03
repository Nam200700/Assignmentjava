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

        jPanel1 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblback = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSendotp = new javax.swing.JButton();
        btnout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(132, 56, 220));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        lblback.setBackground(new java.awt.Color(132, 56, 220));
        lblback.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblback.setForeground(new java.awt.Color(255, 255, 255));
        lblback.setText("Back");
        lblback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblbackMouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Quên mật khẩu");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nhập email");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nhập tên đăng nhập");

        btnSendotp.setForeground(new java.awt.Color(132, 56, 220));
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

        btnout.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnout.setForeground(new java.awt.Color(255, 255, 255));
        btnout.setText("X");
        btnout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblback, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(btnout))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSendotp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                    .addComponent(txtEmail))))))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnout)))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(btnSendotp)
                .addGap(31, 31, 31)
                .addComponent(lblback)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            getcodeOtp code = new getcodeOtp();
            code.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email hoặc tên đăng nhập không hợp lệ!");
        }

    }//GEN-LAST:event_btnSendotpMouseClicked

    private void btnSendotpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendotpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSendotpActionPerformed

    private void lblbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblbackMouseClicked
        login lg = new login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblbackMouseClicked

    private void btnoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnoutMouseClicked
       System.exit(0);
    }//GEN-LAST:event_btnoutMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendotp;
    private javax.swing.JLabel btnout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblback;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
