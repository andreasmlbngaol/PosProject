import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import features.auth.views.AuthScreen
import features.cashier.views.CashierScreen
import features.home.views.HomeScreen
import kotlinx.serialization.Serializable
import di.initKoin
import org.koin.compose.KoinContext
import ui.theme.PosProjectTheme

@Serializable data object HomeRoute
@Serializable data object AuthRoute
@Serializable data object CashierRoute

fun main() {
    initKoin()

    application {
        val windowState = rememberWindowState(placement = WindowPlacement.Fullscreen)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            undecorated = true,
            resizable = false
        ) {
            App(::exitApplication)
        }
    }
}

@Composable
fun App(
    onExitApplication: () -> Unit
) {
    val navController = rememberNavController()
    PosProjectTheme {
        KoinContext {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                MainNavHost(navController, onExitApplication)
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    onExitApplication: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute
    ) {
        composable<AuthRoute> {
            AuthScreen(
                onExitApplication = onExitApplication,
                onNavigateToHome = {
                    navController.navigate(HomeRoute) {
                        launchSingleTop = true
                        popUpTo(AuthRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onNavigateToAuth = {
                    navController.navigate(AuthRoute) {
                        launchSingleTop = true
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                },
                onNavigateToCashier = { navController.navigate(CashierRoute) },
                onExitApplication = onExitApplication
            )
        }

        composable<CashierRoute> {
            CashierScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}