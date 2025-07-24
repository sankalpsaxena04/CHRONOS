package com.sandev.domain.di

import com.sandev.domain.auth.AuthRepository
import com.sandev.domain.auth.GetCurrentUserUseCase
import com.sandev.domain.auth.GetSignInIntentUseCase
import com.sandev.domain.auth.SignInWithGoogleUseCase
import com.sandev.domain.auth.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }

    @Provides
    fun provideGetSignInIntentUseCase(authRepository: AuthRepository): GetSignInIntentUseCase {
        return GetSignInIntentUseCase(authRepository)
    }

    @Provides
    fun provideSignInWithGoogleUseCase(authRepository: AuthRepository): SignInWithGoogleUseCase {
        return SignInWithGoogleUseCase(authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }
}