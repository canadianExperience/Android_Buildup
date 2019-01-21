
package io.buildup.myfirstbuildup20190119013118.ui;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import buildup.ui.DrawerActivity;

import buildup.actions.StartActivityAction;
import buildup.util.Constants;
import io.buildup.myfirstbuildup20190119013118.R;

public class MyFirstBuildupMainActivity extends DrawerActivity {

    private final SparseArray<Class<? extends Fragment>> sectionFragments = new SparseArray<>();
    {
            sectionFragments.append(R.id.entry0, MyFirstBuildupListFragment.class);
    }

    @Override
    public SparseArray<Class<? extends Fragment>> getSectionFragmentClasses() {
      return sectionFragments;
    }

}
