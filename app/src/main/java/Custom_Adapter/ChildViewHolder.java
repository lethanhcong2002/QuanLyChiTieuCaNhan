package Custom_Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    public TextView tenNhomTextView;
    public TextView tongTienTextView;

    public ChildViewHolder(View itemView) {
        super(itemView);
        tenNhomTextView = itemView.findViewById(R.id.tenNhomTextView);
        tongTienTextView = itemView.findViewById(R.id.tongTienTextView);
    }
}

