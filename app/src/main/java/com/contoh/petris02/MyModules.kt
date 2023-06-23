package com.contoh.petris02

import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyModules {
    @Provides
    @Singleton
    fun provideBoardState() : BoardState = BoardState()

    @Provides
    @Singleton
    fun provideGameState() : GameState  = GameState()
}