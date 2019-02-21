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

    @Query(
        """SELECT *
                FROM repair
                WHERE repair.serialNumber = :serNumber  AND (repair.endDate = 0 OR repair.issueDate = 0) limit 1"""
    )
    suspend fun getRepairState(serNumber : String ): Repair?
}