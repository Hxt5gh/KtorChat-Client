package com.hxt5gh.frontend.di

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.data.remote.message.MessageApi
import com.hxt5gh.frontend.data.remote.message.MessageServices
import com.hxt5gh.frontend.data.remote.message.MessageServicesImp
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailService
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailServiceImp
import com.hxt5gh.frontend.data.remote.userDetail.UserNameSearchServiceImp
import com.hxt5gh.frontend.domain.message.GetMessageRepository
import com.hxt5gh.frontend.domain.message.GetMessageRepositoryImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepository
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitObject() : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://${BuildConfig.KTOR_IP_ADDRESS_Two}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMessageApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

    val json = Json{
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideHttpClint() : HttpClient{
        return HttpClient(CIO){

            install(JsonFeature){
                serializer = KotlinxSerializer(json)
                acceptContentTypes = acceptContentTypes + ContentType.Any
            }//if server is sending json we dont need to do anything but if we sending string from the server then we need to enable  accept all type

            install(Logging){
                logger = object : Logger{
                    override fun log(message: String) {
                        Log.d("TAG", "LOGGER : ${message}")
                    }

                }
            }
            install(WebSockets)

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

        }
    }

    @Provides
    @Singleton
    fun provideMessageService(httpClient: HttpClient) : MessageServices {
        return MessageServicesImp(httpClient)
    }

    @Provides
    @Singleton
    fun provideSaveMessage(httpClient: HttpClient) : UserDetailServiceImp{
        return UserDetailServiceImp(httpClient)
    }

    @Provides
    @Singleton
    fun provideSaveUserRepo(userDetailService: UserDetailServiceImp): SaveUserRepositoryImp {
        return SaveUserRepositoryImp(userDetailService)
    }

    @Provides
    @Singleton
    fun provideChatSocketServiceImp(httpClient: HttpClient): ChatSocketServiceImp{
        return ChatSocketServiceImp(httpClient)
    }
    @Provides
    @Singleton
    fun provideUserNameSearchServiceImp(httpClient: HttpClient) : UserNameSearchServiceImp {
        return UserNameSearchServiceImp(httpClient)
    }

    @Provides
    @Singleton
    fun provideGetMessageRepository(messageServices: MessageServicesImp , userNameSearchServiceImp: UserNameSearchServiceImp) : GetMessageRepository{
        return GetMessageRepositoryImp(messageServices, userNameSearchServiceImp)
    }


}