package com.example.storyapp.utils

import com.example.storyapp.data.stories.ListStoryItem

object DataDummy {

    fun generateDataDummy() : List<ListStoryItem> {
        val items : MutableList<ListStoryItem> = arrayListOf()

        for (i in 1 .. 100) {
            val story = ListStoryItem(
                id = i.toString(),
                name = "Story $i",
                description = "Description $i",
                photoUrl = "https://picsum.photos/200/300?random=$i",
                createdAt = "2021-01-01T00:00:00Z",
                lat = (-7.78),
                lon = 114.04
            )
            items.add(story)
        }
        return items
    }
}