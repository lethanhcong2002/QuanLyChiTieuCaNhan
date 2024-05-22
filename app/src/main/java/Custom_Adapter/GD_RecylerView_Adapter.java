package Custom_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.GiaoDich;
import Support.AppData;
import XuLyDuLieu.DAO_DanhMuc_Nhom;

public class GD_RecylerView_Adapter extends RecyclerView.Adapter<GD_RecylerView_Adapter.GiaoDichViewHolder>{

    DAO_DanhMuc_Nhom dao;
    private ClickListener clickListener;
    Context context;
    List<GiaoDich> lst ;
    public GD_RecylerView_Adapter(List<GiaoDich> list, Context context)
    {
        this.context = context;
        lst = list;
        dao = new DAO_DanhMuc_Nhom(context);
    }
    @NonNull
    @Override
    public GiaoDichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item_giaodich, parent, false);
        return new GiaoDichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoDichViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GiaoDich gd = lst.get(position);
        String header = dao.getTenNhom(gd.getID_Nhom()) + " - " + gd.getNgay();
        holder.txtHeader.setText(header);
        holder.txtTien.setText(AppData.getInstance().formatLargeNumber(gd.getTien()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class GiaoDichViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtTien;

        public GiaoDichViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHeader = itemView.findViewById(R.id.txtHeader);
            txtTien = itemView.findViewById(R.id.txtTien);
        }
    }

    public interface ClickListener {
        void onItemClick(int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
