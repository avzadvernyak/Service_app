package kampukter.service.data.dto

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import androidx.core.content.FileProvider
import java.io.File


class EmailSendBackupDto(private val context: Context) : SendBackupDto {
    override fun sendFileBackup(file: File) {
        val addresses = "86tona@mail.ru"
        val subject = "Backup"
        val contentUri = FileProvider.getUriForFile(context, "kampukter.service", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, addresses)
        }
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            .addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            .type = "text/plain"
        context.startActivity(intent)
    }
}

//data = Uri.parse("mailto:"+addresses)
//putExtra(Intent.EXTRA_EMAIL, addresses)
//val pm: PackageManager = context.packageManager