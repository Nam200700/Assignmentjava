/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class LichSuLopHoc {
    private String id;
    private String maSV;
    private String maLopCu;
    private String maLopMoi;
    private String ngaychuyen; // Hoặc dùng LocalDateTime nếu cần xử lý ngày giờ
    private String ghichu;

    public LichSuLopHoc() {
    }

    public LichSuLopHoc(String id, String maSV, String maLopCu, String maLopMoi, String ngaychuyen, String ghichu) {
        this.id = id;
        this.maSV = maSV;
        this.maLopCu = maLopCu;
        this.maLopMoi = maLopMoi;
        this.ngaychuyen = ngaychuyen;
        this.ghichu = ghichu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaLopCu() {
        return maLopCu;
    }

    public void setMaLopCu(String maLopCu) {
        this.maLopCu = maLopCu;
    }

    public String getMaLopMoi() {
        return maLopMoi;
    }

    public void setMaLopMoi(String maLopMoi) {
        this.maLopMoi = maLopMoi;
    }

    public String getNgaychuyen() {
        return ngaychuyen;
    }

    public void setNgaychuyen(String ngaychuyen) {
        this.ngaychuyen = ngaychuyen;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

   
}
