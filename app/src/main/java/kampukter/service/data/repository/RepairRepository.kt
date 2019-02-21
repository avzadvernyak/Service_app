package kampukter.service.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kampukter.service.data.Repair
import kampukter.service.data.RepairState
import kampukter.service.data.dao.RepairDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepairRepository(private val repairDao: RepairDao) {

    fun getAll(): LiveData<List<Repair>> = repairDao.getAll()

    suspend fun delAllRecords() = repairDao.deleteAll()
    suspend fun add(repair: Repair) {
        repairDao.insert(repair)
    }
    suspend fun endRepair(repair:Repair){repairDao.update(repair)}

    fun getStateRepair(serialNumber: String): LiveData<RepairState> {
        var result = MutableLiveData<RepairState>()
        GlobalScope.launch(context = Dispatchers.IO) {
            result.postValue(
                when (repairDao.getRepairState(serialNumber)?.issueDate) {
                    null ->  RepairState.Success
                    0L -> RepairState.Failure("In Work")
                    else -> RepairState.Failure("Close")
                }
            )
        }
        return result
    }


}