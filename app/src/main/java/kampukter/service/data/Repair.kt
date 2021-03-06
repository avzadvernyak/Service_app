package kampukter.service.data

import androidx.room.*
import java.util.*

@Entity(
    tableName = "repair",
    indices = [(Index(value = ["model_Id"], name = "idx_repair_model_Id")),
               (Index(value = ["customer_Id"], name = "idx_repair_customer_Id"))],
    foreignKeys = [ForeignKey(
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
    val beginDate: Date,
    val endDate: Date? = null,
    val issueDate: Date? = null,
    val defect: String? = null,
    val notes: String? = null
)