package com.robotutor.seedData.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

const val LOCATION_COLLECTION = "locations"

@TypeAlias("Location")
@Document(LOCATION_COLLECTION)
data class Location(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    val pincode: Int,
    val district: String,
    val state: String
) {
    companion object {
        fun from(map: Map<String, String>): Location {
            return Location(
                pincode = map.getOrDefault("pincode", "").toInt(),
                district = map.getOrDefault("district", ""),
                state = map.getOrDefault("state", ""),
            )
        }
    }
}
