

package io.buildup.myfirstbuildup20190119013118.ds;

import android.content.Context;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import buildup.ds.SearchOptions;
import buildup.ds.restds.AppNowDatasource;
import buildup.util.StringUtils;
import buildup.ds.restds.TypedByteArrayUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * "MyFirstBuildupSourceDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class MyFirstBuildupSourceDS extends AppNowDatasource<MyFirstBuildupSourceDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private MyFirstBuildupSourceDSService service;

    public static MyFirstBuildupSourceDS getInstance(SearchOptions searchOptions){
        return new MyFirstBuildupSourceDS(searchOptions);
    }

    private MyFirstBuildupSourceDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = MyFirstBuildupSourceDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<MyFirstBuildupSourceDSItem> listener) {
        if ("0".equals(id)) {
                        getItems(new Listener<List<MyFirstBuildupSourceDSItem>>() {
                @Override
                public void onSuccess(List<MyFirstBuildupSourceDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new MyFirstBuildupSourceDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
                      service.getServiceProxy().getMyFirstBuildupSourceDSItemById(id, new Callback<MyFirstBuildupSourceDSItem>() {
                @Override
                public void success(MyFirstBuildupSourceDSItem result, Response response) {
                                        listener.onSuccess(result);
                }

                @Override
                public void failure(RetrofitError error) {
                                        listener.onFailure(error);
                }
            });
        }
    }

    @Override
    public void getItems(final Listener<List<MyFirstBuildupSourceDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<MyFirstBuildupSourceDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
                service.getServiceProxy().queryMyFirstBuildupSourceDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<MyFirstBuildupSourceDSItem>>() {
            @Override
            public void success(List<MyFirstBuildupSourceDSItem> result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"name", "desc", "code"};
    }

    // Pagination

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    public void getUniqueValuesFor(String searchStr, final Listener<List<String>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
                service.getServiceProxy().distinct(searchStr, conditions, new Callback<List<String>>() {
             @Override
             public void success(List<String> result, Response response) {
                                  result.removeAll(Collections.<String>singleton(null));
                 listener.onSuccess(result);
             }

             @Override
             public void failure(RetrofitError error) {
                                  listener.onFailure(error);
             }
        });
    }

    @Override
    public URL getImageUrl(String path) {
        return service.getImageUrl(path);
    }

    @Override
    public void create(MyFirstBuildupSourceDSItem item, Listener<MyFirstBuildupSourceDSItem> listener) {
                    
        if(item.pictureUri != null){
            service.getServiceProxy().createMyFirstBuildupSourceDSItem(item,
                TypedByteArrayUtils.fromUri(item.pictureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().createMyFirstBuildupSourceDSItem(item, callbackFor(listener));
        
    }

    private Callback<MyFirstBuildupSourceDSItem> callbackFor(final Listener<MyFirstBuildupSourceDSItem> listener) {
      return new Callback<MyFirstBuildupSourceDSItem>() {
          @Override
          public void success(MyFirstBuildupSourceDSItem item, Response response) {
                            listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
                            listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(MyFirstBuildupSourceDSItem item, Listener<MyFirstBuildupSourceDSItem> listener) {
                    
        if(item.pictureUri != null){
            service.getServiceProxy().updateMyFirstBuildupSourceDSItem(item.getIdentifiableId(),
                item,
                TypedByteArrayUtils.fromUri(item.pictureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().updateMyFirstBuildupSourceDSItem(item.getIdentifiableId(), item, callbackFor(listener));
        
    }

    @Override
    public void deleteItem(MyFirstBuildupSourceDSItem item, final Listener<MyFirstBuildupSourceDSItem> listener) {
                service.getServiceProxy().deleteMyFirstBuildupSourceDSItemById(item.getIdentifiableId(), new Callback<MyFirstBuildupSourceDSItem>() {
            @Override
            public void success(MyFirstBuildupSourceDSItem result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<MyFirstBuildupSourceDSItem> items, final Listener<MyFirstBuildupSourceDSItem> listener) {
                service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<MyFirstBuildupSourceDSItem>>() {
            @Override
            public void success(List<MyFirstBuildupSourceDSItem> item, Response response) {
                                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<MyFirstBuildupSourceDSItem> items){
        List<String> ids = new ArrayList<>();
        for(MyFirstBuildupSourceDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}
