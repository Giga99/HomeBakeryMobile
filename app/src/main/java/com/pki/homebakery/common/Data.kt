package com.pki.homebakery.common

import com.pki.homebakery.R
import com.pki.homebakery.features.dashboard.domain.Cake
import com.pki.homebakery.features.dashboard.domain.Comment
import com.pki.homebakery.features.notifications.domain.Notification
import com.pki.homebakery.features.profile.domain.ProfileInfo
import java.time.Instant
import java.time.temporal.ChronoUnit

var users = mutableListOf(
    ProfileInfo("Igor1234", "123", "Igor Stevanovic", "Ulica123", "+381621234567"),
    ProfileInfo("Korisnik1", "123", "Test Korisnik", "Ulica456", "+381621234567"),
)

val blackForestCake = Cake(
    id = "1",
    title = "Black Forest Cake",
    subtitle = "Black forest cake with cream",
    icon = R.drawable.ic_black_forest_cake,
    price = "50 €",
    description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
    ingredients = listOf(
        "2 cups all-purpose flour",
        "2 cups granulated sugar",
        "3/4 cup unsweetened cocoa powder",
        "2 teaspoons baking soda",
        "1 teaspoon baking powder",
        "1 teaspoon salt",
        "1 cup buttermilk",
        "1 cup strong black coffee (hot)",
        "1/2 cup vegetable oil",
        "2 large eggs",
        "2 teaspoons vanilla extract"
    ),
    comments = listOf(
        Comment(
            text = "Amazing!",
            username = "Jelena17",
            date = Instant.now().minus(3, ChronoUnit.HOURS)
        ),
        Comment(
            text = "Good quality!",
            username = "Korisnik1",
            date = Instant.now().minus(7, ChronoUnit.DAYS)
        ),
        Comment(
            text = "Good quality!",
            username = "Korisnik2",
            date = Instant.now().minus(15, ChronoUnit.DAYS)
        ),
    )
)

var cakes = listOf(
    blackForestCake,
    Cake(
        id = "5",
        title = "Low Gluten Cake",
        subtitle = "Low gluten cake for the birthday",
        icon = R.drawable.ic_low_gluten_cake,
        price = "70 €",
        description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
        ingredients = listOf(
            "2 cups all-purpose flour",
            "2 cups granulated sugar",
            "3/4 cup unsweetened cocoa powder",
            "2 teaspoons baking soda",
            "1 teaspoon baking powder",
            "1 teaspoon salt",
            "1 cup buttermilk",
            "1 cup strong black coffee (hot)",
            "1/2 cup vegetable oil",
            "2 large eggs",
            "2 teaspoons vanilla extract"
        ),
        comments = listOf(
            Comment(
                text = "Amazing!",
                username = "Jelena17",
                date = Instant.now().minus(3, ChronoUnit.HOURS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik1",
                date = Instant.now().minus(7, ChronoUnit.DAYS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik2",
                date = Instant.now().minus(15, ChronoUnit.DAYS)
            ),
        )
    ),
    Cake(
        id = "6",
        title = "Chocolate Chip Muffin",
        subtitle = "Tasty chocolate chip muffin",
        icon = R.drawable.ic_chocolate_chip_muffin,
        price = "50 €",
        description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
        ingredients = listOf(
            "2 cups all-purpose flour",
            "2 cups granulated sugar",
            "3/4 cup unsweetened cocoa powder",
            "2 teaspoons baking soda",
            "1 teaspoon baking powder",
            "1 teaspoon salt",
            "1 cup buttermilk",
            "1 cup strong black coffee (hot)",
            "1/2 cup vegetable oil",
            "2 large eggs",
            "2 teaspoons vanilla extract"
        ),
        comments = listOf(
            Comment(
                text = "Amazing!",
                username = "Jelena17",
                date = Instant.now().minus(3, ChronoUnit.HOURS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik1",
                date = Instant.now().minus(7, ChronoUnit.DAYS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik2",
                date = Instant.now().minus(15, ChronoUnit.DAYS)
            ),
        )
    ),
    Cake(
        id = "2",
        title = "Chocolate Birthday Cake",
        subtitle = "The best chocolate cake for the birthday",
        icon = R.drawable.ic_chocolate_birthday_cake,
        price = "85 €",
        description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
        ingredients = listOf(
            "2 cups all-purpose flour",
            "2 cups granulated sugar",
            "3/4 cup unsweetened cocoa powder",
            "2 teaspoons baking soda",
            "1 teaspoon baking powder",
            "1 teaspoon salt",
            "1 cup buttermilk",
            "1 cup strong black coffee (hot)",
            "1/2 cup vegetable oil",
            "2 large eggs",
            "2 teaspoons vanilla extract"
        ),
        comments = listOf(
            Comment(
                text = "Amazing!",
                username = "Jelena17",
                date = Instant.now().minus(3, ChronoUnit.HOURS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik1",
                date = Instant.now().minus(7, ChronoUnit.DAYS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik2",
                date = Instant.now().minus(15, ChronoUnit.DAYS)
            ),
        )
    ),
    Cake(
        id = "3",
        title = "Vanilla Birthday Cake",
        subtitle = "Vanilla cake for the birthday",
        icon = R.drawable.ic_vanilla_birthday_cake,
        price = "55 €",
        description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
        ingredients = listOf(
            "2 cups all-purpose flour",
            "2 cups granulated sugar",
            "3/4 cup unsweetened cocoa powder",
            "2 teaspoons baking soda",
            "1 teaspoon baking powder",
            "1 teaspoon salt",
            "1 cup buttermilk",
            "1 cup strong black coffee (hot)",
            "1/2 cup vegetable oil",
            "2 large eggs",
            "2 teaspoons vanilla extract"
        ),
        comments = listOf(
            Comment(
                text = "Amazing!",
                username = "Jelena17",
                date = Instant.now().minus(3, ChronoUnit.HOURS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik1",
                date = Instant.now().minus(7, ChronoUnit.DAYS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik2",
                date = Instant.now().minus(15, ChronoUnit.DAYS)
            ),
        )
    ),
    Cake(
        id = "4",
        title = "Chantilly Cake",
        subtitle = "Fluffy and sweat cake with berry cream filling",
        icon = R.drawable.ic_chantilly_cake,
        price = "50 €",
        description = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
        ingredients = listOf(
            "2 cups all-purpose flour",
            "2 cups granulated sugar",
            "3/4 cup unsweetened cocoa powder",
            "2 teaspoons baking soda",
            "1 teaspoon baking powder",
            "1 teaspoon salt",
            "1 cup buttermilk",
            "1 cup strong black coffee (hot)",
            "1/2 cup vegetable oil",
            "2 large eggs",
            "2 teaspoons vanilla extract"
        ),
        comments = listOf(
            Comment(
                text = "Amazing!",
                username = "Jelena17",
                date = Instant.now().minus(3, ChronoUnit.HOURS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik1",
                date = Instant.now().minus(7, ChronoUnit.DAYS)
            ),
            Comment(
                text = "Good quality!",
                username = "Korisnik2",
                date = Instant.now().minus(15, ChronoUnit.DAYS)
            ),
        )
    ),
)

val notifications = listOf(
    Notification(orderId = "12345", isOrderAccepted = true),
    Notification(orderId = "67890", isOrderAccepted = false),
    Notification(orderId = "14523", isOrderAccepted = false),
    Notification(orderId = "28943", isOrderAccepted = true),
)
