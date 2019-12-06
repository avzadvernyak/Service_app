package kampukter.service.data.dto

import java.io.File

interface SendBackupDto {
    fun sendFileBackup( file: File)
}