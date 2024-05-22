package Custom_Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.NganSach;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;
import XuLyDuLieu.DAO_GiaoDich;

public class NganSachAdapter extends RecyclerView.Adapter<NganSachAdapter.ViewHolder> {
    private Context context;
    private List<NganSach> List;
    DAO_DanhMuc_Nhom dao;
    DAO_GiaoDich dao_giaoDich;

    public NganSachAdapter(Context context, List<NganSach> List) {
        this.context = context;
        this.List = List;
        dao = new DAO_DanhMuc_Nhom(context);
        dao_giaoDich = new DAO_GiaoDich(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTenNhom, txtMucTieu, txtDaTieu, txtChenhLech;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenNhom = itemView.findViewById(R.id.txtTenNhomKH);
            txtMucTieu = itemView.findViewById(R.id.txtMucTieu);
            txtDaTieu = itemView.findViewById(R.id.txtDaTieu);
            progressBar = itemView.findViewById(R.id.pbKHTC);
            txtChenhLech = itemView.findViewById(R.id.txtChenhLech);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_kh_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NganSach ns = List.get(position);
        double maxValue = ns.getTien();
        double currentValue = dao_giaoDich.TienTrongThang(ns.getID_User(), ns.getID_Nhom(), ns.getNgay());
        holder.progressBar.setMax((int) maxValue);

        String text = dao.getTenNhom(ns.getID_Nhom())  + " - " + ns.getNgay();
        holder.txtTenNhom.setText(text);
        holder.txtMucTieu.setText(AppData.getInstance().formatLargeNumber(ns.getTien()));
        holder.txtDaTieu.setText(AppData.getInstance().formatLargeNumber(dao_giaoDich.TienTrongThang(ns.getID_User(), ns.getID_Nhom(), ns.getNgay())));

        if(currentValue >= maxValue) {
            holder.progressBar.setProgress((int) maxValue);
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.red)));
            holder.txtChenhLech.setText("Bạn ta sử dụng tiền quá mục tiêu đặt ra!");
            holder.txtChenhLech.setTextColor(ColorStateList.valueOf(context.getColor(R.color.red)));
        } else {
            double percentage = (currentValue * 100) / maxValue;
            if(percentage >= 75)
            {
                holder.progressBar.setProgress((int) currentValue);
                holder.progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.color_warning)));
                holder.txtChenhLech.setText(String.format("%.2f%%", percentage));
            }
            else {
                holder.progressBar.setProgress((int) currentValue);
                holder.txtChenhLech.setText(String.format("%.2f%%", percentage));
            }
        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}
