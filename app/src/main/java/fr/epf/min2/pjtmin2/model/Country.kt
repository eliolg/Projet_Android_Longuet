package fr.epf.min2.pjtmin2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val capital: String?,
    val region: String,
    val population: Int,
    val area: Double,
    val flag: String,
    var favorite: Boolean = false
):Parcelable{
    companion object {
        fun generate(size: Int = 20) =
            (1..size).map {
                Country("Country${it}",
                    "Capital${it}",
                    "Region${it}",
                    it * 1000000,
                    it * 1000.0,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_the_Taliban.svg/320px-Flag_of_the_Taliban.svg.png")
            }
    }
}
