package fr.epf.min2.pjtmin2.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val capital: String?,
    val region: String,
    val population: Int,
    val area: Double,
    val flag: String,
    val favorite: Boolean = false
)

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    suspend fun getAll(): List<CountryEntity>

    @Insert
    suspend fun insertAll(vararg countries: CountryEntity)

    @Query("DELETE FROM country WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("DELETE FROM country")
    suspend fun clearAll()
}

fun CountryEntity.toCountry(): Country {
    return Country(
        name = this.name,
        capital = this.capital,
        region = this.region,
        population = this.population,
        area = this.area,
        flag = this.flag,
        favorite = this.favorite
    )
}