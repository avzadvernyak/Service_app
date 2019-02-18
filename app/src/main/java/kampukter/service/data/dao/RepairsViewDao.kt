package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.RepairsView

@Dao
interface RepairsViewDao {
    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                """
    )
    fun getAll(): LiveData<List<RepairsView>>

    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                WHERE repair.serialNumber = :searchSerialNumber"""
    )
    fun getRepairsBySerialNumber(searchSerialNumber: Long): LiveData<List<RepairsView>>

    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                WHERE repair.id IN (:selected) """
    )
    suspend fun getSelectedItems(selected : List<Long>): List<RepairsView>


}