package Support;

import java.text.NumberFormat;
import java.util.Locale;

import BangDuLieu.GiaoDich;
import BangDuLieu.KhachHang;
import BangDuLieu.NganSach;

public class AppData {
    private static AppData instance;
    private KhachHang kh;
    private GiaoDich gd;
    private AppData() {}

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public KhachHang getKhachHang() {
        return kh;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.kh = khachHang;
    }

    public GiaoDich getGiaoDich() {
        return gd;
    }

    public void setGiaoDich(GiaoDich gd) {
        this.gd = gd;
    }

    public String formatLargeNumber(double number) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedNumber = formatter.format(number);

        return formattedNumber;
    }

    public void ClearALl()
    {
        kh = null;
        gd = null;
    }
}

