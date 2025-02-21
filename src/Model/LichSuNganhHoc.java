/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class LichSuNganhHoc {

    private String id;
    private String maSV;
    private String maNganhCu;
    private String maNganhMoi;
    private String ngaychuyen; // Hoặc dùng LocalDateTime nếu cần xử lý ngày giờ
    private String ghichu;

    public LichSuNganhHoc() {
    }

    public LichSuNganhHoc(String id, String maSV, String maNganhCu, String maNganhMoi, String ngaychuyen, String ghichu) {
        this.id = id;
        this.maSV = maSV;
        this.maNganhCu = maNganhCu;
        this.maNganhMoi = maNganhMoi;
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

    public String getMaNganhCu() {
        return maNganhCu;
    }

    public void setMaNganhCu(String maNganhCu) {
        this.maNganhCu = maNganhCu;
    }

    public String getMaNganhMoi() {
        return maNganhMoi;
    }

    public void setMaNganhMoi(String maNganhMoi) {
        this.maNganhMoi = maNganhMoi;
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
