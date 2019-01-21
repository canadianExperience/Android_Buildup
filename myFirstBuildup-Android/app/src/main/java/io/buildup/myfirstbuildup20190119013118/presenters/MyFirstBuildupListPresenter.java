
package io.buildup.myfirstbuildup20190119013118.presenters;

import io.buildup.myfirstbuildup20190119013118.R;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;

import java.util.List;

import buildup.ds.CrudDatasource;
import buildup.ds.Datasource;
import buildup.mvp.presenter.BasePresenter;
import buildup.mvp.presenter.ListCrudPresenter;
import buildup.mvp.view.CrudListView;

public class MyFirstBuildupListPresenter extends BasePresenter implements ListCrudPresenter<MyFirstBuildupSourceDSItem>,
      Datasource.Listener<MyFirstBuildupSourceDSItem>{

    private final CrudDatasource<MyFirstBuildupSourceDSItem> crudDatasource;
    private final CrudListView<MyFirstBuildupSourceDSItem> view;

    public MyFirstBuildupListPresenter(CrudDatasource<MyFirstBuildupSourceDSItem> crudDatasource,
                                         CrudListView<MyFirstBuildupSourceDSItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(MyFirstBuildupSourceDSItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<MyFirstBuildupSourceDSItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(MyFirstBuildupSourceDSItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(MyFirstBuildupSourceDSItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(MyFirstBuildupSourceDSItem item) {
                view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

}
