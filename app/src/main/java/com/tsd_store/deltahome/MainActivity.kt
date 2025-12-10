package com.tsd_store.deltahome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.tsd_store.deltahome.data.remote.NetworkClient
import com.tsd_store.deltahome.feature.home.ui.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Navigator(HomeScreen())

        }
      /* val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZDcyMGE5MzQzMTYzYmRkN2ZkNmJmNmE" +
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
               "ppL4s3D8WE6ZIu-fv6K03-UObWrCZW-o59eUSfwxDghH0vpGKRIhrZMHCG5l4sXOQCfMM0x8R1Yca0DZwr3lsTw" */

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDQ5MGQxYzgzZWIwNTkxZjM2MzlhNWU3ZDc5NTUzMDdjY2IyMDUxN2JlMjBkNjExNGMxOWM4YTA5ZGJjM2JjMmE3ZDIyNDJmYmU5NWM1MzQiLCJpYXQiOjE3NjUzMjU5NzkuMTY2MTQ1LCJuYmYiOjE3NjUzMjU5NzkuMTY2MTQ4LCJleHAiOjE3OTY4NjE5NzkuMTU4NTIsInN1YiI6IjEiLCJzY29wZXMiOlsiYWRtaW5lcl9zZXJ2aWNlIl19.cVwYRZgoFaVLfeGtcKOgy6BzuByGv0tsgT0a_EtU-KqJbYW7ZM2F1W6dnhwF4G1FtPpL41MLGL0WdQXOUD8Pdi0ZkBODns-XCiKqk1zwjdpmTi-WnFxRy7sNvxrjcjHEG7Tddka0jUcnj8j_Qj-XVlNqJPALay-YxqYTMYHaaWGNOuqg4SRT-YZrAFvVtEPCts3AROSo8UbywvysCc2LZ4BlpvlWN9livK66Y_SDSFHNCKm_mpp6NbCqw2Z7OkmRtEd-ttI4HFYtpO6m1NbX3bPRV43H1Tun1nDgr6ifRFw8GBNMwFfTEzCCQK9aOfYR5Ixue7ot3epRIxP5gN26n8hLoDiY1pFV1QZwmt3rJGMjEHNsABb5k7caQ-WQA-jK65kStm-VVhHM-wn6TClKETgYX3L8zT5x4DTCG_bq0SDmrLCWbYUwG1LGHJ85Pe73fr0GCDPJzcEWCoM3dKxvk51DK1VuLHJP-FMFJkAv-58meuidQwaUFXrsZ0YVK5iYs4aCSuPWTX85iTCieGBhuAkfQIrp9OZQNaKR8_wP8p65d0SQTZiiYMHJ6MY39pyT4cszLxRkaMAWI1ibfF_ktRgUygSpy2a53iKnL1wZ40NTpptw8e59ApXANyseiYwAn9VIoGwabb00yHyWQ8hD8h4pJ-4QvSw_ztLcfpt6gP4"

        NetworkClient.init(token)
    }
}



