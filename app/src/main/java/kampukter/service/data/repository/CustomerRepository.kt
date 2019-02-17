package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import kampukter.service.data.Customer
import kampukter.service.data.dao.CustomerDao

class CustomerRepository(private val customerDao: CustomerDao) {

    fun searchCustomer(query: String): LiveData<List<Customer>> = customerDao.search(query)
    fun getAll(): LiveData<List<Customer>> = customerDao.getAll()
    fun getCustomerId(customerId: Long): LiveData<Customer> = customerDao.getModelId(customerId)

    suspend fun delAllRecords() = customerDao.deleteAll()
    suspend fun add(customer: Customer) {
        customerDao.insert(customer)
    }
}