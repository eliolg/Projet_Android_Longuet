package fr.epf.min2.pjtmin2

import android.util.Log
import android.view.View
import fr.epf.min2.pjtmin2.model.Country

fun View.click(action: (View) -> Unit){
    Log.d("CLICK","click !")
    this.setOnClickListener(action)
}

fun filterCountries(countries: List<Country>, filter: String?): List<Country> {
    if (filter.isNullOrBlank()) return countries
    return countries.filter {
        it.name.contains(filter, ignoreCase = true) || it.capital?.contains(filter, ignoreCase = true) ?: false
    }
}