package kampukter.service.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "models")
data class Models(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val title: String
)