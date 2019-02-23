package kampukter.service.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kampukter.service.data.*
import kampukter.service.data.dao.CustomerDao
import kampukter.service.data.dao.ModelsDao
import kampukter.service.data.dao.RepairDao
import kampukter.service.data.dao.RepairsViewDao

@Database(
    version = 1, entities = [
        Models::class, Customer::class, Repair::class
    ]
)

@TypeConverters(Converters::class)

abstract class ServiceDatabase : RoomDatabase() {

    abstract fun modelsDao(): ModelsDao
    abstract fun customerDao(): CustomerDao
    abstract fun repairDao(): RepairDao
    abstract fun repairsViewDao(): RepairsViewDao

}