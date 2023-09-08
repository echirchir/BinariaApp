package dev.echirchir.binaria.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.echirchir.binaria.R
import dev.echirchir.binaria.ui.theme.gray05
import dev.echirchir.binaria.ui.theme.gray100

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CountriesBottomSheet(countries: List<String>, onCountrySelected: (String)-> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (WindowInsets.isImeVisible) LocalConfiguration.current.screenHeightDp.dp else 400.dp)
            .testTag("bottomSheet"),
        shape = MaterialTheme.shapes.medium.copy(
            topEnd = CornerSize(32.dp),
            topStart = CornerSize(32.dp),
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 12.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                Modifier
                    .height(6.dp)
                    .fillMaxWidth(0.2f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(text = "Countries", style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold, color = gray100))
                var searchValue by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.height(16.dp))
                SearchBox(searchValue, { searchValue = it }, modifier = Modifier.fillMaxWidth())
                val filteredList = countries.filter {
                    it.contains(searchValue, ignoreCase = true)
                }
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .testTag("countries")
                ){
                    items(filteredList){ country ->
                        ItemCountry(country =  country){
                            onCountrySelected(country)
                            keyboardController?.hide()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        placeholder = {
            Text(
                text = "Search",
                textAlign = TextAlign.Center
            )
        },
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search a contact"
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = gray100,
            backgroundColor = gray05,
            focusedBorderColor = Color.Unspecified,
            unfocusedBorderColor = Color.Unspecified
        )
    )
}

@Composable
fun ItemCountry(country: String, onClick: ()-> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = country, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(16.dp))
    }
}