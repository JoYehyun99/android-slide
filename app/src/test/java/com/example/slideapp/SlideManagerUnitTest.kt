package com.example.slideapp

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class SlideManagerUnitTest {

    private lateinit var slideManager: SlideManager

    @Before
    fun setUp() {
        slideManager = SlideManager()
    }

    @Test
    fun addSlide_whenAddSlide_addedSlide() {
        val actual = slideManager.addSlide()
        assertEquals(slideManager.getSlide(0), actual)
    }

    @Test
    fun addSlide_checkDuplicateId_sameSizeWithSet() {
        val check = mutableSetOf<String>()
        for (i in 1..1000) {
            check.add(slideManager.addSlide().id)
        }
        assertEquals(1000, check.size)
    }

    @Test
    fun countTotalSlides_whenAdd5Slides_5() {
        for (i in 1..5) {
            slideManager.addSlide()
        }
        assertEquals(5, slideManager.countTotalSlides())
    }

    @Test
    fun changeBackgroundColor_notEqual() {
        val slide = slideManager.addSlide()
        val before = slide.color
        slideManager.changeBackgroundColor(0)
        assertNotEquals(before, slide.color)
    }

    @Test
    fun changeOpacity_2to9_true() {
        slideManager.addSlide()
        assertTrue(slideManager.changeOpacity(0, -1))
        assertTrue(slideManager.changeOpacity(0, -1))
        assertTrue(slideManager.changeOpacity(0, 1))
    }

    @Test
    fun changeOpacity_under1OrAbove10_false() {
        slideManager.addSlide()
        assertFalse(slideManager.changeOpacity(0, 1))
    }

}