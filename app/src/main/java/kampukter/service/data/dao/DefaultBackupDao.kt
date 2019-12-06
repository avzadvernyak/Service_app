package kampukter.service.data.dao

import android.content.Context
import java.io.File

class DefaultBackupDao(private val context: Context) : BackupDao {
    override fun getBackupFileUri(backup: String): File? {

        val cacheFile = File(context.cacheDir, "backup.txt")
        cacheFile.delete()
        cacheFile.appendText(backup)
        /*
        if (cacheFile.isFile) Log.d("blablabla", "yes")
        else Log.d("blablabla", "No")
        Log.d("blablabla",cacheFile.absolutePath )
         */
        return cacheFile    }
}