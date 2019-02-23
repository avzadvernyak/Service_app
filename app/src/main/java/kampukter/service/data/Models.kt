package kampukter.service.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "models",  indices = [(Index(value = ["id"], name = "idx_model_id"))])
data class Models(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val title: String
)