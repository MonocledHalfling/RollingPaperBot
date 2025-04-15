package org.example.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.example.data.modal.Config
import java.io.File

object ConfigLoader {
    private val mapper = jacksonObjectMapper()

    fun load(path: String = "src/main/resources/config.json"): Config {
        return mapper.readValue(File(path))
    }
}