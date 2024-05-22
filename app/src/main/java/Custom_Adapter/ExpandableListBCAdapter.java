package Custom_Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;

import java.util.List;

import BangDuLieu.ThuChiTrongThang;
import BangDuLieu.ThuChiTungNhom;
import Support.AppData;
import XuLyDuLieu.DAO_GiaoDich;

public class ExpandableListBCAdapter extends BaseExpandableListAdapter {
    // là một lớp tùy chỉnh dùng để tạo và quản lý danh sách dữ liệu được hiển thị trong một ExpandableListView trong ứng dụng Android. Lớp ExpandableListAdapter là một lớp adapter dành cho ExpandableListView để hiển thị dữ liệu về thu chi trong từng tháng (Tháng) và các khoản thu chi theo từng nhóm (Nhom).
    private Context context;
    private List<ThuChiTrongThang> monthDataList;
    private int Year;
    DAO_GiaoDich dao;

    public ExpandableListBCAdapter(Context context, List<ThuChiTrongThang> monthDataList, int year) {
        this.context = context;
        this.monthDataList = monthDataList;
        this.Year = year;
        dao = new DAO_GiaoDich(context);
    }
    @Override
    public int getGroupCount() {
        return monthDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return monthDataList.get(groupPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandablelist_thang, null);
        }

        ImageView arrowIcon = convertView.findViewById(R.id.arrowIcon);
        arrowIcon.setImageResource(0);
        TextView monthTextView = convertView.findViewById(R.id.txtThang);
        TextView totalIncome = convertView.findViewById(R.id.totalIncome);
        TextView totalExpense = convertView.findViewById(R.id.totalExpense);

        if (isExpanded) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.END;
            arrowIcon.setLayoutParams(layoutParams);
            arrowIcon.setImageResource(android.R.drawable.arrow_up_float);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.END;
            arrowIcon.setLayoutParams(layoutParams);
            arrowIcon.setImageResource(android.R.drawable.arrow_down_float);
        }

        ImageView img = convertView.findViewById(R.id.iv_icon);
        if (calculateDanhGia(monthDataList.get(groupPosition).getTongThuNhap(), monthDataList.get(groupPosition).getTongChiTieu()) == true)
        {
            img.setImageResource(R.drawable.dung);
        }
        else
        {
            img.setImageResource(R.drawable.sai);
        }
        monthTextView.setText("Tháng: " + monthDataList.get(groupPosition).getMonth());
        totalIncome.setText("Tổng thu nhập: " + AppData.getInstance().formatLargeNumber((long) monthDataList.get(groupPosition).getTongThuNhap()));
        totalExpense.setText("Tổng chi tiêu: " + AppData.getInstance().formatLargeNumber((long) monthDataList.get(groupPosition).getTongChiTieu()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandablelist_child_thang, null);
        }

        RecyclerView recyclerView = convertView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        String monthofyear = monthDataList.get(groupPosition).getMonth() + "/" + Year;
        List<ThuChiTungNhom> data = dao.getNhomData(AppData.getInstance().getKhachHang().getID(), monthofyear);

        Child_Nhom_TongTien_Adapter adapter = new Child_Nhom_TongTien_Adapter(data);
        recyclerView.setAdapter(adapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private boolean calculateDanhGia(double tongThuNhap, double tongChiTieu) {
        if (tongThuNhap > tongChiTieu) {
            return true;
        } else {
            return false;
        }
    }

}
