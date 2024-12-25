package com.robotutor.seedData

import com.robotutor.seedData.models.Board
import com.robotutor.seedData.models.Location
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
        val locations = csvImporter.import("/locations.csv", Location::class)
        val boards = csvImporter.import("/boards.csv", Board::class)
        Mono.`when`(
            boardService.saveBoards(boards),
            locationService.saveLocations(locations)
        )
            .block()

        println("Closing application")
        exitProcess(0)
    }
}

