package BangDuLieu;

public class Nhom {
    int ID;
    String Name;
    boolean Class;
    int ID_DanhMuc;
    String HinhAnh;
    double TonTien;
    public Nhom(){}

    public Nhom(int ID, String name, boolean aClass, int ID_DanhMuc, String hinhanh) {
        this.ID = ID;
        Name = name;
        Class = aClass;
        this.ID_DanhMuc = ID_DanhMuc;
        HinhAnh = hinhanh;
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


    public boolean isClass() {
        return Class;
    }

    public void setClass(boolean aClass) {
        Class = aClass;
    }

    public int getID_DanhMuc() {
        return ID_DanhMuc;
    }

    public void setID_DanhMuc(int ID_DanhMuc) {
        this.ID_DanhMuc = ID_DanhMuc;
    }
    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    @Override
    public String toString() {
        return "Nhom{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Class=" + Class +
                ", ID_DanhMuc=" + ID_DanhMuc +
                ", HinhAnh=" + HinhAnh +
                '}';
    }
}
