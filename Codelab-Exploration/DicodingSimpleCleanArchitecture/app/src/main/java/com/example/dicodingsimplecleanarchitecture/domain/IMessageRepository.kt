package com.example.dicodingsimplecleanarchitecture.domain

interface IMessageRepository {
    fun getWelcomeMessage(name: String) : MessageEntity
}

