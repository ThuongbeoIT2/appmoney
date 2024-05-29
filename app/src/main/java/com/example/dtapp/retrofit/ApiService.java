package com.example.dtapp.retrofit;

import com.example.dtapp.model.AuthenticationRequest;
import com.example.dtapp.model.AuthenticationResponse;
import com.example.dtapp.model.CateRespone;
import com.example.dtapp.model.ChangePasswordRequest;
import com.example.dtapp.model.ProfileResponse;
import com.example.dtapp.model.RegisterRequest;
import com.example.dtapp.model.SpendingLimitResponse;
import com.example.dtapp.model.TradeResponse;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST(value = "api/v1/auth/authenticate")
    Call<AuthenticationResponse> Login(@Body AuthenticationRequest authenticationRequest);
    @POST(value = "api/v1/auth/register/user")
    Call<AuthenticationResponse> Register(@Body RegisterRequest registerRequest);
    @GET(value = "category/getall")
    Call<List<CateRespone>> GetCate(@Header("Authorization") String token);
    @GET(value = "category/getallbyspend")
    Call<List<CateRespone>> GetCateBySpend(@Header("Authorization") String token);
    @GET(value = "category/infodetail/{{cateid}}")
    Call<Optional<CateRespone>> GetInfoDetail(@Header("Authorization") String token,
                                              @Path("cateid") int cateid);
    @GET(value = "account/getallspendinglimit")
    Call<List<SpendingLimitResponse>> getAllSpending(@Header("Authorization") String token);
    @GET(value = "account/infodetail/spendinglimit/{catename}")
    Call<Optional<SpendingLimitResponse>> getSpendinglimit(@Header("Authorization") String token,
                                                           @Path("catename") String catename);
    @FormUrlEncoded
    @POST(value = "account/spendinglimit/insert")
    Call<String> InsertSpendingLimit(@Header("Authorization") String token,
                                     @Field("spendingLimit") long spendingLimit,
                                     @Field("cateid") int cateid);
    @FormUrlEncoded
    @POST(value = "account/spendinglimit/insert")
    Call<String> UpdateSpendingLimit(@Header("Authorization") String token,
                                     @Field("spendingLimitValue") String spendingLimitValue,
                                     @Field("spenid") int spendid);
    @GET(value = "account/spendinglimit/delete/{spenid}")
    Call<String> DeleteSpend(@Header("Authorization") String token,
                             @Path("spenid") int spenid);
    @GET(value = "account/trade/getall")
    Call<List<TradeResponse>> GetAllTrade(@Header("Authorization") String token);

    @GET(value = "account/trade/gettop5")
    Call<List<TradeResponse>> GetAllTradeTop5(@Header("Authorization") String token);
    @FormUrlEncoded
    @POST(value = "account/trade/insert")
    Call<String> InsertTrade(@Header("Authorization") String token,
                             @Field("cost") long cost,
                             @Field("cateid") int cateid,
                             @Field("title") String title,
                             @Field("date") String date);
    @GET(value = "/account/trade/lognow")
    Call<List<TradeResponse>> GetTradeLogNow(@Header("Authorization") String token);
    @GET(value = "account/trade/logtrade")
    Call<List<TradeResponse>> GetTradeLog(@Header("Authorization") String token);
    @GET(value = "api/v1/users/profile")
    Call<ProfileResponse> GetProfile(@Header("Authorization") String token);
    @FormUrlEncoded
    @POST(value = "api/v1/users/forgetpassword")
    Call<String> ForgetPassWord(@Field("emailfg") String emailfg);
    @POST(value = "api/v1/users/changepassword")
    Call<String> changePassword(@Header("Authorization") String token,@Body ChangePasswordRequest changePasswordRequest);
}
