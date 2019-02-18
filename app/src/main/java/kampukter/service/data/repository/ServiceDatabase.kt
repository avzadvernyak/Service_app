package kampukter.service.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import kampukter.service.data.Customer
import kampukter.service.data.Models
import kampukter.service.data.Repair
import kampukter.service.data.dao.CustomerDao
import kampukter.service.data.dao.ModelsDao
import kampukter.service.data.dao.RepairDao
import kampukter.service.data.dao.RepairsViewDao

@Database(version = 1, entities = [
    Models::class, Customer::class, Repair::class
])
abstract class ServiceDatabase : RoomDatabase() {

    abstract fun modelsDao(): ModelsDao
    abstract fun customerDao(): CustomerDao
    abstract fun repairDao(): RepairDao
    abstract fun repairsViewDao(): RepairsViewDao

}