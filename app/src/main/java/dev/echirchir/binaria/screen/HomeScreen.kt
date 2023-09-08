package dev.echirchir.binaria.screen

import android.text.TextUtils.indexOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.echirchir.binaria.R
import dev.echirchir.binaria.common.BinariaButton
import dev.echirchir.binaria.common.Route
import dev.echirchir.binaria.ui.theme.gray50

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            modifier = Modifier.size(250.dp).padding(horizontal = 32.dp),
            painter = painterResource(id = R.drawable.money_sent_successfully),
            contentDescription = "signup well done",
        )
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Welcome to Binaria!",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(8.dp))

        val countries = listOf("Kenya", "Uganda", "Tanzania", "Nigeria")
        val description = "You can easily send money to your family residing in Kenya, Uganda, Tanzania, and Nigeria using our fantastic app!"

        val annotatedString = buildAnnotatedString {
            append(description)

            countries.forEach { country ->
                val start = description.indexOf(country)
                val end = start + country.length
                if(start >= 0) {
                    addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                }
            }
        }

        Text(
            text =annotatedString,
            style = MaterialTheme.typography.body1.copy(color = gray50),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        BinariaButton(
            label = "Let's Go",
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            navController.navigate(Route.Home.SendMoneyScreen)
        }
    }
}