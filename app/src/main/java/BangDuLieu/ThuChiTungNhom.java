package BangDuLieu;

public class ThuChiTungNhom {

    String Name;
    Double TongTien;
    String icon;

    public ThuChiTungNhom(String name, Double tongTien) {
        Name = name;
        TongTien = tongTien;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public void setTongTien(Double tongTien) {
        TongTien = tongTien;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
