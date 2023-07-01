package com.contoh.petris02.services

import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.models.BlockType
import com.contoh.petris02.models.BoardState
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

    @Test
    fun isCollideWithTetrominoeBlockTest() {
        val tetrominoeBlocks = resetTetrominoe()
        val boardState = BoardState()
        val boardBlocks = boardState.blocks
        val totalBoardBlocsCount = boardState.xSize * boardState.ySize

        for (index in (totalBoardBlocsCount/2) until totalBoardBlocsCount) {
            boardBlocks[index].type = BlockType.NORMAL
        }

        // Tetrominoe are not collided with tetrominoe block, should be false
        assertEquals(false, isCollideWithTetrominoeBlock(tetrominoeBlocks, boardBlocks))

        val moveCommand = MoveCommand(Position(0, 12), tetrominoeBlocks)
        moveCommand.execute()

        // Tetrominoe are collided with tetrominoe block, should be true
        assertEquals(true, isCollideWithTetrominoeBlock(tetrominoeBlocks, boardBlocks))
    }
}