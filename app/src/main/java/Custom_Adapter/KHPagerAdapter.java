package Custom_Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.doancuoiki.fragment.KHContinuesFragment;
import com.example.doancuoiki.fragment.KHEndFragment;

public class KHPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 2;

    public KHPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KHContinuesFragment();
            case 1:
                return new KHEndFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}


