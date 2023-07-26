package com.example.nycschool.ui.fragment

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.nycschool.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class MainActivityTest {
    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun clickForAddData() {

    }

    @After
    fun tearDown() {
    }
}