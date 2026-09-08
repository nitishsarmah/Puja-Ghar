package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.data.model.PujaService
import com.example.ui.components.PujaCard
import com.example.ui.theme.PujaGharTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun greeting_screenshot() {
        val samplePuja = PujaService(
            id = "satyanarayan_puja",
            title = "Shri Satyanarayan Katha & Puja",
            assameseTitle = "সত্যনাৰায়ণ পূজা",
            description = "Sacred Katha recitations, panchamrit, banana leaf archana and sankalpa.",
            price = 2100,
            originalPrice = 2800,
            category = "Prosperity & Auspicious",
            deity = "Lord Vishnu",
            durationMinutes = 90,
            samagriIncluded = true,
            pujariIncluded = true,
            includedItems = listOf("Pure Desi Ghee", "Hawan Samagri", "Panchamrit"),
            benefits = listOf("Family Peace", "Prosperity", "Removal of Obstacles"),
            rating = 4.9f,
            reviewCount = 124
        )

        composeTestRule.setContent {
            PujaGharTheme {
                PujaCard(
                    puja = samplePuja,
                    onBookClick = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
    }
}
