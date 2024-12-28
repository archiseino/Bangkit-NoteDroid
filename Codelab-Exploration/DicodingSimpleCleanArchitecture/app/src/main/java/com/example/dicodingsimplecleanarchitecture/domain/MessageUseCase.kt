package com.example.dicodingsimplecleanarchitecture.domain

interface MessageUseCase {
    fun getMessage(name : String) : MessageEntity
}