package kampukter.service.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "repair",foreignKeys = [ForeignKey(
    entity = Models::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("model_Id")
), ForeignKey(
    entity = Customer::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("customer_Id")
)]
)
data class Repair(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val serialNumber: String,
    @ColumnInfo(name = "model_Id")
    val modelId: Long,
    @ColumnInfo(name = "customer_Id")
    val customerId: Long,
    val beginDate: Long= 0L,
    val endDate: Long= 0L,
    val defect: String? = null,
    val notes: String?= null)