package com.example.dicodingsimplecleanarchitecture.data

import com.example.dicodingsimplecleanarchitecture.domain.IMessageRepository
import com.example.dicodingsimplecleanarchitecture.domain.MessageEntity

class MessageRepository(private val messageDataSource: IMessageDataSource) : IMessageRepository {
    override fun getWelcomeMessage(name: String) = messageDataSource.getMessageFromSource(name)
}