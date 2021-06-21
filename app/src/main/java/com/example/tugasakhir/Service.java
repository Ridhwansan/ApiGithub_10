package com.example.tugasakhir;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/search/users?q=language:java+location:indonesia")
    Call<ItemResponse> getItems();
}
