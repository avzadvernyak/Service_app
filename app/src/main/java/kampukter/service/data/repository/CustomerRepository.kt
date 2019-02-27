package kampukter.service.data.repository

import androidx.lifecycle.LiveData
import kampukter.service.data.Customer
import kampukter.service.data.dao.CustomerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerRepository(private val customerDao: CustomerDao) {

    fun searchCustomer(query: String): LiveData<List<Customer>> = customerDao.search(query)
    fun getAll(): LiveData<List<Customer>> = customerDao.getAll()
    fun getCustomerId(customerId: Long): LiveData<Customer> = customerDao.getModelId(customerId)

    suspend fun delAllRecords() = customerDao.deleteAll()
    fun add(customer: Customer) {
        GlobalScope.launch(context = Dispatchers.IO) {
            customerDao.insert(customer)
        }
    }
}