package com.contoh.petris02.commands

import com.contoh.petris02.models.TetrominoeBlocks
import com.contoh.petris02.services.rotateTetrominoe

class RotateCommand(
    private val tetrominoeBlocks: TetrominoeBlocks,
    private val clockWise: Boolean
) : BaseCommand {
    override fun execute() {
        rotateTetrominoe(tetrominoeBlocks, clockWise)
    }

    override fun undo() {
        rotateTetrominoe(tetrominoeBlocks, !clockWise)
    }
}