package com.aikonia.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.aikonia.app.common.Constants
import com.aikonia.app.common.Constants.TRANSITION_ANIMATION_DURATION
import com.aikonia.app.ui.assistants.AiAssistantsScreen
import com.aikonia.app.ui.chat.ChatScreen
import com.aikonia.app.ui.history.DeleteHistoryBottomSheet
import com.aikonia.app.ui.history.HistoryScreen
import com.aikonia.app.ui.language.LanguageScreen
import com.aikonia.app.ui.settings.LogoutBottomSheet
import com.aikonia.app.ui.settings.SettingsScreen
import com.aikonia.app.ui.splash.SplashScreen
import com.aikonia.app.ui.startchat.StartChatScreen
import com.aikonia.app.ui.upgrade.PurchaseHelper
import com.aikonia.app.ui.upgrade.UpgradeScreen
import com.aikonia.app.ui.welcome.WelcomeScreen
import com.aikonia.app.ui.startchat.StartChatViewModel
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.aikonia.app.data.source.local.UserRepository

@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    darkMode: MutableState<Boolean>,
    purchaseHelper: PurchaseHelper,
    userRepository: UserRepository // UserRepository-Instanz
) {

    val paddingBottom =
        animateDpAsState(
            if (bottomBarState.value) 56.dp else 0.dp,
            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
        )

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(bottom = paddingBottom.value)

    ) {
        bottomSheet(route = Screen.DeleteHistory.route) {
            DeleteHistoryBottomSheet(onConfirmClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.IS_DELETE, true)
                navController.popBackStack()
            }, onCancelClick = {
                navController.popBackStack()
            })
        }

        bottomSheet(route = Screen.Logout.route) {
            LogoutBottomSheet(onConfirmClick = {
                navController.navigate(Screen.Splash.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }, onCancelClick = {
                navController.popBackStack()
            })
        }

        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navigateToStartChat = {
                    // Navigiere zu StartChatScreen, wenn keine Benutzerdaten vorhanden sind
                    navController.navigate(Screen.Chat.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                navigateToWelcome = {
                    // Navigiere zu WelcomeScreen, wenn Benutzerdaten vorhanden sind
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // WelcomeScreen Composable
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeScreen(
                navigateToChat = {
                    navController.navigate(Screen.History.route) // oder eine andere Route, falls nötig
                },
                playClickSound = {
                    // Implementieren Sie hier den Klick-Sound
                }
            )
        }

        composable(
            route = Screen.StartChat.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }
        ) {
            // Holen des ApplicationContext
            val context = LocalContext.current

            // Holen der SharedPreferences-Instanz
            val sharedPreferences = context.getSharedPreferences("mova_shared_pref", Context.MODE_PRIVATE)

            StartChatScreen(
                navigateToMenu = {
                    // Implementieren Sie die Navigation zurück zum Hauptmenü
                    // Beispiel: navController.navigate(Screen.MainMenu.route) { ... }
                },
                navigateToChat = { name, role, examples ->
                    val examplesString = examples?.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString")
                },
                navigateToWelcome = {
                    // Direkte Navigation zum WelcomeScreen
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                sharedPreferences = sharedPreferences // Übergabe der SharedPreferences-Instanz
                // ... (weitere potenzielle Parameter und Funktionen für StartChatScreen)
            )
        }

        composable(route = "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}",
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.StartChat.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.AiAssistants.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.History.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.StartChat.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.AiAssistants.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.History.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.StartChat.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.AiAssistants.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.History.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.StartChat.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.AiAssistants.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.History.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            }) {

            var exampleList: List<String> = emptyList()

            val name = it.arguments?.getString("name") ?: ""
            val examplesString = it.arguments?.getString("examples")
            if (examplesString != null && examplesString != "null") {
                exampleList = examplesString.split("|").toList()
            }

            ChatScreen(
                navigateToBack = {
                    // Navigation zurück zum History-Screen
                    navController.navigate(Screen.History.route) {
                        popUpTo(Screen.Chat.route) {
                            inclusive = true
                        }
                    }
                },
                name = name,
                examples = exampleList
            )
        }

        composable(route = Screen.AiAssistants.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            }
        ) {
            AiAssistantsScreen(
                navigateToChat = { name, role, examples ->
                    val examplesString = examples.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString")
                }
            )
        }

        composable(route = Screen.History.route) {

            val screenResult = navController.currentBackStackEntry
                ?.savedStateHandle

            HistoryScreen(
                navigateToChat = { name, role, examples, id ->
                    val examplesString = examples?.joinToString(separator = "|")
                    navController.navigate("${Screen.Chat.route}?name=$name&role=$role&examples=$examplesString&id=$id")
                },
                navigateToDeleteConversations = {
                    navController.navigate(Screen.DeleteHistory.route)
                },
                savedStateHandle = screenResult
            )
        }

        composable(route = Screen.Settings.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Languages.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Languages.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.Languages.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            }) {
            SettingsScreen(
                darkMode = darkMode,
                navigateToLanguages = {
                    navController.navigate(Screen.Languages.route)
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                purchaseHelper = purchaseHelper
            )
        }

        composable(route = Screen.Languages.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route ->
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            }) {

            LanguageScreen(
                navigateToBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Upgrade.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.StartChat.route ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideInVertically(
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    Screen.StartChat.route ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    "${Screen.Chat.route}?name={name}&role={role}&examples={examples}&id={id}" ->
                        slideOutVertically(
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
                        )
                    else -> null
                }
            }) {

            UpgradeScreen(
                purchaseHelper,
                navigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
