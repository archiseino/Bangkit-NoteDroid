package com.example.dicodingsimplecleanarchitecture.data

import com.example.dicodingsimplecleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String) : MessageEntity
}

