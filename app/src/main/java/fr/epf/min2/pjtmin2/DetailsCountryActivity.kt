package fr.epf.min2.pjtmin2

import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.epf.min2.pjtmin2.model.Country

class DetailsCountryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_details)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // replace 'ic_back' with your actual back icon
        }

        val country_textview = findViewById<TextView>(R.id.details_country_name)
        val area = findViewById<TextView>(R.id.details_area)
        val population = findViewById<TextView>(R.id.details_population)
        val region = findViewById<TextView>(R.id.details_region)
        val capital = findViewById<TextView>(R.id.details_capital)
        val imageView = findViewById<ImageView>(R.id.imageCountry)

        intent.extras?.apply {
            val country = getParcelable<Country>(COUNTRY_ID_EXTRA)
            country_textview.text = country?.name
            area.text = country?.area.toString()
            population.text = country?.population.toString()
            region.text = country?.region
            capital.text = country?.capital ?: "No capital"
            if (country != null) {
                GlideApp.with(this@DetailsCountryActivity)
                    .`as`(PictureDrawable::class.java)
                    .load(country.flag)
                    .into(SvgSoftwareLayerSetter(imageView))
            }

        }


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