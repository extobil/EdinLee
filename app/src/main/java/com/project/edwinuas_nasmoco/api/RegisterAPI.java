package com.project.edwinuas_nasmoco.api;



import com.project.edwinuas_nasmoco.api.model.Mobil;
import com.project.edwinuas_nasmoco.api.model.MobilResponse;

import com.project.edwinuas_nasmoco.api.model.ResponseOrderHistory;
import com.project.edwinuas_nasmoco.api.ui.product.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("post_register.php")
    Call<ResponseBody> register(@Field("email") String email,
                                @Field("nama") String nama,
                                @Field("password") String password);
    @FormUrlEncoded
    @POST("get_login.php")
    Call<ResponseBody> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("get_profile.php")
    Call<ResponseBody> getProfile(
            @Query("email") String email
    );
    @FormUrlEncoded
    @POST("post_profile.php")
    Call<ResponseBody> updateProfile(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("kota") String kota,
            @Field("provinsi") String provinsi,
            @Field("telp") String telp,
            @Field("kodepos") String kodepos,
            @Field("email") String email
    );
    @GET("get_product.php") // Sesuaikan dengan URL API Anda
    Call<List<Product>> getProducts();

    @FormUrlEncoded
    @POST("update_view.php")
    Call<ResponseBody> updateProductView(
            @Field("kode") String kode,
            @Field("view") int viewCount
    );

    @Multipart
    @POST("uploadimages.php")
    Call<ResponseBody> uploadFotoProfil(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part imageupload
    );

    @GET("getOrderHistory.php")
    Call<ResponseOrderHistory> getOrderHistory(
            @Query("id") int idPelanggan
    );


    @Multipart
    @POST("upload_bukti_bayar.php")
    Call<ResponseBody> uploadBuktiBayar(
            @Part("trans_id") RequestBody transId,
            @Part MultipartBody.Part bukti_bayar
    );


    @FormUrlEncoded
    @POST("produk_by_kategori.php")

    Call<MobilResponse> getMobilByKategori(@Query("kategori") String kategori);

}



