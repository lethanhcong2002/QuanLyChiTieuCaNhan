package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "QuanLyTaiChinh", null, 1);
    }
    public static final String CREATE_TB_DANHMUC = "CREATE TABLE DANHMUC (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME NVARCHAR(50) UNIQUE)";
    public static final String CREATE_TB_NHOM = "CREATE TABLE NHOM (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME NVARCHAR(50) UNIQUE, CLASS BIT, ID_DANHMUC INTERGER, ICON TEXT, FOREIGN KEY(ID_DANHMUC) REFERENCES DANHMUC(ID))";
    public static final String CREATE_TB_KHACHHANG = "CREATE TABLE KHACHHANG (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME NVARCHAR(50), LOGINNAME VARCHAR(50), PASSWORD VARCHAR(50), NGAY TEXT, TRANGTHAI BIT)";
    public static final String CREATE_TB_GIAODICH = "CREATE TABLE GIAODICH (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_USER INTERGER, ID_NHOM INTERGER, GHICHU TEXT, TRANGTHAI BIT, NGAY TEXT, TIEN DOUBLE, HINHANH BLOB, FOREIGN KEY(ID_USER) REFERENCES KHACHHANG(ID), FOREIGN KEY(ID_NHOM) REFERENCES NHOM(ID))";
    public static final String CREATE_TB_NGANSACH = "CREATE TABLE NGANSACH (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME NVARCHAR(50), ID_NHOM INTEGER, TIEN DOUBLE, NGAY TEXT, ID_USER INTEGER, FOREIGN KEY(ID_NHOM) REFERENCES NHOM(ID), FOREIGN KEY(ID_USER) REFERENCES KHACHHANG(ID))";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TB_DANHMUC);
        sqLiteDatabase.execSQL(CREATE_TB_NHOM);
        sqLiteDatabase.execSQL(CREATE_TB_KHACHHANG);
        sqLiteDatabase.execSQL(CREATE_TB_GIAODICH);
        sqLiteDatabase.execSQL(CREATE_TB_NGANSACH);
        InsertKhachHang(sqLiteDatabase);
        InsertDanhMuc(sqLiteDatabase);
        InsertNhom(sqLiteDatabase);
        InsertLapKH(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANHMUC");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DSHINHANH");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NHOM");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS KHACHHANG");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GIAODICH");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGANSACH");

        onCreate(sqLiteDatabase);
    }

    public void InsertKhachHang(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("INSERT INTO KHACHHANG (NAME, LOGINNAME, PASSWORD, NGAY, TRANGTHAI) VALUES ('Lê Thành Công', 'lecong2002', '123', '28/09/2023', 1)");
    }

    public void InsertDanhMuc(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("INSERT INTO DANHMUC VALUES (null, 'Chi tiêu hàng tháng')");
        sqLiteDatabase.execSQL("INSERT INTO DANHMUC VALUES (null, 'Chi tiêu cần thiết')");
        sqLiteDatabase.execSQL("INSERT INTO DANHMUC VALUES (null, 'Vui - chơi')");
        sqLiteDatabase.execSQL("INSERT INTO DANHMUC VALUES (null, 'Đầu tư, Cho vay')");
        sqLiteDatabase.execSQL("INSERT INTO DANHMUC VALUES (null, 'Khoản thu')");
    }

    public void InsertNhom(SQLiteDatabase sqLiteDatabase)
    {
        //class: 0 là chi, 1 là thu

        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Ăn uống', 0, 1, 'food')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Di chuyển', 0, 1, 'moving')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Thuê nhà', 0, 1, 'rent_home')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn nước', 0, 1, 'water_bill')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn điện thoại', 0, 1, 'phone_bill')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn điện nước', 0, 1, 'we_bill')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn gas', 0, 1, 'gas')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn TV', 0, 1, 'tvs')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn internet', 0, 1, 'internet')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Hóa đơn tiện ích khác', 0, 1, 'we_bill')");

        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Sửa & trang trí nhà', 0, 2, 'house')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Bảo dưỡng xe', 0, 2, 'repaircar')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Khám sức khỏe', 0, 2, 'health_check')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Bảo hiểm', 0, 2, 'insurance')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Giáo dục', 0, 2, 'education')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Đồ gia dụng', 0, 2, 'houseware')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Đồ dùng cá nhân', 0, 2, 'belongings')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Vật nuôi', 0, 2, 'pet')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Dịch vụ gia đình', 0, 2, 'social_services')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Các chi phí khác', 0, 2, 'more')");

        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Thể dục thể thao', 0, 3, 'exercise')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Làm đẹp', 0, 3, 'makeup')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Quà tặng & Quyên góp', 0, 3, 'gift')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Dịch vụ trực tuyến', 0, 3, 'customer_service_agent')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Vui chơi', 0, 3, 'entertain')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Ăn vặt', 0, 3, 'snack')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Chơi điện tử', 0, 3, 'game')");

        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Đầu tư', 1, 4, 'dautu')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Thu Nợ', 1, 4, 'thuno')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Đi vay', 1, 4, 'divay')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Cho vay', 0, 4, 'chovay')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Trả Nợ', 1, 4, 'trano')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Trả lãi', 0, 4, 'tralai')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Thu lãi', 1, 4, 'thulai')");

        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Lương', 1, 5, 'salary')");
        sqLiteDatabase.execSQL("INSERT INTO NHOM VALUES (null, 'Thu nhập', 1, 5, 'income')");
    }
    public void InsertLapKH(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("INSERT INTO NGANSACH VALUES (null, 'Ăn Uống - 10/2023', 1, 10000000, '10/2023', 1)");
    }
}
