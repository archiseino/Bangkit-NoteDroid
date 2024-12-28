package com.example.dicodingsimplecleanarchitecture.data

import com.example.dicodingsimplecleanarchitecture.domain.MessageEntity

class MessageDataSource : IMessageDataSource {
    override fun getMessageFromSource(name: String): MessageEntity {
        return MessageEntity("Hello $name, Welcome to Clean Architecture")
    }

}