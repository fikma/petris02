package com.contoh.petris02.commands

import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeBlocks
import com.contoh.petris02.services.moveTetrominoe

class MoveCommand(
    private val direction: Position,
    private val tetrominoeBlocks: TetrominoeBlocks
) : BaseCommand {
    override fun execute() {
        moveTetrominoe(direction, tetrominoeBlocks)
    }

    override fun undo() {
        val newDirection = direction * -1
        moveTetrominoe(newDirection, tetrominoeBlocks)
    }
}