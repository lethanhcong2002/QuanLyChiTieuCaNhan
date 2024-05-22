package BangDuLieu;

public class ThuChiTrongThang {

    private String month;
    private double tongThuNhap;
    private double tongChiTieu;

    public ThuChiTrongThang(String month, double tongThuNhap, double tongChiTieu) {
        this.month = month;
        this.tongThuNhap = tongThuNhap;
        this.tongChiTieu = tongChiTieu;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTongThuNhap() {
        return tongThuNhap;
    }

    public void setTongThuNhap(double tongThuNhap) {
        this.tongThuNhap = tongThuNhap;
    }

    public double getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(double tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }
}
