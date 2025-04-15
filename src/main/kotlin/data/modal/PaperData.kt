package org.example.data.modal

import net.dv8tion.jda.api.entities.User
import java.time.LocalDateTime

data class PaperData(val user: User, val target: User, val message: String) {
    private val createdAt: LocalDateTime = LocalDateTime.now()

    override fun toString(): String {
        return "PaperData(user=${user.id}, target=${target.id}, message='$message', createdAt='${createdAt}')"
    }
}
