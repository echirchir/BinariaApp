package dev.echirchir.binaria

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dev.echirchir.binaria.screen.BinariaProgressDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel


class MainActivity : ComponentActivity() {

    private val exchangeRatesViewModel by viewModel<ExchangeRatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(key1 = Unit) {
                exchangeRatesViewModel.onAction(ExchangeRatesViewModel.Action.OnLoad)
            }
            
            val state = exchangeRatesViewModel.exchangeRatesState
            val context = LocalContext.current

            if(state.isLoading) {
                BinariaProgressDialog()
            }

            if(state.error != null) {
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }

            Column {
                Label(title = state.kes.toString())
                Label(title = state.tzs.toString())
                Label(title = state.ngn.toString())
                Label(title = state.ugx.toString())
            }
        }
    }
}

@Composable
fun Label(title: String) {
    Text(text = title)
}