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
import dev.echirchir.binaria.common.Route
import dev.echirchir.binaria.common.TextIcon
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel
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

    val countryPrompt = "Select country"
    var selectedCountry by remember { mutableStateOf(countryPrompt) }
    var selectedCountryCode by remember { mutableStateOf(state.countryCode) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {}
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
                    text = "Send money",
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Complete the form below to send money to your friend or family",
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
                        viewModel.onAction(ExchangeRatesViewModel.Action.OnFirstNameChanged(it))
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

                val nativeCountryLabel = "Select country"

                BinariaDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    value = selectedCountry,
                    label = nativeCountryLabel
                ) {
                    keyboardController?.hide()
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                var phoneNumber by remember { mutableStateOf("")}

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
                            TextIcon(text = selectedCountryCode)
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
                            placeholder = {
                                Text(text = "XXXXXXX")
                            },
                            enabled = true,
                            value = phoneNumber,
                            onValueChange = {
                                phoneNumber = it
                                viewModel.onAction(ExchangeRatesViewModel.Action.OnPhonePrefixChanged(selectedCountryCode.subSequence(1, selectedCountryCode.length).toString()))
                                viewModel.onAction(ExchangeRatesViewModel.Action.OnPhoneNumberChanged(
                                    phoneNumber
                                ))},
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
                    amountInBinary = "01010101",
                    amount = "100",
                    onAmountChange = {},
                    currency = "Kes",
                    isAmountValid = true,
                    onDone = {
                        keyboardController?.hide()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                BinariaButton(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    label = "Send",
                    enabled = true //state.amount.toString().isNotEmpty() && state.isSendButtonActive
                ) {
                    navController.navigate(Route.Home.SendMoneySuccessScreen)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}