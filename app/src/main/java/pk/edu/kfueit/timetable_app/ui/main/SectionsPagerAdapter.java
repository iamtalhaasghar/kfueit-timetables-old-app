package pk.edu.kfueit.timetable_app.ui.main;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import pk.edu.kfueit.timetable_app.R;
import pk.edu.kfueit.timetable_app.TimeTable;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_mon, R.string.tab_text_tue,
            R.string.tab_text_wed, R.string.tab_text_thu, R.string.tab_text_fri,
            R.string.tab_text_sat,R.string.tab_text_sun};
    private final Context mContext;
    private JSONObject timeTable;

    public SectionsPagerAdapter(Context context, FragmentManager fm, JSONObject timeTable) {
        super(fm);
        mContext = context;
        this.timeTable = timeTable;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        PlaceholderFragment tempPlaceholderFragment = null;
        boolean success = false;
        try {
            if(timeTable != null){
                tempPlaceholderFragment = new PlaceholderFragment(timeTable.getJSONArray(TimeTable.getDay(position + 1)));
                success = true;
            }

        } catch (JSONException e) {
            System.out.println(e);
        }
        if(!success){
            tempPlaceholderFragment = new PlaceholderFragment(null);
        }
        return tempPlaceholderFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}