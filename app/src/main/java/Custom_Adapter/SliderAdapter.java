package Custom_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;
import java.util.List;

import BangDuLieu.ThuChiTungNhom;
import Support.AppData;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<ThuChiTungNhom> imageList;

    public SliderAdapter(List<ThuChiTungNhom> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_thuchi, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        if (imageList.size() == 1 && "Default".equals(imageList.get(0).getName())) {
            holder.cvSlider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.sliderImage.setImageResource(R.drawable.add_new);
            holder.txtTen.setText("Chưa có bất kỳ dữ liệu nào!");
            holder.txtTongTien.setText("Hãy nhập ngay đi.");
        } else {
            ThuChiTungNhom item = imageList.get(position);
            String groupName = item.getName();
            double amount = item.getTongTien();
            String icon = item.getIcon();
            int resourceId = holder.itemView.getResources().getIdentifier(icon, "drawable", holder.itemView.getContext().getPackageName());
            holder.sliderImage.setImageResource(resourceId);
            holder.txtTen.setText(groupName);
            holder.txtTongTien.setText(AppData.getInstance().formatLargeNumber(amount));
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        CardView cvSlider;
        ImageView sliderImage;
        TextView txtTen;
        TextView txtTongTien;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            cvSlider = itemView.findViewById(R.id.cvSlider);
            sliderImage = itemView.findViewById(R.id.iv_icon);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtTongTien = itemView.findViewById(R.id.txtTien);
        }
    }
}
