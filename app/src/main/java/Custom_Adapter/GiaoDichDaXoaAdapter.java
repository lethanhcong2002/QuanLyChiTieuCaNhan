package Custom_Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.GiaoDich;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class GiaoDichDaXoaAdapter extends ArrayAdapter<GiaoDich> {
    private Context context;
    private OnActionCompletedListener actionCompletedListener;
    private List<GiaoDich> GDDXList;
    DAO_GiaoDich dao;
    DAO_DanhMuc_Nhom daoDanhMucNhom;
    public GiaoDichDaXoaAdapter(Context context, List<GiaoDich> GDDXList) {
        super(context, R.layout.custom_gddx_items, GDDXList);
        this.context = context;
        this.GDDXList = GDDXList;
    }

    public void setActionCompletedListener(OnActionCompletedListener listener) {
        this.actionCompletedListener = listener;
    }

    public interface OnActionCompletedListener {
        void onActionCompleted();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        dao = new DAO_GiaoDich(context);
        daoDanhMucNhom = new DAO_DanhMuc_Nhom(context);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_gddx_items, parent, false);
        }
        TextView txtGDDXName = convertView.findViewById(R.id.txtGDDXName);
        ImageView imgReLoad = convertView.findViewById(R.id.imgReLoad);
        ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

        if(position < GDDXList.size())
        {
            GiaoDich gd = GDDXList.get(position);
            String header = daoDanhMucNhom.getTenNhom(gd.getID_Nhom()) + " - " + gd.getNgay();
            imgReLoad.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            txtGDDXName.setText(header);
            imgReLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gd.setTrangThai(Boolean.valueOf("true"));
                    dao.ReloadGiaoDich(gd);
                    Toast.makeText(context, "Khôi Phục Thành Công", Toast.LENGTH_LONG).show();
                    if (actionCompletedListener != null) {
                        actionCompletedListener.onActionCompleted();
                    }
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tạo hộp thoại xác nhận
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn loại bỏ giao dịch này?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.DeleteGiaoDich(gd);
                            Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_LONG).show();
                            if (actionCompletedListener != null) {
                                actionCompletedListener.onActionCompleted();
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }
        else
        {
            imgReLoad.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
        }
        return convertView;
    }
    public void updateData(List<GiaoDich> updatedList) {
        GDDXList = updatedList;
        notifyDataSetChanged();
    }

}
