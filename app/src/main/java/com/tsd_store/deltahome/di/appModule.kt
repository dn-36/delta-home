package com.tsd_store.deltahome.di

//import com.tsd_store.deltahome.data.remote.SmartHomeJsonFakeBackend
import com.tsd_store.deltahome.data.remote.old_remote.SmartHomeRemoteDataSource
//import com.tsd_store.deltahome.datasource.SmartHomeJsonDataSource
import com.tsd_store.deltahome.domain.old_domain.DeviceRepositoryApi
import com.tsd_store.deltahome.domain.old_domain.SmartHomeSyncApi
import com.tsd_store.deltahome.feature.home.viewmodel.HomeViewModel
import com.tsd_store.deltahome.repository.DeviceRepositoryImpl
import com.tsd_store.deltahome.repository.SmartHomeSyncImpl
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    // Json
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
        }
    }


    single { SmartHomeRemoteDataSource() }

    single {
        SmartHomeRemoteDataSource(
            json = get()
        )
    }

    // Новый слой синхронизации
    single<SmartHomeSyncApi> {
        SmartHomeSyncImpl(
            chatsApi = get(),
            json = get(),

        )
    }

    // Repository
    single<DeviceRepositoryApi> {
        DeviceRepositoryImpl(
            remote = get(),
            syncApi = get()
        )
    }

    // ViewModel
    viewModel {
        HomeViewModel(
            repository = get()
        )
    }
}
