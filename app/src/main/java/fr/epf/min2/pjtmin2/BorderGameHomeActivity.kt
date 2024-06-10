package fr.epf.min2.pjtmin2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BorderGameHomeActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.border_game_start)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back) // replace 'ic_back' with your actual back icon
        }

        val startButton = findViewById<Button>(R.id.start_button)
        val startCountry = findViewById<EditText>(R.id.start_country)
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText("\uD83C\uDF0D Bienvenue dans le jeu des pays limitrophes ! \n\nLe but est simple : saisissez le nom d'un pays pour commencer, puis trouvez un pays voisin. \n\nLe jeu continue avec le pays saisi, à condition qu'il soit limitrophe du précédent. Si le pays saisi n'est pas un voisin direct, c'est la fin du jeu. \n\nTestez vos connaissances géographiques et voyez combien de pays voisins vous pouvez enchaîner correctement ! \n\nEntrez le nom du premier pays ci-dessous pour commencer.\n Bonne chance ! \uD83C\uDFC1\"")

        startButton.setOnClickListener {
            val intent = Intent(this, BorderGameActivity::class.java)
            intent.putExtra("start_country", startCountry.text.toString())
            startActivity(intent)
        }

    }
}