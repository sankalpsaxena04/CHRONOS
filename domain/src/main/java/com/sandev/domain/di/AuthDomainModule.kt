package com.sandev.domain.di

import com.sandev.domain.AiService.AiServiceRepository
import com.sandev.domain.AiService.AiServiceUseCase
import com.sandev.domain.auth.AuthRepository
import com.sandev.domain.auth.GetSignedInUserUseCase
import com.sandev.domain.auth.SignInUseCase
import com.sandev.domain.auth.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    fun provideGetSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    fun provideGetSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }

    @Provides
    fun provideGetSignedInUserUseCase(authRepository: AuthRepository): GetSignedInUserUseCase {
        return GetSignedInUserUseCase(authRepository)
    }

    @Provides
    fun providesAiServiceUseCase(aiServiceRepository: AiServiceRepository): AiServiceUseCase{
        return AiServiceUseCase(aiServiceRepository)
    }

}