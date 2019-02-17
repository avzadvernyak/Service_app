package kampukter.service.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kampukter.service.data.Models

@Dao
interface ModelsDao : BasicDao<Models> {

    @Query("select * from models")
    fun getAll(): LiveData<List<Models>>

    @Query("select * from models where title like :query")
    fun search(query: String): LiveData<List<Models>>

    @Query("select * from models where id = :modelId limit 1")
    fun getModelId(modelId: Long ): LiveData<Models>

    @Query("delete from models")
    suspend fun deleteAll()
}