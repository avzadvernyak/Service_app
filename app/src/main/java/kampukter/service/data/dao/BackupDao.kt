package kampukter.service.data.dao

import java.io.File

interface BackupDao {

    fun getBackupFileUri(backup: String): File?

}