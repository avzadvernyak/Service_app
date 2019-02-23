package kampukter.service

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kampukter.service.data.Models
import kampukter.service.data.repository.*
import kampukter.service.viewmodel.ServiceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ServiceApplication : Application() {

    private val module = module {

        viewModel { ServiceViewModel(get(), get(), get(), get()) }
        single { ModelsRepository(get<ServiceDatabase>().modelsDao()) }
        single { CustomerRepository(get<ServiceDatabase>().customerDao()) }
        single { RepairRepository(get<ServiceDatabase>().repairDao()) }
        single { RepairsViewRepository(get<ServiceDatabase>().repairsViewDao()) }

        single {
            Room.databaseBuilder(androidContext(), ServiceDatabase::class.java, "service.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(supportDb: SupportSQLiteDatabase) {
                        GlobalScope.launch(context = Dispatchers.IO) {
                            get<ServiceDatabase>().modelsDao().insertAll(
                                listOf(
                                    Models(title = "D-Link DES-3200-26/A1"),
                                    Models(title = "D-Link DES-3200-26/B1")
                                )
                            )
                        }

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