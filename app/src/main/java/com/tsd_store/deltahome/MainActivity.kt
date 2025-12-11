package com.tsd_store.deltahome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.tsd_store.deltahome.data.remote.NetworkClient
import com.tsd_store.deltahome.presentation.DevicesScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DevicesScreen().Content()

        }

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDQ5MGQxYzgzZWIwNTkxZjM2MzlhNWU3ZDc5NTUzMDdjY2IyMDUxN2JlMjBkNjExNGMxOWM4YTA5ZGJjM2JjMmE3ZDIyNDJmYmU5NWM1MzQiLCJpYXQiOjE3NjUzMjU5NzkuMTY2MTQ1LCJuYmYiOjE3NjUzMjU5NzkuMTY2MTQ4LCJleHAiOjE3OTY4NjE5NzkuMTU4NTIsInN1YiI6IjEiLCJzY29wZXMiOlsiYWRtaW5lcl9zZXJ2aWNlIl19.cVwYRZgoFaVLfeGtcKOgy6BzuByGv0tsgT0a_EtU-KqJbYW7ZM2F1W6dnhwF4G1FtPpL41MLGL0WdQXOUD8Pdi0ZkBODns-XCiKqk1zwjdpmTi-WnFxRy7sNvxrjcjHEG7Tddka0jUcnj8j_Qj-XVlNqJPALay-YxqYTMYHaaWGNOuqg4SRT-YZrAFvVtEPCts3AROSo8UbywvysCc2LZ4BlpvlWN9livK66Y_SDSFHNCKm_mpp6NbCqw2Z7OkmRtEd-ttI4HFYtpO6m1NbX3bPRV43H1Tun1nDgr6ifRFw8GBNMwFfTEzCCQK9aOfYR5Ixue7ot3epRIxP5gN26n8hLoDiY1pFV1QZwmt3rJGMjEHNsABb5k7caQ-WQA-jK65kStm-VVhHM-wn6TClKETgYX3L8zT5x4DTCG_bq0SDmrLCWbYUwG1LGHJ85Pe73fr0GCDPJzcEWCoM3dKxvk51DK1VuLHJP-FMFJkAv-58meuidQwaUFXrsZ0YVK5iYs4aCSuPWTX85iTCieGBhuAkfQIrp9OZQNaKR8_wP8p65d0SQTZiiYMHJ6MY39pyT4cszLxRkaMAWI1ibfF_ktRgUygSpy2a53iKnL1wZ40NTpptw8e59ApXANyseiYwAn9VIoGwabb00yHyWQ8hD8h4pJ-4QvSw_ztLcfpt6gP4"

        NetworkClient.init(token)
    }
}



