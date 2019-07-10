package com.example.androidphp;

import com.example.androidphp.models.Pojo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface jsonPlaceHolderinterface {
    @GET("wp-json/wc/v3/products/categories")
    Call<List<Pojo>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
            ) ;

    @GET("wp-json/wc/v3/products/categories")
    Call<List<Pojo>> getPosts(
            @QueryMap Map<String,String> parameter
            );
//?consumer_key=ck_d5a10c7f59d92b6defed8da69096c1bf95ccfbe9&consumer_secret=cs_a0840eb6bc47952051611c0fa5e42b7854d7d223"
    @GET("posts/{id}/comments")
    Call<List<comments>> getComments(@Path("id") int postId);

    //to post data to the server
    @POST("posts")
    Call<Posts> createPost(@Body Posts posts);

    //another way of sending data to server
    @FormUrlEncoded
    @POST("posts")
    Call<Posts> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    //to update an existing resource:put,all objects need to be sent
    @PUT("posts/{id}")
    Call<Posts> putPost(@Path("id") int id,@Body Posts posts);

    //only the field  is changed,all objects are not replaced
    @PUT("posts/{id}")
    Call<Posts> patchPost(@Path("id") int id,@Body Posts posts);

    @DELETE("posts/{id}")
    Call<Void> deletePosts(@Path("id") int id);
}
