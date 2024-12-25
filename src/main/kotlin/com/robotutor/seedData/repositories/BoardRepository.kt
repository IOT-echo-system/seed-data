package com.robotutor.seedData.repositories

import com.robotutor.seedData.models.Board
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : ReactiveCrudRepository<Board, String> {
}
