package org.example.listeners

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.data.PaperDataManager
import org.example.data.modal.PaperData
import org.example.logger

class ModalInteractionListener : ListenerAdapter() {
    override fun onModalInteraction(event: ModalInteractionEvent) {
        when (event.modalId) {
            "paper:write_modal" -> writeModal(event)
            else -> event.reply(":x: 알 수 없는 모달입니다.").setEphemeral(true).queue()
        }
    }

    private fun writeModal(event: ModalInteractionEvent) {
        val guild = event.guild
        if (guild == null) {
            event.reply(":x: 서버에서만 사용할 수 있습니다.").setEphemeral(true).queue()
            return
        }
        val targetName = event.getValue("target")?.asString ?: ""
        val message = event.getValue("message")?.asString ?: ""
        val user = event.user
        val target = guild.members.find { it.user.name == targetName || it.user.id == targetName || it.nickname == targetName }
            ?: run {
                event.reply(":x: 대상자를 찾을 수 없습니다.").setEphemeral(true).queue()
                return
            }

        val data = PaperData(user, target.user, message)
        logger.info("PaperData: $data")
        PaperDataManager.addPaperData(data)
        event.reply(":white_check_mark: ${target.asMention}의 롤링 페이퍼 작성 완료! :tada:").setEphemeral(true).queue()
    }
}