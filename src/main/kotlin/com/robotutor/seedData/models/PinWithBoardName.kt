package com.robotutor.seedData.models

data class PinWithBoardName(
    val boardName: String,
    val version: String,
    val pin: Pin
) {
    companion object {
        fun fromMap(map: Map<String, String>): PinWithBoardName {
            return PinWithBoardName(
                boardName = map.getOrDefault("boardName", ""),
                version = map.getOrDefault("version", ""),
                pin = Pin(
                    name = map.getOrDefault("name", ""),
                    pinNumber = map.getOrDefault("pinNumber", "").toInt(),
                    description = map.getOrDefault("description", ""),
                    type = map.getOrDefault("pinType", "RESERVED").split("|").map { PinType.valueOf(it.trim()) }
                )
            )
        }
    }
}



