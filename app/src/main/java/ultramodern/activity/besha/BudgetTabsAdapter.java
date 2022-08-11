package ultramodern.activity.besha;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BudgetTabsAdapter extends FragmentPagerAdapter {
    Context myContext;
    int totaltabs;

    public BudgetTabsAdapter(@NonNull FragmentManager fm, Context myContext, int totaltabs) {
        super(fm);
        this.myContext = myContext;
        this.totaltabs = totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BudgetFragment budgetFragment = new BudgetFragment();
                return budgetFragment;

            case 1:
                GoalsFragment goalsFragment = new GoalsFragment();
                return goalsFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
