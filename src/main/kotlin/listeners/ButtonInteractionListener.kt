package org.example.listeners

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal
import org.example.data.PaperDataManager

class ButtonInteractionListener : ListenerAdapter() {
    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        when (event.componentId) {
            "paper:write" -> writePaper(event)
            "paper:view" -> showPaper(event)
        }
    }

    private fun writePaper(event: ButtonInteractionEvent) {
        if (!PaperDataManager.isPaperStarted) {
            event.reply(":x: 현재 진행 중인 롤링 페이퍼가 없습니다.\n먼저 `/롤링시작` 명령어로 시작해 주세요.").setEphemeral(true).queue()
            return
        }

        val modal = Modal.create("paper:write_modal", "롤링 페이퍼 작성").addActionRow(
                TextInput.create("target", "대상자 디스코드 닉네임 또는 ID", TextInputStyle.SHORT).setRequired(true)
                    .setPlaceholder("예시: Barcode").build()
            ).addActionRow(
                TextInput.create("message", "메세지", TextInputStyle.PARAGRAPH).setRequired(true)
                    .setPlaceholder("예시: 너는 정말 너무 착해!").setMinLength(1).setMaxLength(2000).build()
            ).build()

        event.replyModal(modal).queue()
    }

    private fun showPaper(event: ButtonInteractionEvent) {
        if (PaperDataManager.isPaperStarted) {
            event.reply(":x: 아직 롤페이퍼가 진행중입니다. \n종료 후 확인할 수 있습니다.").setEphemeral(true).queue()
            return
        }
        val guild = event.guild
        if (guild == null) {
            event.reply(":x: 서버에서만 사용할 수 있습니다.").setEphemeral(true).queue()
            return
        }

        val target = event.member ?: run {
            event.reply(":x: 오류가 발생했습니다. (사용자를 가져올 수 없음)").setEphemeral(true).queue()
            return
        }

        val papers = PaperDataManager.getPaperDataByTarget(target.user.id)
        if (papers.isEmpty()) {
            event.reply(":newspaper: 받은 롤링 페이퍼가 없습니다.").setEphemeral(true).queue()
            return
        }

        val chunks = mutableListOf<String>()
        val current = StringBuilder(":love_letter: **${target.asMention}** 님에게 도착한 따뜻한 메시지들입니다.\n\n")
        var count = 1
        for (data in papers) {
            val entry = "**${count}**: ${data.message}\n----------\n"
            if (current.length + entry.length > 2000) {
                chunks.add(current.toString())
                current.clear()
            }
            if (current.isEmpty()) current.append(":love_letter: **${target.asMention}** 님에게 도착한 따뜻한 메시지들입니다.\n\n")
            current.append(entry)
            count++
        }
        chunks.add(current.toString())


        event.reply(chunks[0]).setEphemeral(true).queue { hook ->
            for (i in 1 until chunks.size) {
                hook.sendMessage(chunks[i]).setEphemeral(true).queue()
            }
        }
    }
}