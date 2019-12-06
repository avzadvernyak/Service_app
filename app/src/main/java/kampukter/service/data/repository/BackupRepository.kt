package kampukter.service.data.repository

import kampukter.service.data.Customer
import kampukter.service.data.Models
import kampukter.service.data.Repair
import kampukter.service.data.dao.BackupDao
import kampukter.service.data.dao.CustomerDao
import kampukter.service.data.dao.ModelsDao
import kampukter.service.data.dao.RepairDao
import kampukter.service.data.dto.SendBackupDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class BackupRepository(
    private val repairDao: RepairDao,
    private val modelsDao: ModelsDao,
    private val customerDao: CustomerDao,
    private val backupDao: BackupDao,
    private val sendBackupDto: SendBackupDto
) {
    fun createBackupFile(): Boolean {

        GlobalScope.launch(context = Dispatchers.IO) {
            var backupString = "<backup>\n"
            val modelsForBackup: List<Models>? = modelsDao.getAllModels()
            val customerForBackup: List<Customer>? = customerDao.getAllCustomer()
            val repairForBackup: List<Repair>? = repairDao.getAllRepairs()

            if (modelsForBackup != null) {
                backupString += " <models>\n"
                modelsForBackup.forEach {
                    backupString += "id = \'" + it.id + "\' "
                    backupString += "title = \'" + it.title + "\'\n"
                }
                backupString += " </models>\n"
            }
            if (customerForBackup != null) {
                backupString += " <сustomer>\n"
                customerForBackup.forEach {
                    backupString += "id = \'" + it.id + "\' "
                    backupString += "title = \'" + it.title + "\'\n"
                }
                backupString += " </сustomer>\n"
            }
            if (repairForBackup != null) {
                backupString += "<repairs>\n"
                repairForBackup.forEach {
                    var myDateTime = ""
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())

                    //val currentTime = Calendar.getInstance().time
                    //Log.d("blablabla", sdf.format(currentTime))
                    //sdf.timeZone = TimeZone.getTimeZone("GMT+02:00")
                    //Log.d("blablabla", sdf.format(currentTime))

                    it.issueDate?.let { _date ->  myDateTime = sdf.format(_date) }
                    backupString =
                        backupString + "id = " + it.id.toString() + "; BeginDate = " + it.beginDate.time.toString() +
                                "; EndDate = " + it.endDate?.time.toString() +
                                "; IssueDate = " + myDateTime+
                                "; CustomerId = " + it.customerId + "; ModelId = " + it.modelId +
                                "; SerialNumber = " + it.serialNumber + "; Defect = " + it.defect +
                                "; Notes = " + it.notes + "\n"
                }
                backupString += " </repairs>\n"
            }
            backupString += "</backup>"
            //Log.d("blablabla", backupString)
            val backupUri = backupDao.getBackupFileUri(backupString)
            backupUri?.let { sendBackupDto.sendFileBackup(it) }
        }
        return true
    }
}