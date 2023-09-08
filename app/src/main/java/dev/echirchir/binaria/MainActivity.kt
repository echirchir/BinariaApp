package dev.echirchir.binaria

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.echirchir.binaria.common.Route
import dev.echirchir.binaria.screen.BinariaProgressDialog
import dev.echirchir.binaria.screen.HomeScreen
import dev.echirchir.binaria.screen.SendMoneyFormScreen
import dev.echirchir.binaria.screen.SendMoneySuccessScreen
import dev.echirchir.binaria.ui.theme.green100
import dev.echirchir.binaria.ui.theme.green70
import org.koin.androidx.viewmodel.ext.android.viewModel
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel


class MainActivity : ComponentActivity() {

    private val exchangeRatesViewModel by viewModel<ExchangeRatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            window.statusBarColor = green70.toArgb()

            LaunchedEffect(key1 = Unit) {
                exchangeRatesViewModel.onAction(ExchangeRatesViewModel.Action.OnFetchRates)
            }

            val state = exchangeRatesViewModel.exchangeRatesState

            if (state.isLoading) {
                BinariaProgressDialog()
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Route.Home.Home
                ) {
                    composable(Route.Home.Home) {
                        HomeScreen(navController = navController)
                    }

                    composable(Route.Home.SendMoneyScreen) {
                        SendMoneyFormScreen(
                            viewModel = exchangeRatesViewModel,
                            navController = navController
                        )
                    }

                    composable(Route.Home.SendMoneySuccessScreen) {
                        SendMoneySuccessScreen(
                            navController = navController,
                            viewModel = exchangeRatesViewModel
                        )
                    }
                }
            }
        }
    }
}