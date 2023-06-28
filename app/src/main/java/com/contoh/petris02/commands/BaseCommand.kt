package com.contoh.petris02.commands

interface BaseCommand {
    fun execute()
    fun undo()
}