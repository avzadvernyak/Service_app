package kampukter.service.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "repair",foreignKeys = arrayOf(
    ForeignKey(
        entity = Models::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("model_Id")
    ),
    ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("customer_Id")
    )
))
data class Repair(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val serialNumber: Long,
    @ColumnInfo(name = "model_Id")
    val modelId: Long,
    @ColumnInfo(name = "customer_Id")
    val customerId: Long,
    val beginDate: Long,
    val endDate: Long,
    val defect: String,
    val notes: String)