package XuLyDuLieu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import BangDuLieu.NganSach;
import Database.DBHelper;

public class DAO_NganSach {

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public DAO_NganSach(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<NganSach> LayNganSachConHieuLuc(int ID_User)
    {
        List<NganSach> lst = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        String currentMonthString = String.format("%02d", currentMonth);
        String currentYearString = String.valueOf(currentYear);

        String query = "SELECT * FROM NGANSACH WHERE ID_USER = ? AND (substr(NGAY, 4, 4) > ? OR (substr(NGAY, 1, 2) >= ? AND substr(NGAY, 4, 4) = ?))";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID_User), currentYearString, currentMonthString, currentYearString});

        while (cursor.moveToNext())
        {
            NganSach ns = new NganSach();
            ns.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            ns.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            ns.setID_Nhom(cursor.getInt(cursor.getColumnIndex("ID_NHOM")));
            ns.setTien(cursor.getDouble(cursor.getColumnIndex("TIEN")));
            ns.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            ns.setID_User(cursor.getInt(cursor.getColumnIndex("ID_USER")));

            lst.add(ns);
        }
        cursor.close();

        return lst;
    }

    @SuppressLint("Range")
    public List<NganSach> LayNganSachHetHieuLuc(int ID_User)
    {
        List<NganSach> lst = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        String currentMonthString = String.format("%02d", currentMonth);
        String currentYearString = String.valueOf(currentYear);

        String query = "SELECT * FROM NGANSACH WHERE ID_USER = ? AND (substr(NGAY, 4, 4) < ? OR (substr(NGAY, 1, 2) < ? AND substr(NGAY, 4, 4) = ?))";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID_User), currentYearString, currentMonthString, currentYearString});

        while (cursor.moveToNext())
        {
            NganSach ns = new NganSach();
            ns.setID(cursor.getInt(cursor.getColumnIndex("ID")));
            ns.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            ns.setID_Nhom(cursor.getInt(cursor.getColumnIndex("ID_NHOM")));
            ns.setTien(cursor.getDouble(cursor.getColumnIndex("TIEN")));
            ns.setNgay(cursor.getString(cursor.getColumnIndex("NGAY")));
            ns.setID_User(cursor.getInt(cursor.getColumnIndex("ID_USER")));

            lst.add(ns);
        }
        cursor.close();

        return lst;
    }

    public boolean ThemNganSach(NganSach ns)
    {
        ContentValues values = new ContentValues();
        values.put("NAME", ns.getName());
        values.put("ID_NHOM", ns.getID_Nhom());
        values.put("TIEN", ns.getTien());
        values.put("NGAY", ns.getNgay());
        values.put("ID_USER", ns.getID_User());

        long check = db.insert("NGANSACH", null, values);
        if(check == 0)
        {
            return false;
        }
        return true;
    }

}
