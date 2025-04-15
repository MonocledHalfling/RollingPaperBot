package org.example

import org.example.data.ConfigLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("Main")

fun main() {
    val config = ConfigLoader.load()
    val bot = Bot(config.token)
    logger.info("${bot.jda!!.selfUser.name} (${bot.jda!!.selfUser.id}) 작동중")
}