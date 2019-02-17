package kampukter.service.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kampukter.service.data.Customer
import kampukter.service.data.Models
import kampukter.service.data.repository.CustomerRepository
import kampukter.service.data.repository.ModelsRepository
import kampukter.service.data.repository.RepairRepository
import kampukter.service.data.repository.RepairsViewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val modelsRepository: ModelsRepository,
    private val customerRepository: CustomerRepository,
    private val repairRepository: RepairRepository,
    private val repairsViewRepository: RepairsViewRepository
) : ViewModel() {

    /*
    * Repair Entity
    *
    */
    val repair = repairRepository.getAll()
    /*
   * RepairsView
   *
   */
    val repairsView = repairsViewRepository.getAll()

    /*
    * Model Entity
    *
    */
    private val _queryModel = MutableLiveData<String>()
    val models: LiveData<List<Models>> =
        Transformations.switchMap(_queryModel) { query -> modelsRepository.searchModel("%$query%") }
    fun setQuery(query: String) {
        _queryModel.postValue(query)
    }
    fun clearSearch() {
        _queryModel.postValue("")
    }


    private val _id = MutableLiveData<Long>()
    val modelId: LiveData<Models> =
        Transformations.switchMap(_id) { id -> modelsRepository.getModelId(id) }
    fun setModelId(id: Long) {
        _id.postValue(id)
    }

    fun addModel(model: String) {
        GlobalScope.launch(context = Dispatchers.IO) {
            modelsRepository.add(Models(title = model))
        }
    }
    fun delAll() {
        GlobalScope.launch(context = Dispatchers.IO) {
            modelsRepository.delAllRecords()
        }
    }

    /*
    * Customer Entity
    *
    */
    private val _queryCustomer = MutableLiveData<String>()
    fun setQueryCustomer(query: String) {
        _queryCustomer.postValue(query)
    }
    fun clearSearchCustomer() {
        _queryCustomer.postValue("")
    }
    val customer: LiveData<List<Customer>> =
        Transformations.switchMap(_queryCustomer) { query -> customerRepository.searchCustomer("%$query%") }

    private val _idCustomer = MutableLiveData<Long>()
    fun setCustomerId(id: Long) {
        _idCustomer.postValue(id)
    }
    val customerId: LiveData<Customer> =
        Transformations.switchMap(_idCustomer) { id -> customerRepository.getCustomerId(id) }

    fun addCustomer(name: String) {
        GlobalScope.launch(context = Dispatchers.IO) {
            customerRepository.add(Customer(title = name))
        }
    }
}