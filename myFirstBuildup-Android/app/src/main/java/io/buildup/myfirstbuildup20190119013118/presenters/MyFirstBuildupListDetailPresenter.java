
package io.buildup.myfirstbuildup20190119013118.presenters;

import io.buildup.myfirstbuildup20190119013118.R;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;

import buildup.ds.CrudDatasource;
import buildup.ds.Datasource;
import buildup.mvp.presenter.BasePresenter;
import buildup.mvp.presenter.DetailCrudPresenter;
import buildup.mvp.view.DetailView;

public class MyFirstBuildupListDetailPresenter extends BasePresenter implements DetailCrudPresenter<MyFirstBuildupSourceDSItem>,
      Datasource.Listener<MyFirstBuildupSourceDSItem> {

    private final CrudDatasource<MyFirstBuildupSourceDSItem> datasource;
    private final DetailView view;

    public MyFirstBuildupListDetailPresenter(CrudDatasource<MyFirstBuildupSourceDSItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(MyFirstBuildupSourceDSItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(MyFirstBuildupSourceDSItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(MyFirstBuildupSourceDSItem item) {
                view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
}
