package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import kampukter.service.data.Repair
import kampukter.service.data.dao.RepairDao

class RepairRepository(private val repairDao: RepairDao) {

    fun getAll(): LiveData<List<Repair>> = repairDao.getAll()
    suspend fun delAllRecords() = repairDao.deleteAll()
    suspend fun add(repair: Repair) {
        repairDao.insert(repair)
    }
}