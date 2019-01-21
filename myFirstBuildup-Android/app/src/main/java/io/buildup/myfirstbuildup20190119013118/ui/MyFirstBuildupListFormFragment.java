
package io.buildup.myfirstbuildup20190119013118.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import buildup.ui.FormFragment;
import buildup.util.StringUtils;
import buildup.views.ImagePicker;
import buildup.views.TextWatcherAdapter;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSService;
import io.buildup.myfirstbuildup20190119013118.presenters.MyFirstBuildupListFormFormPresenter;
import io.buildup.myfirstbuildup20190119013118.R;
import java.io.IOException;
import java.io.File;

import static android.net.Uri.fromFile;
import buildup.ds.Datasource;
import buildup.ds.CrudDatasource;
import buildup.ds.SearchOptions;
import buildup.ds.filter.Filter;
import java.util.Arrays;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDSItem;
import io.buildup.myfirstbuildup20190119013118.ds.MyFirstBuildupSourceDS;

public class MyFirstBuildupListFormFragment extends FormFragment<MyFirstBuildupSourceDSItem> {

    private CrudDatasource<MyFirstBuildupSourceDSItem> datasource;
    public static MyFirstBuildupListFormFragment newInstance(Bundle args){
        MyFirstBuildupListFormFragment fr = new MyFirstBuildupListFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public MyFirstBuildupListFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new MyFirstBuildupListFormFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

    }

    @Override
    protected MyFirstBuildupSourceDSItem newItem() {
        MyFirstBuildupSourceDSItem newItem = new MyFirstBuildupSourceDSItem();
        return newItem;
    }

    private MyFirstBuildupSourceDSService getRestService(){
        return MyFirstBuildupSourceDSService.getInstance();
    }

    @Override
    protected int getLayout() {
        return R.layout.myfirstbuilduplistform_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final MyFirstBuildupSourceDSItem item, View view) {
        
        bindString(R.id.myfirstbuildupsourceds_name, item.name, false, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.name = s.toString();
            }
        });
        
        
        bindString(R.id.myfirstbuildupsourceds_desc, item.desc, false, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.desc = s.toString();
            }
        });
        
        
        bindString(R.id.myfirstbuildupsourceds_code, item.code, false, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.code = s.toString();
            }
        });
        
        
        bindImage(R.id.myfirstbuildupsourceds_picture,
            item.picture != null ?
                getRestService().getImageUrl(item.picture) : null,
            false,
            0,
            new ImagePicker.Callback(){
                @Override
                public void imageRemoved(){
                    item.picture = null;
                    item.pictureUri = null;
                    ((ImagePicker) getView().findViewById(R.id.myfirstbuildupsourceds_picture)).clear();
                }
            }
        );
        
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            ImagePicker picker = null;
            Uri imageUri = null;
            MyFirstBuildupSourceDSItem item = getItem();

            if((requestCode & ImagePicker.GALLERY_REQUEST_CODE) == ImagePicker.GALLERY_REQUEST_CODE) {
                imageUri = data.getData();
                switch (requestCode - ImagePicker.GALLERY_REQUEST_CODE) {
                        
                        case 0:   // picture field
                            item.pictureUri = imageUri;
                            item.picture = "cid:picture";
                            picker = (ImagePicker) getView().findViewById(R.id.myfirstbuildupsourceds_picture);
                            break;
                        
                    default:
                        return;
                }

                picker.setImageUri(imageUri);
            } else if((requestCode & ImagePicker.CAPTURE_REQUEST_CODE) == ImagePicker.CAPTURE_REQUEST_CODE) {
                switch (requestCode - ImagePicker.CAPTURE_REQUEST_CODE) {
                        
                        case 0:   // picture field
                            picker = (ImagePicker) getView().findViewById(R.id.myfirstbuildupsourceds_picture);
                            imageUri = fromFile(picker.getImageFile());
                        		item.pictureUri = imageUri;
                            item.picture = "cid:picture";
                            break;
                        
                    default:
                        return;
                }
                picker.setImageUri(imageUri);
            }
        }
    }
}
