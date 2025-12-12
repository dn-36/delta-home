package com.tsd_store.deltahome.di

import com.tsd_store.deltahome.data.remote.actual_remote.sensors_client.SensorsClient
import com.tsd_store.deltahome.datasource.FakeSensorsRepository
import com.tsd_store.deltahome.domain.repositories.SensorsRepository
import com.tsd_store.deltahome.presentation.DevicesViewModel
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val appModule = module {

    // Json
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
        }
    }

  //  single { SmartHomeRemoteDataSource() }
    factory {
        DevicesViewModel(
            repository = get<SensorsRepository>()
        )
    }
    single<SensorsRepository> { FakeSensorsRepository() }

   /* single {
        SmartHomeRemoteDataSource(
            json = get()
        )
    }*/



    factory { SensorsClient() }

}
