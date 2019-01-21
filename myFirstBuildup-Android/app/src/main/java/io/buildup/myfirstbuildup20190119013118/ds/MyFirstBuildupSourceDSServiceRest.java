
package io.buildup.myfirstbuildup20190119013118.ds;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Path;
import retrofit.http.PUT;
import retrofit.mime.TypedByteArray;
import retrofit.http.Part;
import retrofit.http.Multipart;

public interface MyFirstBuildupSourceDSServiceRest{

	@GET("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS")
	void queryMyFirstBuildupSourceDSItem(
		@Query("skip") String skip,
		@Query("limit") String limit,
		@Query("conditions") String conditions,
		@Query("sort") String sort,
		@Query("select") String select,
		@Query("populate") String populate,
		Callback<List<MyFirstBuildupSourceDSItem>> cb);

	@GET("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS/{id}")
	void getMyFirstBuildupSourceDSItemById(@Path("id") String id, Callback<MyFirstBuildupSourceDSItem> cb);

	@DELETE("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS/{id}")
  void deleteMyFirstBuildupSourceDSItemById(@Path("id") String id, Callback<MyFirstBuildupSourceDSItem> cb);

  @POST("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS/deleteByIds")
  void deleteByIds(@Body List<String> ids, Callback<List<MyFirstBuildupSourceDSItem>> cb);

  @POST("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS")
  void createMyFirstBuildupSourceDSItem(@Body MyFirstBuildupSourceDSItem item, Callback<MyFirstBuildupSourceDSItem> cb);

  @PUT("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS/{id}")
  void updateMyFirstBuildupSourceDSItem(@Path("id") String id, @Body MyFirstBuildupSourceDSItem item, Callback<MyFirstBuildupSourceDSItem> cb);

  @GET("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS")
  void distinct(
        @Query("distinct") String colName,
        @Query("conditions") String conditions,
        Callback<List<String>> cb);
    
    @Multipart
    @POST("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS")
    void createMyFirstBuildupSourceDSItem(
        @Part("data") MyFirstBuildupSourceDSItem item,
        @Part("picture") TypedByteArray picture,
        Callback<MyFirstBuildupSourceDSItem> cb);
    
    @Multipart
    @PUT("/app/5c44d97cd3e9420400941fc7/r/myFirstBuildupSourceDS/{id}")
    void updateMyFirstBuildupSourceDSItem(
        @Path("id") String id,
        @Part("data") MyFirstBuildupSourceDSItem item,
        @Part("picture") TypedByteArray picture,
        Callback<MyFirstBuildupSourceDSItem> cb);
}
