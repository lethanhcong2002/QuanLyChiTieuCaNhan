package Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.GiaoDich;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;

public class GiaoDichAdapter extends ArrayAdapter<GiaoDich> {
    private Context context;
    private List<GiaoDich> giaoDichList;
    DAO_DanhMuc_Nhom dao;

    public GiaoDichAdapter(Context context, List<GiaoDich> giaoDichList) {
        super(context, R.layout.custom_list_item_giaodich, giaoDichList);
        this.context = context;
        this.giaoDichList = giaoDichList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dao = new DAO_DanhMuc_Nhom(context);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_list_item_giaodich, parent, false);
        }

        GiaoDich giaoDich = giaoDichList.get(position);

        TextView txtHeader = convertView.findViewById(R.id.txtHeader);
        TextView txtTien = convertView.findViewById(R.id.txtTien);
        String header = dao.getTenNhom(giaoDich.getID_Nhom()) + " - " + giaoDich.getNgay();
        txtHeader.setText(header);
        txtTien.setText(AppData.getInstance().formatLargeNumber((long) giaoDich.getTien()));

        return convertView;
    }

}

