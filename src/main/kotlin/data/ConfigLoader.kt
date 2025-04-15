package org.example.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.example.data.modal.Config
import java.io.File

object ConfigLoader {
    private val mapper = jacksonObjectMapper()

    fun load(path: String = "config.json"): Config {
        val file = File(path)
        if (!file.exists()) {
            error("❌ 설정 파일(config.json)을 찾을 수 없습니다: $path")
        }
        return mapper.readValue(file)
    }
}