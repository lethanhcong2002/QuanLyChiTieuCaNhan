package Custom_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.ThuChiTungNhom;
import Support.AppData;

public class Child_Nhom_TongTien_Adapter extends RecyclerView.Adapter<ChildViewHolder> {
        private List<ThuChiTungNhom> dataList;

        public Child_Nhom_TongTien_Adapter(List<ThuChiTungNhom> dataList) {
                this.dataList = dataList;
        }

        @NonNull
        @Override
        public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item_nhom_tien, parent, false);
                return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
                ThuChiTungNhom nhomData = dataList.get(position);
                holder.tenNhomTextView.setText(nhomData.getName());
                holder.tongTienTextView.setText(String.valueOf(AppData.getInstance().formatLargeNumber(nhomData.getTongTien())));
        }

        @Override
        public int getItemCount() {
                return dataList.size();
        }
}
