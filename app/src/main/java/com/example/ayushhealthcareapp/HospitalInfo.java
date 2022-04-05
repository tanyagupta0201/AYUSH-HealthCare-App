package com.example.ayushhealthcareapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HospitalInfo {

    @GET("77.376184/28.594191")
    Call<String> getPost();
}
