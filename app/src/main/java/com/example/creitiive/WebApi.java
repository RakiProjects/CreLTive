package com.example.creitiive;

        import com.example.creitiive.model.Blog;
        import com.example.creitiive.model.Params;
        import com.example.creitiive.model.SingleBlogModel;
        import com.example.creitiive.model.Token;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.Headers;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface WebApi {

    // Login
    @POST("login")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<Token> getToken (@Body Params params);

    // blog list
    @GET("blogs")
    Call<ArrayList<Blog>> getBlogList(@Header("Accept") String accept, @Header("X-Authorize") String token);

    // single blog
    @GET("blogs/{id}")
    Call<SingleBlogModel> getSingleBlog(@Header("Accept") String accept, @Header("X-Authorize") String token, @Path("id") String id);
}

