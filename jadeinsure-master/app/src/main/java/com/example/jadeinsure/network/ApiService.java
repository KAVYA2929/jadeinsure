package com.example.jadeinsure.network;

import com.example.jadeinsure.model.ClaimRequest;
import com.example.jadeinsure.model.Policy;
import com.example.jadeinsure.model.User;
import com.example.jadeinsure.model.Claim;
import com.example.jadeinsure.model.Service;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Call<AuthResponse> signup(@Body SignupRequest signupRequest);

    @GET("services")
    Call<List<Service>> getServices();

    @GET("services/{id}")
    Call<Service> getServiceDetails(@Path("id") String serviceId);

    @GET("policies")
    Call<List<Policy>> getUserPolicies();

    @GET("claims")
    Call<List<Claim>> getUserClaims();

    @POST("claims")
    Call<Claim> submitClaim(@Body ClaimRequest claimRequest);

    @GET("user/profile")
    Call<User> getUserProfile();

    @PUT("user/profile")
    Call<User> updateUserProfile(@Body User user);
} 