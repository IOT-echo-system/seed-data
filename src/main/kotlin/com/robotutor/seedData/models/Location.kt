package com.robotutor.seedData.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

const val LOCATION_COLLECTION = "locations"

@TypeAlias("Location")
@Document(LOCATION_COLLECTION)
data class Location(
    @Id
    val id: String? = null,
    val pincode: String,
    val city: String,
    val district: String,
    val state: String
)
