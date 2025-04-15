package org.example.utils

import net.dv8tion.jda.api.EmbedBuilder

object EmbedCreator {
    fun startPaperEmbed(): EmbedBuilder {
        return EmbedBuilder()
            .setTitle("✅ 롤링 페이퍼")
            .setDescription("롤링 페이퍼가 진행중입니다!\n" +
                    "아래 버튼을 눌러서 롤링 페이퍼를 작성해보세요!")
            .setColor(0x00FF00)
    }

    fun endPaperEmbed(): EmbedBuilder {
        return EmbedBuilder()
            .setTitle("\uD83D\uDED1 롤링 페이퍼 종료")
            .setDescription("롤링 페이퍼가 종료되었습니다!\n" +
                    "아래 버튼을 눌러서 롤링 페이퍼를 확인해보세요!")
            .setColor(0xFF0000)
    }
}