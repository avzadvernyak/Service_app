package kampukter.service

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kampukter.service.data.Models
import kampukter.service.data.dao.BackupDao
import kampukter.service.data.dao.DefaultBackupDao
import kampukter.service.data.dto.EmailSendBackupDto
import kampukter.service.data.dto.SendBackupDto
import kampukter.service.data.repository.*
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ServiceApplication : Application() {

    private val module = module {

        viewModel { ServiceViewModel(get(), get(), get(), get(), get()) }

        single<BackupDao> { DefaultBackupDao(androidContext()) }
        single<SendBackupDto> { EmailSendBackupDto(androidContext()) }

        single { ModelsRepository(get<ServiceDatabase>().modelsDao()) }
        single { CustomerRepository(get<ServiceDatabase>().customerDao()) }
        single { RepairRepository(get<ServiceDatabase>().repairDao()) }
        single { RepairsViewRepository(get<ServiceDatabase>().repairsViewDao()) }
        single {
            BackupRepository(
                get<ServiceDatabase>().repairDao(),
                get<ServiceDatabase>().modelsDao(),
                get<ServiceDatabase>().customerDao(),
                get<BackupDao>(),
                get<SendBackupDto>()
            )
        }

        single {
            Room.databaseBuilder(androidContext(), ServiceDatabase::class.java, "service.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(supportDb: SupportSQLiteDatabase) {
                        GlobalScope.launch(context = Dispatchers.IO) {
                            get<ServiceDatabase>().modelsDao().insertAll(
                                listOf(
                                    Models(title = "D-Link DES-3200-10/A1"),
                                    Models(title = "D-Link DES-3200-10/C1"),
                                    Models(title = "D-Link DES-3200-18/A1"),
                                    Models(title = "D-Link DES-3200-18/C1"),
                                    Models(title = "D-Link DES-3200-26/A1"),
                                    Models(title = "D-Link DES-3200-26/B1"),
                                    Models(title = "D-Link DES-3200-26/С1"),
                                    Models(title = "D-Link DES-3200-28/A1"),
                                    Models(title = "D-Link DES-3200-28/C1"),
                                    Models(title = "D-Link DES-3200-52/A1"),
                                    Models(title = "D-Link DES-3200-52/C1"),
                                    Models(title = "D-Link DES-3028/A"),
                                    Models(title = "D-Link DES-3028/B"),
                                    Models(title = "D-Link DES-3052/A"),
                                    Models(title = "D-Link DES-3200-28F/A1"),
                                    Models(title = "D-Link DES-3200-28F/C1"),
                                    Models(title = "D-Link DES-1210-26/ME/B1"),
                                    Models(title = "D-Link DES-1210-28/A")
                                )
                            )
                        }
                        /*
                        GlobalScope.launch(context = Dispatchers.IO) {
                            get<ServiceDatabase>().customerDao().insertAll(
                                listOf(
                                    Customer(title = "Матрица (ДЭК)"),
                                    Customer(title = "ТРК Горизонт"),
                                    Customer(title = "ТРК Планета"),
                                    Customer(title = "ISP Енакиево"),
                                    Customer(title = "СТК Старобешево")
                                )
                            )
                        }*/

                    }
                }).build()
        }

    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ServiceApplication)
            modules(module)
        }
    }

}