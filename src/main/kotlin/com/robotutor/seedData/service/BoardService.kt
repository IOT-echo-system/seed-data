package com.robotutor.seedData.service

import com.robotutor.seedData.models.Board
import com.robotutor.seedData.repositories.BoardRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    fun saveBoards(boards: List<Board>): Flux<Board> {
        return boardRepository.deleteAll()
            .thenMany(boardRepository.saveAll(boards))
    }
}

