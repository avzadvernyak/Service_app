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

    fun delAllRecords() {
        GlobalScope.launch(context = Dispatchers.IO) { repairDao.deleteAll() }
    }
    fun deleteSelected( selected: Set<Long>) {
        Log.d("blablabla", "Rep"+selected.size.toString())
        GlobalScope.launch(context = Dispatchers.IO) {
            repairDao.deleteIdList(selected) }
    }

    fun add(repair: Repair) {
        GlobalScope.launch(context = Dispatchers.IO) {
            repairDao.insert(repair)
        }
    }

    fun endRepair(repair: Repair) {
        GlobalScope.launch(context = Dispatchers.IO) {
            repairDao.update(repair)
        }
    }

    fun getStateRepair(serialNumber: String): LiveData<RepairState> {
        var result = MutableLiveData<RepairState>()
        GlobalScope.launch(context = Dispatchers.IO) {
            result.postValue(
                when (repairDao.getRepairState(serialNumber)) {
                    null -> RepairState.Success
                    //0L -> RepairState.Failure("In Work")
                    else -> RepairState.Failure("Close")
                }
            )
        }
        return result
    }


}