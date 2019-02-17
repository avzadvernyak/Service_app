package kampukter.service.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val title: String
)