package com.ayan.composeexamples

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val name: String, val image: ImageVector){
    object Home:BottomNavItem("Home",Icons.Default.Home)
    object Profile:BottomNavItem("Profile",Icons.Default.Person)
}


object Utils{
    val bottomNavItems= arrayListOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
}
