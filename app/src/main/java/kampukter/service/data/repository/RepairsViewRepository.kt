package kampukter.service.data.repository

import android.content.Context.MODE_PRIVATE
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kampukter.service.data.Models
import kampukter.service.data.RepairsFilter
import kampukter.service.data.RepairsView
import kampukter.service.data.dao.RepairsViewDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*


class RepairsViewRepository(private val repairsViewDao: RepairsViewDao) {

    fun getAll(): LiveData<List<RepairsView>> = repairsViewDao.getAll()
    fun getRepairSerNum(searchSerNumb: String): LiveData<List<RepairsView>> =
        repairsViewDao.getRepairsBySerialNumber(searchSerNumb)

    fun getRepairsById(searchId: Long): LiveData<RepairsView> =
        repairsViewDao.getRepairsById(searchId)

    fun getRepairsBySNandCustomer(searchSNandCustomer: RepairsFilter): LiveData<List<RepairsView>> {
        val search = "%${searchSNandCustomer.searchString}%"
        val filter = searchSNandCustomer.filterSwitch.value
        return repairsViewDao.getRepairsBySNandCustomer(search, filter)
    }

    fun getSelectedItemsForSend(selected: List<Long>): LiveData<String> {
        var resultString = ""
        val result = MutableLiveData<String>()

        GlobalScope.launch(context = Dispatchers.IO) {
            repairsViewDao.getSelectedItems(selected).forEach {
                resultString =
                    resultString + it.serialNumber + "," + it.modelName + "," + it.customerName + "," + it.defect + "\n"
            }
            result.postValue(resultString)
        }
        return result
    }
}