package com.example.nycschool.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nycschool.api.NYCSchoolApi
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.repository.NYCSchoolRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class NYCSchoolViewModelTest {
    var vm: NYCSchoolViewModel? = null

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    lateinit var repository: NYCSchoolRepository

    @MockK
    lateinit var api: NYCSchoolApi

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = Mockito.mock(NYCSchoolRepository::class.java)
        vm = NYCSchoolViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getSATScore() = runTest{
        val satScore = SatScoreResponseItem("01M292",
            "200", "340",
        "334", "332",
        "ABC")
        val satScoreList = mutableListOf(satScore)
        //Mockito.`when`(repository.getSATScore("01M292")).thenReturn(satScoreList)
        coEvery { repository } returns NYCSchoolRepository(api)
        coEvery { repository.getSATScore("01M292") } returns satScoreList

        val ld: LiveData<List<SatScoreResponseItem>>? = vm?.getSATScore("01M292")
        ld?.observeForever{}
        Assert.assertEquals("01M292", ld?.value?.get(0)?.dbn ?: "")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}