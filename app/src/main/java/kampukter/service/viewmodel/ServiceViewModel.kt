package kampukter.service.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kampukter.service.data.*
import kampukter.service.data.repository.CustomerRepository
import kampukter.service.data.repository.ModelsRepository
import kampukter.service.data.repository.RepairRepository
import kampukter.service.data.repository.RepairsViewRepository

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
    private val _setRepairSN = MutableLiveData<String>()

    fun setQuerySNRepair(query: String) {
        _setRepairSN.postValue(query)
    }

    val repairState: LiveData<RepairState> =
        Transformations.switchMap(_setRepairSN) { query -> repairRepository.getStateRepair(query) }

    val repair = repairRepository.getAll()

    fun addRepair(repair: Repair) {
        repairRepository.add(repair)
    }

    fun delAllRepair() {
        repairRepository.delAllRecords()
    }
    fun deleteSelected(selected: Set<Long>){repairRepository.deleteSelected(selected)}

    var repairForSave: Repair? = null
    fun putRepairForSave(repair: Repair) {
        repairForSave = repair
    }

    fun endRepairIssue() {
        repairForSave?.let {
            repairRepository.endRepair(it)
        }
    }

    fun endRepair(repair: Repair) {
        repairRepository.endRepair(repair)
    }

    /*
   * RepairsView
   *
   */
    private val _queryId = MutableLiveData<Long>()

    fun setQueryId(query: Long) {
        _queryId.postValue(query)
    }

    val repairsId: LiveData<RepairsView> =
        Transformations.switchMap(_queryId) { query -> repairsViewRepository.getRepairsById(query) }

    private val _querySNandCustomer = MutableLiveData<String>()
    fun setQuerySNandCustomer(query: String) {
        _querySNandCustomer.postValue(query)
    }

    fun getQuerySNandCustomer(): String? {
        return _querySNandCustomer.value
    }

    fun clearSearchSNCustomer() {
        _querySNandCustomer.postValue("")
    }

    val repairsView: LiveData<List<RepairsView>> =
        Transformations.switchMap(_querySNandCustomer) { query -> repairsViewRepository.getRepairsBySNandCustomer("%$query%") }


    private val _querySeriaNumber = MutableLiveData<String>()
    fun setQuerySerialNumber(query: String) {
        _querySeriaNumber.postValue(query)
    }

    fun clearSearchSerialNumber() {
        _querySeriaNumber.postValue("")
    }

    val repairs: LiveData<List<RepairsView>> =
        Transformations.switchMap(_querySeriaNumber) { query -> repairsViewRepository.getRepairSerNum(query) }


    private val _selected = MutableLiveData<List<Long>>()
    fun setSelected(selected: List<Long>) {
        _selected.postValue(selected)
    }

    val repairToSend: LiveData<String> =
        Transformations.switchMap(_selected) { query -> repairsViewRepository.getSelectedItemsForSend(query) }


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
        modelsRepository.add(Models(title = model))
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
        customerRepository.add(Customer(title = name))
    }
}