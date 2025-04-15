package org.example.listeners

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.example.data.PaperDataManager
import org.example.utils.EmbedCreator

class PaperCommandListener : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "롤링시작" -> handleRollingStart(event)
            "롤링종료" -> handleRollingEnd(event)
            else -> replyEphemeral(event, "알 수 없는 명령어입니다.")
        }
    }

    private fun handleRollingStart(event: SlashCommandInteractionEvent) {
        if (!checkHandler(event)) return
        if (PaperDataManager.isPaperStarted) {
            replyEphemeral(event, ":warning:  현재 진행 중인 롤링 페이퍼가 있습니다.")
            return
        }

        val textChannel = event.channel as TextChannel
        PaperDataManager.isPaperStarted = true
        PaperDataManager.clearPaperData()
        textChannel.sendMessageEmbeds(EmbedCreator.startPaperEmbed().build())
            .setActionRow(Button.primary("paper:write", "✍ 글 쓰기")).queue()

        replyEphemeral(event, ":white_check_mark: **롤링 페이퍼가 시작되었습니다!** 친구들에게 따뜻한 메시지를 남겨보세요 :writing_hand:")
    }

    private fun handleRollingEnd(event: SlashCommandInteractionEvent) {
        if (!checkHandler(event)) return
        if (!PaperDataManager.isPaperStarted) {
            replyEphemeral(event, ":warning:  현재 진행 중인 롤링 페이퍼가 없습니다. 먼저 `/롤링시작` 명령어로 시작 해주세요.")
        }

        val textChannel = event.channel as TextChannel
        PaperDataManager.isPaperStarted = false
        textChannel.sendMessageEmbeds(EmbedCreator.endPaperEmbed().build())
            .setActionRow(Button.secondary("paper:view", "\uD83D\uDCC4 확인하기")).queue()

        replyEphemeral(event, ":octagonal_sign: **롤링 페이퍼가 종료되었습니다.**\n:page_facing_up: 받은 메시지를 이제 확인할 수 있어요!")
    }

    private fun checkHandler(event: SlashCommandInteractionEvent): Boolean {
        if (!event.member!!.permissions.contains(Permission.ADMINISTRATOR)) {
            replyEphemeral(event, ":warning:  관리자 권한이 필요합니다.")
            return false
        }
        if (event.guild == null) {
            replyEphemeral(event, ":x: 이 명령어는 **서버 내에서만 사용**할 수 있습니다.")
            return false
        }

        return true
    }

    private fun replyEphemeral(event: SlashCommandInteractionEvent, message: String) {
        event.reply(message).setEphemeral(true).queue()
    }
}
