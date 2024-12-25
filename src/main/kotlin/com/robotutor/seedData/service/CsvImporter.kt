package com.robotutor.seedData.service

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.robotutor.seedData.utils.mapToDataClass
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class CsvImporter {

    fun <T : Any> import(filePath: String, clazz: KClass<T>): List<T> {
        val list = mutableListOf<T>()
        val inputStream = javaClass.getResourceAsStream(filePath)!!
        val listOfRecords = csvReader().readAllWithHeader(inputStream)
        listOfRecords.forEach {
            list.add(mapToDataClass(it, clazz))
        }
        return list
    }

}
