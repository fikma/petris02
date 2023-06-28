package com.contoh.petris02.services

import com.contoh.petris02.models.Position
import org.junit.Assert.assertEquals
import org.junit.Test

class TetrominoeServiceTest {
    @Test
    fun isTetrominoeOutsideBoardTest() {
        var tetrominoeBlocks = resetTetrominoe()

        assertEquals(false, isTetrominoeOutsideBoard(tetrominoeBlocks))

        moveTetrominoe(Position(-2, 0), tetrominoeBlocks)
        assertEquals(true, isTetrominoeOutsideBoard(tetrominoeBlocks))

        moveTetrominoe(Position(2, 0), tetrominoeBlocks)
        moveTetrominoe(Position(11, 0), tetrominoeBlocks)
        assertEquals(true, isTetrominoeOutsideBoard(tetrominoeBlocks))

        moveTetrominoe(Position(-11, 0), tetrominoeBlocks)
        moveTetrominoe(Position(0, 30), tetrominoeBlocks)
        assertEquals(true, isTetrominoeOutsideBoard(tetrominoeBlocks))
    }
}