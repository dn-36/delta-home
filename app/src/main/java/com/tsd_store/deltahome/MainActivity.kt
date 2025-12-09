package com.tsd_store.deltahome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.tsd_store.deltahome.data.remote.old_remote.NetworkClient
import com.tsd_store.deltahome.feature.home.ui.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Navigator(HomeScreen())

        }
       val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZDcyMGE5MzQzMTYzYmRkN2ZkNmJmNmE" +
               "5N2ExNThkNGZmODYzYTJjYmYxMDUzZTYyNmMxYzNiNTAwOWQ4ZWQwZTAxZmJlNTFkNGY4MDJhZGMiLCJpYX" +
               "QiOjE3NjM1MDQwNTkuNTM4MDA0LCJuYmYiOjE3NjM1MDQwNTkuNTM4MDA3LCJleHAiOjE3OTUwNDAwNTku" +
               "NTMzMzg1LCJzdWIiOiIyODQiLCJzY29wZXMiOlsidXNlcl9zZXJ2aWNlIl19.xHXJEaF5_wm1cCLqwiYlbe" +
               "8jc993zBjK1EzJqGItoOd-spEU4xOciSxpNjioIMT1biFUPeQUofzyNvbhAUSc8MS_pWDKF1LGC6swJNC_" +
               "RY8dMt436h3vcg0hJ6jmU92hfmGk_UCvWCXax2ChhOcjFdceZyXdq7sqFuMcd_G8dwcJOeV00LD6X5AHfg" +
               "ZhSKmAeXskm50_cCbrxJW16T63ZEkKl4w8vjj7DuOmsXZzvErxU16LhjHzbNULCZzwAu4a9J6IpGesfh-" +
               "l-vUBugfgGXgLvJy4_i9B0E9e-IQ2VhOkI8vfpLXZgWP1dq1tpxm9iac2_gpOSmUL3dy_la_3wC19aPVE" +
               "6KHI8Nvn6cH3jc7BTFm_CMqp4y4zBqs02pBa71-JMnk1YPUv_5IfGuZgU14JLI7I1qiZgtvL1CGMxfGtJS" +
               "P0_UT1TxjdJikqoh38qcu8QfX-cpV02bsoRxp8E_De4A4xgy0zgPA3eGKgI-7DdzGo_Y6Um1xS8MKJ5xu-U" +
               "RT0REQavLwRAisgzLyh2T4rjxIX9zSo9wfO32YCkHP3QbuUQKy-nVzsIFzBXq2id_WiiY7RWziIRRptYI7b" +
               "ppL4s3D8WE6ZIu-fv6K03-UObWrCZW-o59eUSfwxDghH0vpGKRIhrZMHCG5l4sXOQCfMM0x8R1Yca0DZwr3lsTw"

        NetworkClient.init(token)
    }
}



