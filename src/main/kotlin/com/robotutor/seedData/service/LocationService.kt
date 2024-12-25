package com.robotutor.seedData.service

import com.robotutor.seedData.models.Location
import com.robotutor.seedData.repositories.LocationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class LocationService(private val locationRepository: LocationRepository) {
    fun saveLocations(locations: List<Location>): Flux<Location> {
        return locationRepository.deleteAll()
            .thenMany(locationRepository.saveAll(locations))
    }
}

