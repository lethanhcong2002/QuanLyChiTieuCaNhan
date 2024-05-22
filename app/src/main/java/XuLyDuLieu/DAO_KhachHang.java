package XuLyDuLieu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import BangDuLieu.KhachHang;
import Database.DBHelper;

public class DAO_KhachHang {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public DAO_KhachHang(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean ThemKhachHang(KhachHang user) {
        ContentValues values = new ContentValues();
        values.put("NAME", user.getName());
        values.put("LOGINNAME", user.getLoginName());
        values.put("PASSWORD", user.getPassword());
        values.put("NGAY", user.getNgay());
        values.put("TRANGTHAI", user.getTrangThai());

        long check = db.insert("KHACHHANG", null, values);
        if (check <= 0) {
            return false;
        }
        return true;
    }

    @SuppressLint("Range")
    public KhachHang getKhachHang(String loginName, String password) {
        String query = "SELECT * FROM KHACHHANG WHERE LOGINNAME = ? AND PASSWORD = ?";
        Cursor cursor = db.rawQuery(query, new String[]{loginName, password});

        if (cursor.moveToFirst()) {
            KhachHang khachHang = new KhachHang();
            khachHang.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            khachHang.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            khachHang.setLoginName(cursor.getString(cursor.getColumnIndex("LOGINNAME")));
            khachHang.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
            khachHang.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            khachHang.setTrangThai(cursor.getInt(cursor.getColumnIndex("TRANGTHAI")) == 1);
            cursor.close();
            return khachHang;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean kiemtraKH(String loginame) {
        String query = "SELECT * FROM KHACHHANG WHERE LOGINNAME = ?";
        Cursor cursor = db.rawQuery(query, new String[]{loginame});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }
    public boolean DoiMatKhau(KhachHang user){
        ContentValues cv = new ContentValues();
        cv.put("PASSWORD", user.getPassword());

        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(user.getID())};

        int result = db.update("KHACHHANG", cv, whereClause, whereArgs);

        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }
}
