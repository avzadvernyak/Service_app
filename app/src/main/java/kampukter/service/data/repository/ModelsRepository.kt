package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import kampukter.service.data.Models
import kampukter.service.data.dao.ModelsDao

class ModelsRepository(private val modelsDao: ModelsDao) {

    fun searchModel(query: String): LiveData<List<Models>> = modelsDao.search(query)
    fun getAll(): LiveData<List<Models>> = modelsDao.getAll()
    fun getModelId(modelId: Long): LiveData<Models>  = modelsDao.getModelId(modelId)

    suspend fun delAllRecords() = modelsDao.deleteAll()
    suspend fun add(model: Models) {
        modelsDao.insert(model)
    }
}