package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.Customer

@Dao
interface CustomerDao : BasicDao<Customer> {

    @Query("select * from customer")
    fun getAll(): LiveData<List<Customer>>

    @Query("SELECT * FROM customer")
    suspend fun getAllCustomer(): List<Customer>

    @Query("select * from customer where title like :query")
    fun search(query: String): LiveData<List<Customer>>

    @Query("select * from customer where id = :modelId limit 1")
    fun getModelId(modelId: Long ): LiveData<Customer>

    @Query("delete from customer")
    suspend fun deleteAll()
}