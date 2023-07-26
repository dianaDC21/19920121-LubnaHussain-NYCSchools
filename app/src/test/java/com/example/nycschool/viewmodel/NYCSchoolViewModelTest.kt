package com.example.nycschool.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschool.model.NYCSchoolResponseItem
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.repository.NYCSchoolRepository
import com.example.nycschool.utils.ApiResult
import com.example.nycschool.utils.ApiStatus
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class NYCSchoolViewModelTest {
    var vm: NYCSchoolViewModel? = null

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: NYCSchoolRepository

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
    fun test_getNYCSchools() = runTest {
        val nycSchoolList = mutableListOf<NYCSchoolResponseItem>()
        nycSchoolList.add(NYCSchoolResponseItem("01M292", "ABC_SCHOOL","",
                "", "", "", "","", ""))
        nycSchoolList.add(NYCSchoolResponseItem("01M448", "XYZ_SCHOOL", "",
                "", "", "", "", "", ""))

        Mockito.`when`(repository.getNYCSchools()).thenReturn(ApiResult(ApiStatus.SUCCESS,
            nycSchoolList))

        vm?.getNYCSchools()
        testDispatcher.scheduler.advanceUntilIdle()
        vm?.schoolData?.observeForever {
            Assert.assertEquals("01M448",
                it.apiResponse?.get(1)?.dbn ?: ""
            )
            Assert.assertEquals("XYZ_SCHOOL",
                it.apiResponse?.get(1)?.school_name ?: ""
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getSATScore() = runTest {
        val satScore = SatScoreResponseItem("01M292",
            "200", "340",
        "334", "332",
        "ABC_SCHOOL")
        val satScoreList = mutableListOf(satScore)

        Mockito.`when`(repository.getSATScore("01M292")).thenReturn(ApiResult(ApiStatus.SUCCESS,
            satScoreList))

        vm?.getSATScore("01M292")
        testDispatcher.scheduler.advanceUntilIdle()

        vm?.satScoreData?.observeForever {
            Assert.assertEquals("01M292",
            it.apiResponse?.get(0)?.dbn ?: ""
            )
            Assert.assertEquals("334",
                it.apiResponse?.get(0)?.sat_math_avg_score ?: ""
            )
            Assert.assertEquals("ABC_SCHOOL",
                it.apiResponse?.get(0)?.school_name ?: ""
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}