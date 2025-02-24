/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Model.User;

public class Auth {

    public static User user = null;

    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     */
    public static void clear() {
        Auth.user = null;
    }

    /**
     * Kiểm tra xem đăng nhập hay chưa
     */
    public static boolean isLogin() {
        return Auth.user != null;
    }
     /**
     * Kiểm tra xem có phải là admin hay giáo viên và sinh viên 
     */
    public static boolean isAdmin() {
        return Auth.isLogin() && "admin".equalsIgnoreCase(user.getVaiTro());
    }

    public static boolean isTeacher() {
        return Auth.isLogin() && "giáo viên".equalsIgnoreCase(user.getVaiTro());
    }

    public static boolean isStudent() {
        return Auth.isLogin() && "sinh viên".equalsIgnoreCase(user.getVaiTro());
    }
}
