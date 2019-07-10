package com.example.androidphp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidphp.models.Pojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String apiPath = "https://jsonplaceholder.typicode.com/todos?_limit=5";

    private jsonPlaceHolderinterface jsonplaceholder;
    private TextView textView;
    private EditText editText;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text_view_result);
        editText=findViewById(R.id.namefield);
        submit_btn=findViewById(R.id.submitbtn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });

        Gson gson=new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", Credentials.basic("ck_d5a10c7f59d92b6defed8da69096c1bf95ccfbe9", "cs_a0840eb6bc47952051611c0fa5e42b7854d7d223"))
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://validation.appetizers.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonplaceholder= retrofit.create(jsonPlaceHolderinterface.class);
         getPosts();
     //   getComments();
      // createPost();
     //  updateRequest();
       // deletePost();

    }



    private void deletePost() {
        Call<Void> call=jsonplaceholder.deletePosts(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textView.setText("Code: "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
        }

    private void updateRequest() {
        Posts posts=new Posts(12,"null", "New text");
        Call<Posts> call=jsonplaceholder.putPost(5,posts);
    //    Call<Posts> call=jsonplaceholder.patchPost(5,posts);

        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }
                Posts postResponse = response.body();


                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                //content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
               // content += "Text: " + postResponse.getText() + "\n";
                textView.setText(content);
            }
            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        } );
    }


    private void createPost() {
        String names=editText.getText().toString();
    Posts posts=new Posts(23,"New title", names);

//    Call<Posts> call=jsonplaceholder.createPost(posts);
        Call<Posts> call=jsonplaceholder.createPost(23,"new title",names + "");

    call.enqueue(new Callback<Posts>() {
        @Override
        public void onResponse(Call<Posts> call, Response<Posts> response) {
            if (!response.isSuccessful()) {
                textView.setText("Code:" + response.code());
                return;
            }
           Posts postResponse = response.body();


                String content = "";
                content += "Code: " + response.code() + "\n";
              //  content += "ID: " + postResponse.getId() + "\n";
               // content += "User ID: " + postResponse.getUserId() + "\n";
                //content += "Title: " + postResponse.getTitle() + "\n";
                content += "Name: " + postResponse.getText() + "\n";
                textView.setText(content);

            }

        @Override
        public void onFailure(Call<Posts> call, Throwable t) {

            textView.setText(t.getMessage());
        }
    });
    }


    private void getComments() {
        Call<List<comments>> call=jsonplaceholder.getComments(3);
        call.enqueue(new Callback<List<comments>>() {
            @Override
            public void onResponse(Call<List<comments>> call, Response<List<comments>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }
                List<comments> comment = response.body();

                for (comments com : comment) {
                    String content = "";
                    content += "ID: " + com.getId() + "\n";
                    content += "Post ID: " + com.getPostId() + "\n";
                    content += "Name: " + com.getName() + "\n";
                    content += "Email: " + com.getEmail() + "\n";
                    content += "Text: " + com.getText() + "\n";
                    textView.append(content);

                }
            }
            @Override
            public void onFailure(Call<List<comments>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {

        Map<String,String> parameter=new HashMap<>();


        Call<List<Pojo>> call=jsonplaceholder.getPosts(parameter);
//       Call<List<Posts>> call=jsonplaceholder.getPosts(new Integer[]{2,3,6},"id","desc");
        call.enqueue((new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {

                if(!response.isSuccessful()){
                    textView.setText("Code:"+response.code());
                    return;
                }

                List<Pojo> posts=response.body();

                for(Pojo post:posts){
                    String content="";
                    content+="ID: "+post.getId()+"\n";
                    content+="Name: "+post.getName()+"\n";
//                    content+="Title: "+post.getTitle()+"\n";
//                    content+="Body: "+post.getText()+"\n";

                    textView.append(content);
                }
            }


            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        }));
    }
}