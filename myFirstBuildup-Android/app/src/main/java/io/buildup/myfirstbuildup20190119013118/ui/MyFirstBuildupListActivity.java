

package io.buildup.myfirstbuildup20190119013118.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.buildup.myfirstbuildup20190119013118.R;

import buildup.ui.BaseListingActivity;
/**
 * MyFirstBuildupListActivity list activity
 */
public class MyFirstBuildupListActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        setTitle(getString(R.string.myFirstBuildupListActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return MyFirstBuildupListFragment.class;
    }

}
