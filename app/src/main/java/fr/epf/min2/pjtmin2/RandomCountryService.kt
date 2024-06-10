package fr.epf.min2.pjtmin2

import fr.epf.min2.pjtmin2.model.Country
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RandomCountryService {
    @GET("countries")
    suspend  fun getCountrys() : List<ApiCountry>
}

interface CountryService {
    @GET("countries/name/{countryName}")
    suspend  fun getCountry(@Path("countryName") countryName: String) : List<ApiCountry>
}

data class GetCountrysResult(val results: List<ApiCountry>)
data class GetCountryResult(val results: ApiCountry)
data class ApiCountry(
    val name: String,
    val capital: String,
    val region: String,
    val population: Int,
    val area: Double,
    val flags: Flags,
    val alpha3Code: String,
    val borders: List<String>
) {
    data class Flags(
        val svg: String
    )
}