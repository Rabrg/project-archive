package me.rabrg.guerrilla;

import me.rabrg.guerrilla.model.EmailAddress;
import me.rabrg.guerrilla.model.EmailUser;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

interface GuerrillaMailService {

    @GET("?f=get_email_address")
    Call<EmailAddress> getEmailAddress(@Query("lang") String languageCode, @Query("sid_token") String sessionId, @Query("domain") String domain);

    @PUT("?f=set_email_user")
    Call<EmailUser> setEmailUser(@Query("email_user") String emailUser, @Query("lang") String languageCode, @Query("sid_token") String sessionId, @Query("domain") String domain);
}
