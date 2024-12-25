package com.robotutor.seedData.repositories

import com.robotutor.seedData.models.Location
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : ReactiveCrudRepository<Location, String> {
}
