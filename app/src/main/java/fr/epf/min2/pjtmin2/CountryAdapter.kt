package fr.epf.min2.pjtmin2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import fr.epf.min2.pjtmin2.model.Country
import fr.epf.min2.pjtmin2.GlideApp
import fr.epf.min2.pjtmin2.model.AppDatabase
import fr.epf.min2.pjtmin2.model.CountryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val COUNTRY_ID_EXTRA = "countryId"

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

class CountryAdapter(var countries: List<Country>) : RecyclerView.Adapter<CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val countryNameTextView = view.findViewById<TextView>(R.id.country_text_view)
        countryNameTextView.text = country.name

        val imageView = view.findViewById<ImageView>(R.id.country_view_image)


        GlideApp.with(view.context)
            .`as`(PictureDrawable::class.java)
            .load(country.flag)
            .into(SvgSoftwareLayerSetter(imageView))

        val cardView = view.findViewById<CardView>(R.id.country_card_view)
        cardView.click {
            with(it.context) {
                val intent = Intent(this, DetailsCountryActivity::class.java)
                intent.putExtra(COUNTRY_ID_EXTRA, country)
                startActivity(intent)
            }
        }

        val toggleButton = view.findViewById<ToggleButton>(R.id.favorite_button)
        toggleButton.setOnCheckedChangeListener(null)
        toggleButton.isChecked = country.favorite
        if (country.favorite) {
            toggleButton.background = ContextCompat.getDrawable(view.context, R.drawable.star_24)
        } else {
            toggleButton.background = ContextCompat.getDrawable(view.context, R.drawable.star_border_24)
        }
        //toggleButton.background = ContextCompat.getDrawable(view.context, R.drawable.star_border_24)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toggleButton.background =
                    ContextCompat.getDrawable(view.context, R.drawable.star_24)
                val countryEntity = CountryEntity(
                    0,
                    country.name,
                    country.capital,
                    country.region,
                    country.population,
                    country.area,
                    country.flag,
                    true
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val db = Room.databaseBuilder(
                        view.context,
                        AppDatabase::class.java, "country_database2"
                    ).build()
                    db.countryDao().insertAll(countryEntity)
                    country.favorite = true
                    //db.clearAllTables()
                }
            } else {
                toggleButton.background =
                    ContextCompat.getDrawable(view.context, R.drawable.star_border_24)
                CoroutineScope(Dispatchers.IO).launch {
                    val db = Room.databaseBuilder(
                        view.context,
                        AppDatabase::class.java, "country_database2"
                    ).build()
                    db.countryDao().deleteByName(country.name)
                    country.favorite = false
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCountries: List<Country>) {
        this.countries = newCountries
        notifyDataSetChanged()
    }
}