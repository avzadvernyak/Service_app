package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.*

@Dao
interface RepairsViewDao {
    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.issueDate, repair.defect, repair.notes
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
                repair.issueDate, repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                WHERE repair.serialNumber LIKE :searchSerialNumber """
    )
    fun getRepairsBySerialNumber(searchSerialNumber: String): LiveData<List<RepairsView>>

    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.issueDate, repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                WHERE repair.id = :searchId """
    )
    fun getRepairsById(searchId: Long): LiveData<RepairsView>

    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.issueDate, repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id 
                inner join customer on customer.id = repair.customer_Id
                WHERE (repair.serialNumber LIKE :search OR  customer.title LIKE :search) AND
                 case :filter 
                    when "$FILTER_WITHOUT_DATE" then repair.issueDate is null AND repair.endDate is not null 
                    when "$FILTER_WITH_DATE" then repair.issueDate is not null
                    when "$FILTER_IN_WORK" then repair.issueDate is null AND repair.endDate is null
                    when "$FILTER_ALL" then 1=1
                 end
                ORDER BY repair.id DESC"""
    )
    fun getRepairsBySNandCustomer(search: String, filter: String): LiveData<List<RepairsView>>

    @Query(
        """SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number,
                models.title AS model_Name, customer.title AS customer_Name,
                repair.beginDate, repair.customer_Id AS customerId, repair.model_Id AS modelId, repair.endDate,
                repair.issueDate, repair.defect, repair.notes
                FROM models
                inner join repair on repair.model_Id=models.id
                inner join customer on customer.id = repair.customer_Id
                WHERE repair.id IN (:selected)  """
    )
    suspend fun getSelectedItems(selected: List<Long>): List<RepairsView>

}