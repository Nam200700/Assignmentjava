/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class MajorandSubject {
     public String majorId;
    public String mamon;
    public int MaMonHocNganhHoc;
    public MajorandSubject() {
    }
    
    
    public MajorandSubject(String majorId, String mamon) {
        this.majorId = majorId;
        this.mamon = mamon;
    }

    public String getMajorId() {
        return majorId;
    }

    public String getMamon() {
        return mamon;
    }
}
