package io.buildup.myfirstbuildup20190119013118.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import buildup.behaviors.FabBehaviour;
import buildup.behaviors.SelectionBehavior;
import buildup.ds.Datasource;
import buildup.ds.restds.AppNowDatasource;
import buildup.ui.ListGridFragment;
import buildup.util.Constants;
import buildup.util.image.ImageLoader;
import buildup.util.image.PicassoImageLoader;
import buildup.util.StringUtils;
import buildup.util.ViewHolder;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSService;
import io.buildup.myfirstbuildup20190119013118.presenters.MyFirstBuildupListPresenter;
import io.buildup.myfirstbuildup20190119013118.R;
import java.net.URL;
import java.util.List;
import static buildup.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import buildup.ds.SearchOptions;
import buildup.ds.filter.Filter;
import java.util.Arrays;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDS;
import buildup.mvp.view.CrudListView;
import buildup.ds.CrudDatasource;
import buildup.dialogs.DeleteItemDialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import buildup.util.Constants;
import static buildup.util.NavigationUtils.generateIntentToDetailOrForm;

/**
 * "MyFirstBuildupListFragment" listing
 */
public class MyFirstBuildupListFragment extends ListGridFragment<MyFirstBuildupSourceDSItem> implements CrudListView<MyFirstBuildupSourceDSItem>, DeleteItemDialog.DeleteItemListener {

    private CrudDatasource<MyFirstBuildupSourceDSItem> datasource;
    private List<MyFirstBuildupSourceDSItem> selectedItemsForDelete;

    // "Add" button
    private FabBehaviour fabBehavior;

    public static MyFirstBuildupListFragment newInstance(Bundle args) {
        MyFirstBuildupListFragment fr = new MyFirstBuildupListFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new MyFirstBuildupListPresenter(
            (CrudDatasource) getDatasource(),
            this
        ));
        // Multiple selection
        SelectionBehavior<MyFirstBuildupSourceDSItem> selectionBehavior = new SelectionBehavior<>(
            this,
            R.string.remove_items,
            R.drawable.ic_delete_alpha);

        selectionBehavior.setCallback(new SelectionBehavior.Callback<MyFirstBuildupSourceDSItem>() {
            @Override
            public void onSelected(List<MyFirstBuildupSourceDSItem> selectedItems) {
                selectedItemsForDelete = selectedItems;
                DialogFragment deleteDialog = new DeleteItemDialog(MyFirstBuildupListFragment.this);
                deleteDialog.show(getActivity().getSupportFragmentManager(), "");
            }
        });
        addBehavior(selectionBehavior);

        // FAB button
        fabBehavior = new FabBehaviour(this, R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addForm();
            }
        });
        addBehavior(fabBehavior);
        
    }

    protected SearchOptions getSearchOptions() {
        SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        return searchOptionsBuilder.build();
    }


    /**
    * Layout for the list itselft
    */
    @Override
    protected int getLayout() {
        return R.layout.fragment_grid;
    }

    /**
    * Layout for each element in the list
    */
    @Override
    protected int getItemLayout() {
        return R.layout.myfirstbuilduplist_item;
    }

    @Override
    protected Datasource<MyFirstBuildupSourceDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = MyFirstBuildupSourceDS.getInstance(getSearchOptions());
        return datasource;
    }

    @Override
    protected void bindView(MyFirstBuildupSourceDSItem item, View view, int position) {
        
        ImageLoader imageLoader = new PicassoImageLoader(view.getContext(), false);
        ImageView image = ViewHolder.get(view, R.id.image);
        URL imageMedia = ((AppNowDatasource) getDatasource()).getImageUrl(item.picture);
        if(imageMedia != null){
            imageLoader.load(imageLoaderRequest()
                          .withPath(imageMedia.toExternalForm())
                          .withTargetView(image)
                          .fit()
        				  .build()
            );
        	
        }
        else {
            imageLoader.load(imageLoaderRequest()
                          .withResourceToLoad(R.drawable.ic_ibm_placeholder)
                          .withTargetView(image)
        				  .build()
            );
        }
        
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        title.setText((item.code != null ? item.code : ""));
        
    }

    @Override
    protected void itemClicked(final MyFirstBuildupSourceDSItem item, final int position) {
        fabBehavior.hide(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getPresenter().detail(item, position);
            }
        });
    }

    @Override
    public void showDetail(MyFirstBuildupSourceDSItem item, int position) {
        Intent intent = generateIntentToDetailOrForm(item,
            position,
            getActivity(),
            MyFirstBuildupListDetailActivity.class);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void showAdd() {
        startActivityForResult(
                generateIntentToDetailOrForm(null,
                        0,
                        getActivity(),
                        MyFirstBuildupListFormFormActivity.class
                ), Constants.MODE_CREATE
        );
    }

    @Override
    public void showEdit(MyFirstBuildupSourceDSItem item, int position) {
        startActivityForResult(
                generateIntentToDetailOrForm(item,
                        position,
                        getActivity(),
                        MyFirstBuildupListFormFormActivity.class
                ), Constants.MODE_EDIT
        );
    }

    @Override
    public void deleteItem(boolean isDeleted) {
        if (isDeleted) {
            getPresenter().deleteItems(selectedItemsForDelete);
        }

        selectedItemsForDelete.clear();
    }

}
