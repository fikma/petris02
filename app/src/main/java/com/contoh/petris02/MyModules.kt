package com.contoh.petris02

import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeState
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
    fun provideBoardState() : BoardState = BoardState(
        boardSize = Position(12, 22)
    )

    @Provides
    @Singleton
    fun provideGameState() : GameState  = GameState()

    @Provides
    @Singleton
    fun provideTetrominoeState() : TetrominoeState = TetrominoeState()
}