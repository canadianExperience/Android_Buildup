
package io.buildup.myfirstbuildup20190119013118.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import buildup.behaviors.FabBehaviour;
import buildup.behaviors.ShareBehavior;
import buildup.ds.restds.AppNowDatasource;
import buildup.mvp.presenter.DetailCrudPresenter;
import buildup.util.ColorUtils;
import buildup.util.Constants;
import buildup.util.image.ImageLoader;
import buildup.util.image.PicassoImageLoader;
import buildup.util.StringUtils;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSService;
import io.buildup.myfirstbuildup20190119013118.presenters.MyFirstBuildupListDetailPresenter;
import io.buildup.myfirstbuildup20190119013118.R;
import java.net.URL;
import static buildup.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import buildup.ds.Datasource;
import buildup.ds.CrudDatasource;
import buildup.dialogs.DeleteItemDialog;
import android.support.v4.app.DialogFragment;
import buildup.ds.SearchOptions;
import buildup.ds.filter.Filter;
import java.util.Arrays;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDS;

public class MyFirstBuildupListDetailFragment extends buildup.ui.DetailFragment<MyFirstBuildupSourceDSItem> implements ShareBehavior.ShareListener, DeleteItemDialog.DeleteItemListener {

    private CrudDatasource<MyFirstBuildupSourceDSItem> datasource;
    public static MyFirstBuildupListDetailFragment newInstance(Bundle args){
        MyFirstBuildupListDetailFragment fr = new MyFirstBuildupListDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public MyFirstBuildupListDetailFragment(){
        super();
    }

    @Override
    public Datasource<MyFirstBuildupSourceDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = MyFirstBuildupSourceDS.getInstance(new SearchOptions());
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // the presenter for this view
        setPresenter(new MyFirstBuildupListDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new FabBehaviour(this, R.drawable.ic_edit_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailCrudPresenter<MyFirstBuildupSourceDSItem>) getPresenter()).editForm(getItem());
            }
        }));
        addBehavior(new ShareBehavior(getActivity(), this));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.myfirstbuilduplistdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final MyFirstBuildupSourceDSItem item, View view) {
        
        TextView view0 = (TextView) view.findViewById(R.id.view0);
        view0.setText((item.name != null ? item.name : ""));
        
        
        TextView view1 = (TextView) view.findViewById(R.id.view1);
        view1.setText((item.desc != null ? item.desc : ""));
        
        
        ImageView view2 = (ImageView) view.findViewById(R.id.view2);
        URL view2Media = ((AppNowDatasource) getDatasource()).getImageUrl(item.picture);
        if(view2Media != null){
            ImageLoader imageLoader = new PicassoImageLoader(view2.getContext(), false);
            imageLoader.load(imageLoaderRequest()
                                   .withPath(view2Media.toExternalForm())
                                   .withTargetView(view2)
                                   .fit()
                                   .build()
                    );
            
        } else {
            view2.setImageDrawable(null);
        }
    }

    @Override
    protected void onShow(MyFirstBuildupSourceDSItem item) {
        // set the title for this fragment
        getActivity().setTitle((item.code != null ? item.code : ""));
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), MyFirstBuildupListFormFormActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, Constants.MODE_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            DialogFragment deleteDialog = new DeleteItemDialog(this);
            deleteDialog.show(getActivity().getSupportFragmentManager(), "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void deleteItem(boolean isDeleted) {
        if(isDeleted) {
            ((DetailCrudPresenter<MyFirstBuildupSourceDSItem>) getPresenter()).deleteItem(getItem());
        }
    }
    @Override
    public void onShare() {
        MyFirstBuildupSourceDSItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.name != null ? item.name : "") + "\n" +
                    (item.desc != null ? item.desc : ""));
        intent.putExtra(Intent.EXTRA_SUBJECT, (item.code != null ? item.code : ""));

        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
}
