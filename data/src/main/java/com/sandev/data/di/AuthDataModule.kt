package com.sandev.data.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.sandev.data.AiService.AiServiceRepoImpl
import com.sandev.data.auth.AuthDataSource
import com.sandev.data.auth.AuthRepositoryImpl
import com.sandev.domain.AiService.AiServiceRepository
import com.sandev.domain.auth.AuthRepository
//import com.sandev.data.auth.AuthRepositoryImpl
//import com.sandev.domain.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule{
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository {
        return authRepositoryImpl
    }

    @Provides
    fun provideOneTapClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    fun provideAuthDataSource(
        oneTapClient: SignInClient
    ): AuthDataSource {
        return AuthDataSource(oneTapClient)
    }

    @Provides
    @Singleton
    fun providesAiServiceRepo(aiServiceRepoImpl: AiServiceRepoImpl): AiServiceRepository{
        return aiServiceRepoImpl
    }
}