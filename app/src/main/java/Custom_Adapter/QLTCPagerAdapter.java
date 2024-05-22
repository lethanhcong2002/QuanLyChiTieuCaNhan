package Custom_Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doancuoiki.fragment.ExpenseFragment;
import com.example.doancuoiki.fragment.IncomeFragment;

public class QLTCPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 2;

    public QLTCPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IncomeFragment();
            case 1:
                return new ExpenseFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

