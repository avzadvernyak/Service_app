package kampukter.service

import android.app.Application
import androidx.room.Room
import kampukter.service.data.repository.*
import kampukter.service.viewmodel.ServiceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ServiceApplication : Application() {

    private val module = module {
        single { Room.databaseBuilder(androidContext(), ServiceDatabase::class.java, "service.db").build() }
        single { ModelsRepository(get<ServiceDatabase>().modelsDao()) }
        single { CustomerRepository(get<ServiceDatabase>().customerDao()) }
        single { RepairRepository(get<ServiceDatabase>().repairDao()) }
        single { RepairsViewRepository(get<ServiceDatabase>().repairsViewDao()) }
        viewModel { ServiceViewModel(get(),get(),get(),get())}
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ServiceApplication)
            modules(module)
        }
    }

}