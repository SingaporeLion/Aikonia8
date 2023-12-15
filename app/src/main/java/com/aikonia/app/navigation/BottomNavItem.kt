package com.aikonia.app.navigation

import com.aikonia.app.R


sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val icon_filled: Int,
    val route: String
) {
    object Chat : BottomNavItem(
        title = R.string.chat,
        icon = R.drawable.chat,
        icon_filled = R.drawable.chat_filled,
        route = Screen.Chat.route
    )

    //object AiAssistants : BottomNavItem(
   //     title = R.string.ai_assistants,
   //     icon = R.drawable.category,
   //     icon_filled = R.drawable.category_filled,
   //     route = Screen.AiAssistants.route
  //  )

    object History : BottomNavItem(
        title = R.string.history,
        icon = R.drawable.time_circle,
        icon_filled = R.drawable.time_circle_filled,
        route = Screen.History.route
    )

    object Settings : BottomNavItem(
        title = R.string.settings,
        icon = R.drawable.setting,
        icon_filled = R.drawable.setting_filled,
        route = Screen.Settings.route
    )
}
