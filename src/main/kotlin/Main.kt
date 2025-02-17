import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import features.auth.views.AuthScreen
import features.cashier.views.CashierScreen
import features.home.views.HomeScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.ProvidePreComposeLocals
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.theme.PosProjectTheme

@Composable
fun App(
    onExitApplication: () -> Unit
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        PosProjectTheme {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navigator = navigator,
                    initialRoute = "/auth"
                ) {
                    scene("/auth") {
                        AuthScreen(
                            onExitApplication = onExitApplication,
                            onNavigateToHome = {
                                navigator.navigate(
                                    "/home",
                                    options = NavOptions(
                                        launchSingleTop = true,
                                        popUpTo = PopUpTo(
                                            route = "/auth",
                                            inclusive = true
                                        )
                                    )
                                )
                            }
                        )
                    }
                    scene("/home") {
                        HomeScreen(
                            onNavigateToAuth = {
                                navigator.navigate(
                                    "/auth",
                                    options = NavOptions(
                                        launchSingleTop = true,
                                        popUpTo = PopUpTo(
                                            route = "/auth",
                                            inclusive = true
                                        )
                                    )
                                )

                            },
                            onNavigateToCashier = { navigator.navigate("/cashier") },
                            onExitApplication = onExitApplication
                        )
                    }

                    scene("/cashier") {
                        CashierScreen(
                            onBack = { navigator.goBack() }
                        )
                    }
                }
            }
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(placement = WindowPlacement.Fullscreen)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        undecorated = true,
        resizable = false
    ) {
        ProvidePreComposeLocals {
            App(::exitApplication)
        }
    }
}
