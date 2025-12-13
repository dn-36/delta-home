package com.tsd_store.deltahome.di

import com.tsd_store.deltahome.data.remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.FakeSensorsRepository
import com.tsd_store.deltahome.datasource.LoadDevicesRepositoryImpl
import com.tsd_store.deltahome.datasource.UpdateFieldValueRepositoryImpl
import com.tsd_store.deltahome.domain.repositories.LoadDevicesRepository
import com.tsd_store.deltahome.domain.repositories.SensorsRepository
import com.tsd_store.deltahome.domain.repositories.UpdateFieldValueRepository
import com.tsd_store.deltahome.presentation.DevicesViewModel
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {

    // Json
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
        }
    }
    single {
        FakeSensorsRepository() as SensorsRepository
    }
    single {
        LoadDevicesRepositoryImpl(get()) as LoadDevicesRepository
    }
    single {
        UpdateFieldValueRepositoryImpl(get()) as UpdateFieldValueRepository
    }


    factory {
        DevicesViewModel(
            repository = get<SensorsRepository>(),
            get(),get()
        )
    }
    single<SensorsRepository> { FakeSensorsRepository() }

    factory { SensorsClient() }

}
