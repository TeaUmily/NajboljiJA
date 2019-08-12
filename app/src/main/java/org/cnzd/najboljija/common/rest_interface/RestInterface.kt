package org.cnzd.najboljija.common.rest_interface

import io.reactivex.Observable
import io.reactivex.Single
import org.cnzd.najboljija.ui.login.model.LoginRequest
import org.cnzd.najboljija.ui.login.model.LoginResponse
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionAnswersdata
import org.cnzd.najboljija.ui.login.model.NewTokenIdRequest
import org.cnzd.najboljija.ui.login.model.NewTokenIdResponse
import retrofit2.http.*

interface RestInterface {

    @POST("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword")
    fun logIn(@Query("key") webApiKey: String, @Body loginRequest: LoginRequest): Observable<LoginResponse>

    @GET("/{localId}/mentorski_par.json")
    fun getNames(@Path("localId") localId: String, @Query("auth") authKey: String): Observable<Map<String, String>>

    @GET("/{localId}/stanje_izazova.json")
    fun getChallengesState(@Path("localId") localId: String, @Query("auth") authKey: String): Observable<Map<String, Boolean>>

    @GET("/{localId}/bolje_se_upoznajmo/mentor_odgovori.json")
    fun getMentorAnswers(@Path("localId") localId: String, @Query("auth") authKey: String): Observable<IntroductionAnswersdata>

    @GET("/{localId}/bolje_se_upoznajmo/dijete_odgovori.json")
    fun getChildAnswers(@Path("localId") localId: String, @Query("auth") authKey: String): Observable<IntroductionAnswersdata>

    @PUT("/{localId}/bolje_se_upoznajmo/mentor_odgovori.json")
    fun saveMentorData(@Path("localId") localId: String, @Query("auth") authKey: String, @Body data: IntroductionAnswersdata): Observable<Unit>

    @PUT("/{localId}/bolje_se_upoznajmo/dijete_odgovori.json")
    fun saveChildData(@Path("localId") localId: String, @Query("auth") authKey: String, @Body data: IntroductionAnswersdata): Observable<Unit>

    @PUT("/{localId}/stanje_izazova/{challenge}.json")
    fun setChallengeToCompleted(@Path("localId") localId: String, @Path("challenge") challenge: String, @Query("auth") authKey: String, @Body value: Boolean): Observable<Unit>

    @GET("/{localId}/stanje_izazova/{challenge}.json")
    fun getChallengeState(@Path("localId") localId: String, @Path("challenge") challenge: String, @Query("auth") authKey: String): Observable<Boolean>
}