package BangDuLieu;

import java.io.Serializable;
import java.util.Date;

public class KhachHang implements Serializable {
    int ID;
    String Name;
    String LoginName;
    String Password;
    String Ngay;
    Boolean TrangThai;
    public KhachHang(){}
    public KhachHang(String name, String loginName, String password, String ngay, Boolean trangThai) {
        Name = name;
        LoginName = loginName;
        Password = password;
        Ngay = ngay;
        TrangThai = trangThai;
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

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        TrangThai = trangThai;
    }


    @Override
    public String toString() {
        return "KhachHang{" +
                ", Name='" + Name + '\'' +
                ", LoginName='" + LoginName + '\'' +
                ", Password='" + Password + '\'' +
                ", Ngay=" + Ngay +
                ", TrangThai=" + TrangThai +
                '}';
    }
}
