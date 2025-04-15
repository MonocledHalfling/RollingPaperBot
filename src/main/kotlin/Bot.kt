package org.example

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import org.example.listeners.ButtonInteractionListener
import org.example.listeners.ModalInteractionListener
import org.example.listeners.PaperCommandListener

class Bot(botToken: String) {
    var jda: JDA? = null
    init {
        jda = JDABuilder.createDefault(botToken)
            .setActivity(Activity.playing("봇 작동"))
            .setStatus(OnlineStatus.ONLINE)
            .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .setAutoReconnect(true)
            .build()
            .awaitReady()

        jda!!.addEventListener(PaperCommandListener())
        jda!!.addEventListener(ButtonInteractionListener())
        jda!!.addEventListener(ModalInteractionListener())

        val commands = jda!!.updateCommands()
        commands.addCommands(
            Commands.slash("롤링시작", "롤링 페이퍼를 시작합니다."),
            Commands.slash("롤링종료", "현재 진행중인 롤링 페이퍼를 종료후 발표합니다."),
        ).queue()
    }
}