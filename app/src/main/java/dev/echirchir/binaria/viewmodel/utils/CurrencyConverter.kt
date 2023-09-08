package dev.echirchir.binaria.viewmodel.utils


enum class COUNTRY(val currency: String) {
    KENYA("kes"),
    UGANDA("ugx"),
    TANZANIA("tzs"),
    NIGERIA("ngn");
}

data class CountryData(val currency: String, val phonePrefix: String, val phoneLength: Int)

val countriesMap = mapOf(
    "Kenya" to CountryData("kes", "+254", 9),
    "Uganda" to CountryData("ugx", "+256", 7),
    "Tanzania" to CountryData("tzs", "+255", 9),
    "Nigeria" to CountryData("ngn", "+234", 7)
)

fun getExchangeRateByCountry(countryName: String, exchangeRatesMap: Map<String, Double>): Double? {
    val countryData = countriesMap[countryName]
    return countryData?.currency?.let { exchangeRatesMap[it] }
}

fun getCurrencyByCountry(country: String): String? {
    return countriesMap[country]?.currency?.uppercase()
}

fun getPhoneLengthByCountry(country: String): Int? {
    return countriesMap[country]?.phoneLength
}

fun getPhonePrefixByCountry(countryName: String): String? {
    return countriesMap[countryName]?.phonePrefix
}