package XuLyDuLieu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import BangDuLieu.DanhMuc;
import BangDuLieu.Nhom;
import BangDuLieu.ThuChiTungNhom;
import Database.DBHelper;

public class DAO_DanhMuc_Nhom {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public DAO_DanhMuc_Nhom(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> danhMucList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DANHMUC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                DanhMuc danhMuc = new DanhMuc(id, name);
                danhMucList.add(danhMuc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhMucList;
    }

    @SuppressLint("Range")
    public List<Nhom> getNhomListByDanhMucId(int danhMucId) {
        List<Nhom> nhomList = new ArrayList<>();

        String query = "SELECT * FROM NHOM WHERE ID_DANHMUC = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(danhMucId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                boolean isClass = cursor.getInt(cursor.getColumnIndex("CLASS")) == 1;
                String url = cursor.getString(cursor.getColumnIndex("ICON"));
                Nhom nhom = new Nhom(id, name, isClass, danhMucId, url);
                nhomList.add(nhom);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nhomList;
    }

    @SuppressLint("Range")
    public String getTenNhom(int ID_Nhom)
    {
        String query = "SELECT NAME FROM NHOM WHERE ID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID_Nhom)});
        String tenNhom = null;
        if (cursor.moveToFirst()) {
            tenNhom = cursor.getString(cursor.getColumnIndex("NAME"));
        }
        cursor.close();
        return tenNhom;
    }

    @SuppressLint("Range")
    public boolean getClassNhom(int ID_Nhom)
    {
        String query = "SELECT CLASS FROM NHOM WHERE ID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID_Nhom)});
        boolean classNhom = true;
        if (cursor.moveToFirst()) {
            int nhom_class = cursor.getInt(cursor.getColumnIndex("CLASS"));
            classNhom = nhom_class == 1;
        }
        cursor.close();
        return classNhom;
    }

    @SuppressLint("Range")
    public List<ThuChiTungNhom> getTop5NhomByTotalTien(int idUser, int loai) {
        List<ThuChiTungNhom> top5NhomList = new ArrayList<>();

        String query = "SELECT NHOM.NAME, SUM(GIAODICH.TIEN) AS TOTAL_TIEN, NHOM.ICON " +
                "FROM NHOM " +
                "LEFT JOIN GIAODICH ON NHOM.ID = GIAODICH.ID_NHOM " +
                "WHERE GIAODICH.ID_USER = ? AND CAST(NHOM.CLASS AS INTEGER) = ? AND GIAODICH.TRANGTHAI = 1 " +
                "GROUP BY NHOM.NAME " +
                "ORDER BY TOTAL_TIEN DESC " +
                "LIMIT 5";


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser), String.valueOf(loai)});

        if (cursor.moveToFirst()) {
            do {
                String nhomName = cursor.getString(cursor.getColumnIndex("NAME"));
                double totalTien = cursor.getDouble(cursor.getColumnIndex("TOTAL_TIEN"));
                String icon = cursor.getString(cursor.getColumnIndex("ICON"));
                ThuChiTungNhom a = new ThuChiTungNhom(nhomName, totalTien);
                a.setIcon(icon);
                top5NhomList.add(a);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return top5NhomList;
    }

}
