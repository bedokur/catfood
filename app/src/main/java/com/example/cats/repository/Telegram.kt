package com.example.cats.repository

import com.example.cats.BuildConfig
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Telegram @Inject constructor() {
    fun sendMessage(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val bot = bot {
                token = BuildConfig.TOKEN
                logLevel = LogLevel.Network.Body
            }
            bot.sendMessage(chatId = ChatId.fromId(BuildConfig.CHAT_ID.toLong()), text)
        }
    }
}
