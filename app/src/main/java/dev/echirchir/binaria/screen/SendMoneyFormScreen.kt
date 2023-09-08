package dev.echirchir.binaria.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.echirchir.binaria.R
import dev.echirchir.binaria.common.AmountTextField
import dev.echirchir.binaria.common.BinariaButton
import dev.echirchir.binaria.common.BinariaDropDown
import dev.echirchir.binaria.common.BinariaTextField
import dev.echirchir.binaria.common.CountriesBottomSheet
import dev.echirchir.binaria.common.Route
import dev.echirchir.binaria.common.TextIcon
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel
import dev.echirchir.binaria.viewmodel.utils.countriesMap
import dev.echirchir.binaria.viewmodel.utils.isBinary
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SendMoneyFormScreen(
    viewModel: ExchangeRatesViewModel,
    navController: NavController
) {
    val state = viewModel.exchangeRatesState
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Collapsed, density = density)
    )

    val countries = countriesMap.keys.toList()

    val countryPrompt = "Select country"
    var selectedCountry by remember { mutableStateOf(countryPrompt) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            CountriesBottomSheet(countries = countries) {
                selectedCountry = it
                viewModel.onAction(ExchangeRatesViewModel.Action.OnCountrySelected(it, countryPrompt))
            }
            LaunchedEffect(key1 = Unit) {
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(all = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0XFFF2F3F4))
                        .clickable {
                            navController.popBackStack()
                        },
                    contentAlignment = Alignment.Center

                ){
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "back arrow",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Send Transactions",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Complete the form below to send money to your friends or family members!",
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(24.dp))

                BinariaTextField(
                    value = state.firstName,
                    onChange = {
                        viewModel.onAction(ExchangeRatesViewModel.Action.OnFirstNameChanged(it))
                    },
                    hint = "Joe",
                    onDone = {},
                    label = "First Name",
                    fieldDescription = "",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    isValid = state.firstNameError == null,
                    errorMessage = "First name is required",
                    enabled = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                BinariaTextField(
                    value = state.lastName,
                    onChange = {
                        viewModel.onAction(ExchangeRatesViewModel.Action.OnLastNameChanged(it))
                    },
                    hint = "Munyao",
                    onDone = {},
                    label = "Last Name",
                    fieldDescription = "",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    isValid = state.lastNameError == null,
                    errorMessage = "Last name is required",
                    enabled = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                BinariaTextField(
                    value = state.country,
                    onChange = {},
                    hint = countryPrompt,
                    onDone = {},
                    label = "Select Country",
                    fieldDescription = "",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    isValid = state.firstNameError == null,
                    errorMessage = "Country name is required",
                    enabled = false,
                    modifier = Modifier.clickable {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Phone Number",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .height(56.dp)
                                .width(96.dp)
                                .padding(top = 8.dp)
                                .background(Color.Transparent)
                                .border(
                                    border = BorderStroke(
                                        ButtonDefaults.OutlinedBorderSize,
                                        MaterialTheme.colors.onSurface.copy(alpha = ButtonDefaults.OutlinedBorderOpacity)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .testTag("country code indicator"),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            TextIcon(text = state.prefix)
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier= Modifier
                                .height(56.dp)
                                .padding(top = 8.dp)
                                .testTag("phone number")
                            ,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                placeholderColor = Color(0.88f, 0.89f, 0.9f, 1.0f),
                                unfocusedBorderColor = Color(0.75f, 0.76f, 0.79f, 1.0f),
                                focusedBorderColor = Color(0.0f, 0.46f, 0.33f, 1.0f)
                            ),
                            placeholder = { Text(text = "XXXXXXX") },
                            enabled = state.country.isNotEmpty(),
                            isError = state.phone.length > state.maxPhoneLength,
                            value = state.phone,
                            onValueChange = {
                                if (it.length <= state.maxPhoneLength) {
                                    viewModel.onAction(ExchangeRatesViewModel.Action.OnPhoneNumberChanged(it))
                                }
                            },
                            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Phone),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AmountTextField(
                    amountInBinary = state.amountInBinary,
                    amount = state.amount,
                    onAmountChange = {
                        if (it.all { char -> char == '1' || char == '0' }) {
                            viewModel.onAction(ExchangeRatesViewModel.Action.OnAmountChanged(it))
                        }
                    },
                    baseCurrency = state.base.uppercase(),
                    isEnabled = state.country.isNotEmpty(),
                    isAmountValid = true,
                    onDone = {
                        keyboardController?.hide()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                BinariaButton(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    label = "Send",
                    enabled = state.isSendButtonActive
                ) {
                    navController.navigate(Route.Home.SendMoneySuccessScreen)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}