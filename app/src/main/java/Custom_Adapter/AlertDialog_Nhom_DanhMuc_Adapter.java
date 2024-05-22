package Custom_Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.DanhMuc;
import BangDuLieu.Nhom;

public class AlertDialog_Nhom_DanhMuc_Adapter extends ArrayAdapter<Object> {
    private LayoutInflater inflater;

    public AlertDialog_Nhom_DanhMuc_Adapter(Context context, List<Object> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);

        if (item instanceof DanhMuc) {
            DanhMuc danhMuc = (DanhMuc) item;
            convertView = inflater.inflate(R.layout.custom_danhmuc_items, parent, false);
            TextView textView = convertView.findViewById(R.id.textViewDanhMuc);
            textView.setText(Html.fromHtml("<b>" + danhMuc.getName() + "</b>"));
            textView.setTypeface(null, Typeface.BOLD);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else if (item instanceof Nhom) {
            Nhom nhom = (Nhom) item;
            convertView = inflater.inflate(R.layout.custom_nhom_items, parent, false);
            TextView textView = convertView.findViewById(R.id.textViewNhom);
            ImageView iv = convertView.findViewById(R.id.iv_icon);
            textView.setText(nhom.getName());

            String iconName = nhom.getHinhAnh();

            if (iconName != null && !iconName.isEmpty()) {
                int resourceId = convertView.getResources().getIdentifier(iconName, "drawable", convertView.getContext().getPackageName());
                if (resourceId != 0) {
                    iv.setImageResource(resourceId);
                }
            }
        }

        return convertView;
    }
}
