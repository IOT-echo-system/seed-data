package com.robotutor.seedData.service

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.stereotype.Service

@Service
class CsvImporter {
    fun <T : Any> import(filePath: String, converter: ((t: Map<String, String>) -> T)): List<T> {
        val list = mutableListOf<T>()
        val inputStream = javaClass.getResourceAsStream(filePath)!!
        val listOfRecords = csvReader().readAllWithHeader(inputStream)
        listOfRecords.forEach {
            list.add(converter(it))
        }
        return list
    }
}
