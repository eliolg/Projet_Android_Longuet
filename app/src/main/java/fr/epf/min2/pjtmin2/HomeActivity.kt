package fr.epf.min2.pjtmin2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val homeButton = findViewById<Button>(R.id.home_strat_button)

        homeButton.setOnClickListener {
            val intent = Intent(this, ListCountryActivity::class.java)
            startActivity(intent)
        }

        val playButton = findViewById<Button>(R.id.home_play_button)

        playButton.setOnClickListener {
            val intent = Intent(this, BorderGameHomeActivity::class.java)
            startActivity(intent)
        }

    }
}