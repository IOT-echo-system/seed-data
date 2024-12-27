package com.robotutor.seedData

import com.robotutor.seedData.models.Board
import com.robotutor.seedData.models.Location
import com.robotutor.seedData.models.PinWithBoardName
import com.robotutor.seedData.service.BoardService
import com.robotutor.seedData.service.CsvImporter
import com.robotutor.seedData.service.LocationService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import reactor.core.publisher.Mono
import kotlin.system.exitProcess

@SpringBootApplication
class SeedDataApplication(
    private val csvImporter: CsvImporter,
    private val locationService: LocationService,
    private val boardService: BoardService,
) : CommandLineRunner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplicationBuilder(SeedDataApplication::class.java).run(*args)
        }
    }

    override fun run(vararg args: String?) {
        val locations = csvImporter.import("/locations.csv") { Location.from(it) }
        val boards = csvImporter.import("/boards.csv") { Board.fromMap(it) }
        val pinsGroup = csvImporter.import("/boardPins.csv") { PinWithBoardName.fromMap(it) }
            .groupBy { it.boardName.plus("__").plus(it.version) }

        boards.forEach { board ->
            val pins = pinsGroup.values.find {
                val pin = it.first()
                pin.boardName == board.boardName && pin.version == board.version
            } ?: emptyList()
            board.updatePins(pins.map { it.pin })
        }

        Mono.`when`(
            boardService.saveBoards(boards),
            locationService.saveLocations(locations)
        )
            .block()

        println("Closing application")
        exitProcess(0)
    }
}

