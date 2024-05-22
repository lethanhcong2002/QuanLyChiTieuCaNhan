package BangDuLieu;

import java.util.Date;

public class NganSach {
    int ID;
    String Name;
    int ID_Nhom;
    double Tien;
    String Ngay;
    int ID_User;

    public NganSach(){}

    public NganSach(int ID, String name, int ID_Nhom, double tien, String ngay, int ID_User) {
        this.ID = ID;
        Name = name;
        this.ID_Nhom = ID_Nhom;
        Tien = tien;
        Ngay = ngay;
        this.ID_User = ID_User;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID_Nhom() {
        return ID_Nhom;
    }

    public void setID_Nhom(int ID_Nhom) {
        this.ID_Nhom = ID_Nhom;
    }

    public double getTien() {
        return Tien;
    }

    public void setTien(double tien) {
        Tien = tien;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    @Override
    public String toString() {
        return "NganSach{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", ID_Nhom=" + ID_Nhom +
                ", Tien=" + Tien +
                ", Ngay=" + Ngay +
                ", ID_User=" + ID_User +
                '}';
    }
}
