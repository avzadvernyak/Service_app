package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.Repair

@Dao
interface RepairDao : BasicDao<Repair> {

    @Query("select * from repair")
    fun getAll(): LiveData<List<Repair>>

    @Query("delete from repair")
    suspend fun deleteAll()
}