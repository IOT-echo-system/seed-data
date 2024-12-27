package com.robotutor.seedData.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

const val BOARD_COLLECTION = "boards"

@TypeAlias("Board")
@Document(BOARD_COLLECTION)
data class Board(
    @Id
    val id: String? = null,
    val name: String,
    val version: String,
    val boardName: String,
    var pins: List<Pin> = emptyList()
) {
    fun updatePins(pins: List<Pin>): Board {
        this.pins = pins
        return this
    }

    companion object {
        fun fromMap(map: Map<String, String>): Board {
            return Board(
                name = map.getOrDefault("name", ""),
                version = map.getOrDefault("version", ""),
                boardName = map.getOrDefault("boardName", ""),
            )
        }
    }
}

data class Pin(
    val pinNumber: Int,
    val name: String,
    val type: List<PinType>,
    val description: String
)

enum class PinType {
    RESERVED,
    I2C,
    UART,
    SPI,
    ADC,
    DAC,
    PWM,
    INPUT_ONLY,
    INPUT_OUTPUT,
    OUTPUT_ONLY
}
