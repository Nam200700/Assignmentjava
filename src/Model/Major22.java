/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class Major22 {
    public String MajorID;
    public String MajorName;
    public String Note;

    public Major22() {
    }

    public String getMajorID() {
        return MajorID;
    }

    public void setMajorID(String MajorID) {
        this.MajorID = MajorID;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String MajorName) {
        this.MajorName = MajorName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public Major22(String MajorID, String MajorName, String Note) {
        this.MajorID = MajorID;
        this.MajorName = MajorName;
        this.Note = Note;
    }
}
