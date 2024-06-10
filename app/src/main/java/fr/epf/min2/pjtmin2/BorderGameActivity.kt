package fr.epf.min2.pjtmin2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.epf.min2.pjtmin2.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BorderGameActivity : AppCompatActivity() {

    lateinit var countrySave: ApiCountry
    lateinit var country: ApiCountry
    var count = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.border_game)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // replace 'ic_back' with your actual back icon
        }


        if (!::countrySave.isInitialized) {
            GlobalScope.launch(Dispatchers.IO) {
                val startCountry = intent.getStringExtra("start_country")
                Log.d("start_country", startCountry.toString())
                countrySave = synchro(startCountry)
                withContext(Dispatchers.Main) {
                    // Use apiCountry here
                    val country_textview = findViewById<TextView>(R.id.country)
                    country_textview.text = countrySave.name
                }
            }
        }
        val country_textview = findViewById<TextView>(R.id.country)
        val playButton = findViewById<Button>(R.id.play_button)
        val editText = findViewById<EditText>(R.id.nextcountry_edittext)
        playButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                country = synchro(editText.text.toString())
                withContext(Dispatchers.Main) {
                    // Use apiCountry here
                    Log.d("country Borders", countrySave.borders.toString())
                    Log.d("country AlphaCode", country.alpha3Code)
                    if (countrySave.borders.contains(country.alpha3Code)) {
                        count++
                    } else {
                        val toastMessage = "You lose. Your score: $count"
                        Toast.makeText(this@BorderGameActivity, toastMessage, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@BorderGameActivity, BorderGameHomeActivity::class.java)
                        startActivity(intent)
                    }
                    countrySave = country
                    country_textview.text = countrySave.name
                    findViewById<TextView>(R.id.score).text = count.toString()
                }
            }
        }

    }


    suspend fun synchro(country: String?):ApiCountry {
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

        val service = retrofit.create(CountryService::class.java)
        val countries = if (country != null) {
            service.getCountry(country)
        } else {
            service.getCountry("")
        }

        return countries.firstOrNull() ?: throw Exception("Country not found")


    }
}