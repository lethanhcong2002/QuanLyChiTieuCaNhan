package XuLyDuLieu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import BangDuLieu.ThuChiTrongThang;
import BangDuLieu.GiaoDich;
import BangDuLieu.ThuChiTungNhom;
import Database.DBHelper;

public class DAO_GiaoDich {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public DAO_GiaoDich(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<GiaoDich> getAllGiaoDich(int id) {
        List<GiaoDich> lst = new ArrayList<>();
        String query = "SELECT * FROM GIAODICH WHERE ID_USER = ? AND TRANGTHAI = 1 ORDER BY ID DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        while (cursor.moveToNext()) {
            GiaoDich gd = new GiaoDich();
            gd.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            gd.setID_User(id);
            gd.setID_Nhom(cursor.getInt(cursor.getColumnIndex("ID_NHOM")));
            gd.setGhiChu(cursor.getString(cursor.getColumnIndex("GHICHU")));

            int trangThai = cursor.getInt(cursor.getColumnIndex("TRANGTHAI"));
            gd.setTrangThai(trangThai == 1);

            gd.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            gd.setTien(cursor.getDouble(cursor.getColumnIndex("TIEN")));
            gd.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("HINHANH")));

            lst.add(gd);
        }

        cursor.close();
        return lst;
    }

    public List<Double> getTongTienNgay(int id, String ngay)
    {
        List<Double> lst = new ArrayList<>();

        String sqlQuery = "SELECT b.CLASS, SUM(COALESCE(a.TIEN, 0)) AS TongTien " +
                "FROM NHOM b " +
                "LEFT JOIN (SELECT * FROM GIAODICH WHERE ID_USER = ? AND TRANGTHAI = 1 AND NGAY = ?) a " +
                "ON a.ID_NHOM = b.ID " +
                "GROUP BY b.CLASS " +
                "ORDER BY b.CLASS";

        Cursor cursor = db.rawQuery(sqlQuery, new String[] { String.valueOf(id), ngay });

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") double tongTien = cursor.getDouble(cursor.getColumnIndex("TongTien"));
                    lst.add(tongTien);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lst;
    }
    public boolean ThemGiaoDich(GiaoDich gd)
    {
        ContentValues values = new ContentValues();
        values.put("ID_USER", gd.getID_User());
        values.put("ID_NHOM", gd.getID_Nhom());
        values.put("GHICHU", gd.getGhiChu());
        values.put("TRANGTHAI", gd.getTrangThai());
        values.put("NGAY", gd.getNgay());
        values.put("TIEN", gd.getTien());
        values.put("HINHANH", gd.getHinhAnh());

        long check = db.insert("GIAODICH", null, values);
        if(check == 0)
        {
            return false;
        }
        return true;
    }

    @SuppressLint("Range")
    public List<GiaoDich> getGiaoDichByTrangThai(int ID_User, int classValue) {
        List<GiaoDich> lst = new ArrayList<>();

        String sqlQuery = "SELECT a.* FROM GIAODICH a JOIN NHOM b ON a.ID_NHOM = b.ID WHERE a.ID_USER = ? AND a.TRANGTHAI = ? AND b.CLASS = ?";
        String[] selectionArgs = {String.valueOf(ID_User), "1", String.valueOf(classValue) };
        Cursor cursor = db.rawQuery(sqlQuery, selectionArgs);
        while (cursor.moveToNext()) {
            GiaoDich gd = new GiaoDich();
            gd.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            gd.setID_User(ID_User);
            gd.setID_Nhom(cursor.getInt(cursor.getColumnIndex("ID_NHOM")));
            gd.setGhiChu(cursor.getString(cursor.getColumnIndex("GHICHU")));
            int trangThai = cursor.getInt(cursor.getColumnIndex("TRANGTHAI"));
            gd.setTrangThai(trangThai == 1);
            gd.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            gd.setTien(cursor.getDouble(cursor.getColumnIndex("TIEN")));
            gd.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("HINHANH")));
            lst.add(gd);
        }

        cursor.close();
        return lst;
    }

    @SuppressLint("Range")
    public List<GiaoDich> getGiaoDichDaXoa(int ID_User){
        List<GiaoDich> lst = new ArrayList<>();

        String query = "SELECT * FROM GIAODICH WHERE ID_USER = ? AND TRANGTHAI = 0";
        String[] selectionArgs = {String.valueOf(ID_User) };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()){
            GiaoDich gd = new GiaoDich();
            gd.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            gd.setID_User(ID_User);
            gd.setID_Nhom(cursor.getInt(cursor.getColumnIndex("ID_NHOM")));
            gd.setGhiChu(cursor.getString(cursor.getColumnIndex("GHICHU")));
            int trangThai = cursor.getInt(cursor.getColumnIndex("TRANGTHAI"));
            gd.setTrangThai(trangThai == 0);
            gd.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            gd.setTien(cursor.getDouble(cursor.getColumnIndex("TIEN")));
            gd.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("HINHANH")));

            lst.add(gd);
        }
        cursor.close();
        return lst;
    }

    public boolean VoHieuGiaoDich(int giaoDichID) {
        ContentValues values = new ContentValues();
        values.put("TrangThai", 0);
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(giaoDichID)};
        int result = db.update("GIAODICH", values, whereClause, whereArgs);
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean capNhatGiaoDich(GiaoDich gd) {
        ContentValues values = new ContentValues();

        values.put("ID_NHOM", gd.getID_Nhom());
        values.put("ID_USER", gd.getID_User());
        values.put("GHICHU", gd.getGhiChu());
        values.put("TRANGTHAI", gd.getTrangThai() ? 1 : 0);
        values.put("NGAY", gd.getNgay());
        values.put("TIEN", gd.getTien());
        values.put("HINHANH", gd.getHinhAnh());

        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(gd.getID())};

        int result = db.update("GIAODICH", values, whereClause, whereArgs);

        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Double> getTongThuChi(int ID_USER) {
        List<Double> lst = new ArrayList<>();

        String sqlQuery = "SELECT b.CLASS, SUM(COALESCE(a.TIEN, 0)) AS TongTien " +
                "FROM NHOM b " +
                "LEFT JOIN (SELECT * FROM GIAODICH WHERE ID_USER = ? AND TRANGTHAI = 1) a " +
                "ON a.ID_NHOM = b.ID " +
                "GROUP BY b.CLASS " +
                "ORDER BY b.CLASS";

        Cursor cursor = db.rawQuery(sqlQuery, new String[] { String.valueOf(ID_USER) });

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") double tongTien = cursor.getDouble(cursor.getColumnIndex("TongTien"));
                    lst.add(tongTien);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lst;
    }



    @SuppressLint("Range")
    public List<ThuChiTrongThang> getMonthData(int userID, int year) {
        List<ThuChiTrongThang> monthDataList = new ArrayList<>();

        String sqlQuery = "SELECT substr(NGAY, 4, 2) AS Thang, " +
                "SUM(CASE WHEN b.CLASS = 1 THEN a.TIEN ELSE 0 END) AS TongThuNhap, " +
                "SUM(CASE WHEN b.CLASS = 0 THEN a.TIEN ELSE 0 END) AS TongChiTieu " +
                "FROM GIAODICH a " +
                "JOIN NHOM b ON a.ID_NHOM = b.ID " +
                "WHERE a.ID_USER = ? AND a.TRANGTHAI = 1 " +
                "AND substr(NGAY, 7, 4) = ? " +
                "GROUP BY Thang " +
                "ORDER BY Thang";

        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(userID), String.valueOf(year)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String month = cursor.getString(cursor.getColumnIndex("Thang"));
                    double tongThuNhap = cursor.getDouble(cursor.getColumnIndex("TongThuNhap"));
                    double tongChiTieu = cursor.getDouble(cursor.getColumnIndex("TongChiTieu"));
                    ThuChiTrongThang monthData = new ThuChiTrongThang(month, tongThuNhap, tongChiTieu);
                    monthDataList.add(monthData);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return monthDataList;
    }

    @SuppressLint("Range")
    public List<ThuChiTungNhom> getNhomData(int userID, String MonthOfYear)
    {
        List<ThuChiTungNhom> lst = new ArrayList<>();

        String sqlQuery = "SELECT b.NAME AS TenNhom, " +
                "SUM(a.TIEN) AS TongTien " +
                "FROM GIAODICH a " +
                "JOIN NHOM b ON a.ID_NHOM = b.ID " +
                "WHERE a.ID_USER = ? AND a.TRANGTHAI = 1 " +
                "AND substr(NGAY, 4, 7) = ? " +
                "GROUP BY b.NAME ";

        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(userID), MonthOfYear});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("TenNhom"));
                    double tongtien = cursor.getDouble(cursor.getColumnIndex("TongTien"));
                    ThuChiTungNhom Data = new ThuChiTungNhom(name, tongtien);
                    lst.add(Data);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lst;
    }

    @SuppressLint("Range")
    public Double TienTrongThang(int ID_USER, int ID_NHOM, String Thang)
    {
        Double tien = Double.valueOf(0);
        String sql = "SELECT SUM(TIEN) AS TONGTIEN FROM GIAODICH WHERE ID_USER = ? AND substr(NGAY, 4, 7) = ? AND ID_NHOM = ? AND TRANGTHAI = 1";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(ID_USER), Thang, String.valueOf(ID_NHOM)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    tien = cursor.getDouble(cursor.getColumnIndex("TONGTIEN"));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return tien;
    }
    @SuppressLint("Range")
    public boolean ReloadGiaoDich(GiaoDich gd){
        ContentValues values = new ContentValues();
        values.put("TRANGTHAI", gd.getTrangThai());

        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(gd.getID())};

        int result = db.update("GIAODICH",values,whereClause,whereArgs);

        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteGiaoDich(GiaoDich gd)
    {
        ContentValues values = new ContentValues();
        String whereClause = "ID=?";
        String[] whereArgs = {String.valueOf(gd.getID())};

        int result = db.delete("GIAODICH", whereClause, whereArgs);

        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Double> getGiaoDichByDateRange(int idUser, String ngayBD, String ngayKT)
    {
        List<Double> lst = new ArrayList<>();

        String sqlQuery = "SELECT b.CLASS, SUM(COALESCE(a.TIEN, 0)) AS TongTien " +
                "FROM NHOM b " +
                "LEFT JOIN (SELECT * FROM GIAODICH WHERE ID_USER = ? AND TRANGTHAI = 1 AND NGAY BETWEEN ? AND ?) a " +
                "ON a.ID_NHOM = b.ID " +
                "GROUP BY b.CLASS " +
                "ORDER BY b.CLASS";

        Cursor cursor = db.rawQuery(sqlQuery, new String[] { String.valueOf(idUser), ngayBD, ngayKT });

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") double tongTien = cursor.getDouble(cursor.getColumnIndex("TongTien"));
                    lst.add(tongTien);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lst;
    }

    @SuppressLint("Range")
    public List<ThuChiTungNhom> getNhomDataDateRange(int userID, String ngayBD, String ngayKT)
    {
        List<ThuChiTungNhom> lst = new ArrayList<>();

        String sqlQuery = "SELECT b.NAME AS TenNhom, " +
                "SUM(a.TIEN) AS TongTien " +
                "FROM GIAODICH a " +
                "JOIN NHOM b ON a.ID_NHOM = b.ID " +
                "WHERE a.ID_USER = ? AND a.TRANGTHAI = 1 " +
                "AND NGAY BETWEEN ? AND ? " +
                "GROUP BY b.NAME ";

        Cursor cursor = db.rawQuery(sqlQuery, new String[]{String.valueOf(userID), ngayBD, ngayKT});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("TenNhom"));
                    double tongtien = cursor.getDouble(cursor.getColumnIndex("TongTien"));
                    ThuChiTungNhom Data = new ThuChiTungNhom(name, tongtien);
                    lst.add(Data);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lst;
    }
}
