package com.et.gestionreclamo.intefaces;

import com.et.gestionreclamo.entidad.Cierre;
import com.et.gestionreclamo.entidad.RecCierreMovil;
import com.et.gestionreclamo.entidad.ResAuth;
import com.et.gestionreclamo.entidad.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceConsumer {

    @Headers("content-type: application/json")
    @POST("authorization_service")
    Call<ResAuth> getUserLogin(@Body Usuario usuario);

    @FormUrlEncoded
    @POST("authorization_service")
    Call<ResAuth> getAuth(@Field("username") String username, @Field("password") String password, @Field("tokenPublic") String tokenPublic);

    @Headers("content-type: application/json")
    @POST("getchild")
    Call<List<Cierre>> getChild(@Header("Authorization")String authToken, @Query("id") Integer id);

    @Headers("content-type: application/json")
    @POST("getNodos")
    Call<List<Cierre>> getNodos(@Header("Authorization")String authToken, @Body RecCierreMovil recCierreMovil);
}
