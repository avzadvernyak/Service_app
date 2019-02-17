package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import kampukter.service.data.RepairsView
import kampukter.service.data.dao.RepairsViewDao

class RepairsViewRepository(private val repairsViewDao: RepairsViewDao) {

    fun getAll(): LiveData<List<RepairsView>> = repairsViewDao.getAll()
}