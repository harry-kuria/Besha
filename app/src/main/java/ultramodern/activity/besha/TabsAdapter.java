package ultramodern.activity.besha;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {
    Context myContext;
    int totaltabs;

    public TabsAdapter(@NonNull FragmentManager fm, Context myContext, int totaltabs) {
        super(fm);
        this.myContext = myContext;
        this.totaltabs = totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                CardsFragment cardsFragment = new CardsFragment();
                return cardsFragment;

            case 2:
                BanksFragment banksFragment = new BanksFragment();
                return banksFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
