package com.tsd_store.deltahome.di

//import com.tsd_store.deltahome.data.remote.SmartHomeJsonFakeBackend
import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.data.remote.old_remote.SmartHomeRemoteDataSource
import com.tsd_store.deltahome.datasource.CreateSensorRepositoryImpl
import com.tsd_store.deltahome.datasource.GetSensorsRepositoryImpl
import com.tsd_store.deltahome.datasource.GetValueSensorRepositoryImpl
import com.tsd_store.deltahome.datasource.SendAlarmSensorRepositoryImpl
import com.tsd_store.deltahome.datasource.SendStatusSensorRepositoryImpl
import com.tsd_store.deltahome.datasource.SendValueSensorRepositoryImpl
import com.tsd_store.deltahome.domain.actual_domain.repositories.CreateSensorRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetSensorsRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.repositories.GetValueSensorRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendAlarmSensorRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendStatusSensorRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.repositories.SendValueSensorRepositoryApi
import com.tsd_store.deltahome.domain.actual_domain.usecases.CreateSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.GetSensorsUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.GetValueSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendAlarmSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendStatusSensorUseCase
import com.tsd_store.deltahome.domain.actual_domain.usecases.SendValueSensorUseCase
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

    factory { CreateSensorUseCase(get()) }

    factory { GetSensorsUseCase(get()) }

    factory { GetValueSensorUseCase(get()) }

    factory { SendAlarmSensorUseCase(get()) }

    factory { SendStatusSensorUseCase(get()) }

    factory { SendValueSensorUseCase(get()) }


    factory { CreateSensorRepositoryImpl(get()) as CreateSensorRepositoryApi }

    factory { GetSensorsRepositoryImpl(get()) as GetSensorsRepositoryApi }

    factory { GetValueSensorRepositoryImpl(get()) as GetValueSensorRepositoryApi }

    factory { SendAlarmSensorRepositoryImpl(get()) as SendAlarmSensorRepositoryApi }

    factory { SendStatusSensorRepositoryImpl(get()) as SendStatusSensorRepositoryApi }

    factory { SendValueSensorRepositoryImpl(get()) as SendValueSensorRepositoryApi }


    factory { SensorsClient() }

}
