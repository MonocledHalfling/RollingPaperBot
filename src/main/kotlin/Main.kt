package org.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("Main")

fun main() {
    val botToken = "TOKEN_HERE" // 이곳에 토큰을 작성하세요
    val bot = Bot(botToken)
    logger.info("${bot.jda!!.selfUser.name} (${bot.jda!!.selfUser.id}) 작동중")
}