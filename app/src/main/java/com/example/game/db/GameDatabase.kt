package com.example.game.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/**
 * Flat, single-row persistence record for the run. Collection-valued parts of
 * [GameState] are stored as JSON blobs; [GameRepository] owns the mapping.
 */
@Entity(tableName = "game_progress")
data class GameProgress(
    @PrimaryKey val id: Int = 1,
    val playerName: String = "Quantum Baby",
    val level: Int = 1,
    val xp: Int = 0,
    val credits: Int = 1200,
    val nanites: Int = 150,
    val health: Int = 520,
    val maxHealth: Int = 520,
    val mp: Int = 80,
    val maxMp: Int = 80,
    val currentWeapon: String = "QUANTUM_BLADE",
    val currentOutfit: String = "DEFAULT",
    val installedAugmentsJson: String,
    val installedModChipsJson: String,
    val factionReputationsJson: String,
    val companionsJson: String,
    val mapStateJson: String,
    val deployedStructuresJson: String = "[]",
    val inventoryJson: String = "[]",
    val questsJson: String = "[]"
)

@Dao
interface GameProgressDao {
    @Query("SELECT * FROM game_progress WHERE id = 1")
    suspend fun getProgress(): GameProgress?

    @Query("SELECT * FROM game_progress WHERE id = 1")
    fun getProgressFlow(): Flow<GameProgress?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: GameProgress)

    @Query("DELETE FROM game_progress")
    suspend fun clearProgress()
}

@Database(entities = [GameProgress::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameProgressDao(): GameProgressDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "quantum_effect_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
