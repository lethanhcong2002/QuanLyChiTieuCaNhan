package BangDuLieu;

public class GiaoDich {
    int ID;
    int ID_User;
    int ID_Nhom;
    String GhiChu;
    Boolean TrangThai;
    String Ngay;
    double Tien;

    double TongTien;
    byte[] HinhAnh;

    public GiaoDich(){}

    public GiaoDich(int ID, int ID_User, int ID_Nhom, String ghiChu, Boolean trangThai, String ngay, double tien) {
        this.ID = ID;
        this.ID_User = ID_User;
        this.ID_Nhom = ID_Nhom;
        GhiChu = ghiChu;
        TrangThai = trangThai;
        Ngay = ngay;
        Tien = tien;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public int getID_Nhom() {
        return ID_Nhom;
    }

    public void setID_Nhom(int ID_Nhom) {
        this.ID_Nhom = ID_Nhom;
    }


    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        TrangThai = trangThai;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public double getTien() {
        return Tien;
    }

    public void setTien(double tien) {
        Tien = tien;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }


    @Override
    public String toString() {
        return "GiaoDich{" +
                "ID=" + ID +
                ", ID_User=" + ID_User +
                ", ID_Nhom=" + ID_Nhom +
                ", GhiChu='" + GhiChu + '\'' +
                ", TrangThai=" + TrangThai +
                ", Ngay=" + Ngay +
                ", Tien=" + Tien +
                ", HinhAnh=" + HinhAnh +
                '}';
    }

    public void TongTien(double tien)
    {
        TongTien = tien;
    }
}
