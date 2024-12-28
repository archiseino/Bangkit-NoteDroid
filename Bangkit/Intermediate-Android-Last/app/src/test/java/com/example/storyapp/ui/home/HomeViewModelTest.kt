package com.example.storyapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.data.stories.ListStoryItem
import com.example.storyapp.data.stories.StoryRepository
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.DataStoreManager
import com.example.storyapp.utils.MainDispatcherRule
import com.example.storyapp.utils.StoryPagingSource
import com.example.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository : StoryRepository
    @Mock
    private lateinit var dataStore : DataStoreManager



    @Test
    fun `when getStories is success and return data`() = runTest {
        val dummyQuote = DataDummy.generateDataDummy()
        val data : PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyQuote)
        val expectedValue = MutableLiveData<PagingData<ListStoryItem>>()
        expectedValue.value = data
        `when`(repository.getStories()).thenReturn(expectedValue)

        val homeViewModel = HomeViewModel(repository, dataStore)

        homeViewModel.getAllStories()

        val actualStory : PagingData<ListStoryItem> = homeViewModel.storiesResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
        diffCallback = HomeAdapter.DIFF_CALLBACK,
        updateCallback = nooplistUpdateCallback,
        workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0], differ.snapshot()[0])
    }

    @Test
    fun `when getStory is empty and should return no data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedValue = MutableLiveData<PagingData<ListStoryItem>>()
        expectedValue.value = data

        Mockito.`when`(repository.getStories()).thenReturn(expectedValue)

        val homeViewModel = HomeViewModel(repository, dataStore)

        homeViewModel.getAllStories()

        val actualStory : PagingData<ListStoryItem> = homeViewModel.storiesResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = nooplistUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        Assert.assertEquals(0, differ.snapshot().size)

    }


    val nooplistUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}

        override fun onRemoved(position: Int, count: Int) {}

        override fun onMoved(fromPosition: Int, toPosition: Int) {}

        override fun onChanged(position: Int, count: Int, payload: Any?) {}

    }
}