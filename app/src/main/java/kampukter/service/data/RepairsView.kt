package kampukter.service.data

import androidx.room.ColumnInfo

data class RepairsView(
    @ColumnInfo(name = "id_Repair")
    val id: Long,
    @ColumnInfo(name = "serial_Number")
    val serialNumber: Long,
    val modelId: Long,
    @ColumnInfo(name = "model_Name")
    val modelName: String,
    val customerId: Long,
    @ColumnInfo(name = "customer_Name")
    val customerName: String,
    val beginDate: Long? = null,
    val endDate: Long? = null,
    val defect: String? = null,
    val notes: String? = null)