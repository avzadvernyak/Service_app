package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.RepairsView

@Dao
interface RepairsViewDao {

    @Query(
        "SELECT repair.id AS id_Repair, repair.serialNumber AS serial_Number, "+
                "models.title AS model_Name, customer.title AS customer_Name " +
                "FROM repair "+
                "INNER JOIN models ON models.id = repair.model_Id "+
                "INNER JOIN customer ON customer.id = repair.customer_Id "
    )
    fun getAll(): LiveData<List<RepairsView>>

}