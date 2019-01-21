
package io.buildup.myfirstbuildup20190119013118.ds;
import java.net.URL;
import io.buildup.myfirstbuildup20190119013118.R;
import buildup.ds.RestService;
import buildup.util.StringUtils;

/**
 * "MyFirstBuildupSourceDSService" REST Service implementation
 */
public class MyFirstBuildupSourceDSService extends RestService<MyFirstBuildupSourceDSServiceRest>{

    public static MyFirstBuildupSourceDSService getInstance(){
          return new MyFirstBuildupSourceDSService();
    }

    private MyFirstBuildupSourceDSService() {
        super(MyFirstBuildupSourceDSServiceRest.class);

    }

    @Override
    public String getServerUrl() {
        return "https://pods.hivepod.io";
    }

    @Override
    protected String getApiKey() {
        return "sTd0HEpW";
    }

    @Override
    public URL getImageUrl(String path){
        return StringUtils.parseUrl("https://pods.hivepod.io/app/5c44d97cd3e9420400941fc7",
                path,
                "apikey=sTd0HEpW");
    }

}
