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
    val type: String,
    val pins: List<String>
)
