package Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.ThuChiTungNhom;


public class BC_DateRange extends ArrayAdapter<ThuChiTungNhom> {
    public BC_DateRange(@NonNull Context context, List<ThuChiTungNhom> lst) {
        super(context, 0, lst);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_bc_dialog, parent, false);
        }
        ThuChiTungNhom item = getItem(position);

        TextView txtNhom = convertView.findViewById(R.id.txtNhom);
        TextView txtTien = convertView.findViewById(R.id.txtTien);

        if (item != null) {
            txtNhom.setText(item.getName());
            txtTien.setText(String.valueOf(item.getTongTien()));
        }

        return convertView;
    }
}
