package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kampukter.service.data.RepairState
import kampukter.service.data.RepairsView
import kampukter.service.data.dao.RepairsViewDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepairsViewRepository(private val repairsViewDao: RepairsViewDao) {

    fun getAll(): LiveData<List<RepairsView>> = repairsViewDao.getAll()
    fun getRepairSerNum(searchSerNumb: String): LiveData<List<RepairsView>> =
        repairsViewDao.getRepairsBySerialNumber(searchSerNumb)
    fun getRepairsById(searchId: Long): LiveData<RepairsView> =
        repairsViewDao.getRepairsById(searchId)
    fun getRepairsBySNandCustomer(searchSNandCustomer:String):LiveData<List<RepairsView>> =
        repairsViewDao.getRepairsBySNandCustomer(searchSNandCustomer)

    fun getSelectedItemsForSend(selected: List<Long>): LiveData<String> {
        var resultString: String = ""
        var result = MutableLiveData<String>()

        GlobalScope.launch(context = Dispatchers.IO) {
            repairsViewDao.getSelectedItems(selected).forEach {
                resultString = resultString + it.serialNumber + "," + it.modelName + "," + it.customerName +it.notes+ "\n"
            }
            result.postValue(resultString)
        }
        return result
    }
}