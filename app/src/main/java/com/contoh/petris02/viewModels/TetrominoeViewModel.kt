package com.contoh.petris02.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.TetrominoeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TetrominoeViewModel @Inject constructor(
    private val _boardState: BoardState,
    private val _tetrominoeState: TetrominoeState,
) : ViewModel() {
    val moveLeft = ::_moveLeft
    val moveRight = ::_moveRight
    val rotate = ::_rotate
    val pullDown = ::_pullDown

    private fun _moveLeft() {
        Log.i(TetrominoeViewModel::class.simpleName, "Left")
    }

    private fun _moveRight() {
        Log.i(TetrominoeViewModel::class.simpleName, "Right")
    }

    private fun _rotate() {
        Log.i(TetrominoeViewModel::class.simpleName, "rotate")
    }

    private fun _pullDown() {
        Log.i(TetrominoeViewModel::class.simpleName, "pulldown")
    }
}