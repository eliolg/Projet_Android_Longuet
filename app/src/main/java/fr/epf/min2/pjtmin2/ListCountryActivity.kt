package fr.epf.min2.pjtmin2


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.epf.min2.pjtmin2.model.AppDatabase
import fr.epf.min2.pjtmin2.model.Country
import fr.epf.min2.pjtmin2.model.CountryEntity
import fr.epf.min2.pjtmin2.model.toCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListCountryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var countries : List<Country> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_country)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // replace 'ic_back' with your actual back icon
        }

        recyclerView = findViewById<RecyclerView>(R.id.list_country_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val searchView = findViewById<SearchView>(R.id.search_country)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("ListCountryActivity", "onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("ListCountryActivity", "onQueryTextChange: $newText")
                Log.d("ListCountryActivity", "List of countries before change: $countries")
                setFilterCountries(filterCountries(countries, newText))
                Log.d("ListCountryActivity", "List of countries : $countries")
                return true
            }
        })

        synchro()

        if (!isOnline(applicationContext)) {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "country_database2"
            ).build()

            GlobalScope.launch(Dispatchers.IO) {
                val countryEntities = db.countryDao().getAll()
                val offlineCountries = countryEntities.map { it.toCountry() }
                // Now you can use the list of Country objects
                withContext(Dispatchers.Main) {
                    setFilterCountries(offlineCountries)
                }
            }
        } else {
            setFilterCountries(countries)
        }

        val toggleButton = findViewById<ToggleButton>(R.id.offline_toggle)
        toggleButton.isChecked = false
        toggleButton.background = ContextCompat.getDrawable(this, R.drawable.baseline_signal_wifi_4_bar_24)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toggleButton.background = ContextCompat.getDrawable(this, R.drawable.baseline_signal_wifi_off_24)
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "country_database2"
                ).build()

                GlobalScope.launch(Dispatchers.IO) {
                    val countryEntities = db.countryDao().getAll()
                    val offlineCountries = countryEntities.map { it.toCountry() }
                    // Now you can use the list of Country objects
                    withContext(Dispatchers.Main) {
                        setFilterCountries(offlineCountries)
                    }
                }
            } else {
                toggleButton.background = ContextCompat.getDrawable(this, R.drawable.baseline_signal_wifi_4_bar_24)
                setFilterCountries(countries)
            }
        }

/*        val countries = Country.generate(20)
        recyclerView.adapter = CountryAdapter(countries)*/

        Log.d("ListCountryActivity", "Number fo items in adapter : ${recyclerView.adapter?.itemCount}")
    }


    public fun setFilterCountries(filteredCountries: List<Country>){
        (recyclerView.adapter as CountryAdapter).updateData(filteredCountries)
    }

    private fun synchro() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.apicountries.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(RandomCountryService::class.java)

        runBlocking {
            val apiCountries = service.getCountrys()

            countries = apiCountries.map {
                Country(it.name, it.capital, it.region, it.population, it.area,it.flags.svg)
            }

            countries.forEach { country ->
                Log.d("ListCountryActivity", "Country: $country")
            }

            val adapter = CountryAdapter(countries)

            recyclerView.adapter = adapter


        }



    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}