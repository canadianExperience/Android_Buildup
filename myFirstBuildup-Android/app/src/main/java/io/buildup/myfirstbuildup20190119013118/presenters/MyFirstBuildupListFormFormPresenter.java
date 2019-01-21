
package io.buildup.myfirstbuildup20190119013118.presenters;

import io.buildup.myfirstbuildup20190119013118.R;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;

import java.util.List;

import buildup.ds.CrudDatasource;
import buildup.ds.Datasource;
import buildup.mvp.presenter.BaseFormPresenter;
import buildup.mvp.view.FormView;

public class MyFirstBuildupListFormFormPresenter extends BaseFormPresenter<MyFirstBuildupSourceDSItem> {

    private final CrudDatasource<MyFirstBuildupSourceDSItem> datasource;

    public MyFirstBuildupListFormFormPresenter(CrudDatasource<MyFirstBuildupSourceDSItem> datasource, FormView<MyFirstBuildupSourceDSItem> view){
        super(view);
        this.datasource = datasource;
    }

    @Override
    public void deleteItem(MyFirstBuildupSourceDSItem item) {
        datasource.deleteItem(item, new OnItemDeletedListener());
    }

    @Override
    public void save(MyFirstBuildupSourceDSItem item) {
        // validate
        if (validate(item)){
            datasource.updateItem(item, new OnItemUpdatedListener());
        } else {
            view.showMessage(R.string.correct_errors, false);
        }
    }

    @Override
    public void create(MyFirstBuildupSourceDSItem item) {
        // validate
        if (validate(item)){
            datasource.create(item, new OnItemCreatedListener());
        } else {
            view.showMessage(R.string.correct_errors, false);
        }
    }

    private class OnItemDeletedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(MyFirstBuildupSourceDSItem  item) {
                        view.showMessage(R.string.item_deleted, true);
            view.close(true);
        }
    }

    private class OnItemUpdatedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(MyFirstBuildupSourceDSItem item) {
                        view.setItem(item);
            view.showMessage(R.string.item_updated, true);
            view.close(true);
        }
    }

    private class OnItemCreatedListener extends ShowingErrorOnFailureListener {
        @Override
        public void onSuccess(MyFirstBuildupSourceDSItem item) {
                        view.setItem(item);
            view.showMessage(R.string.item_created, true);
            view.close(true);
        }
    }

    private abstract class ShowingErrorOnFailureListener implements Datasource.Listener<MyFirstBuildupSourceDSItem > {
        @Override
        public void onFailure(Exception e) {
            view.showMessage(R.string.error_data_generic, true);
        }
    }

}
