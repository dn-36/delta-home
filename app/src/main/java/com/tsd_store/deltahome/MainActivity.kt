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

        val token =
"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNDcwMDlkOWQ5OTBiN2FlNzc2Yjc5ZWViYzVjOGJiMGQxY2JhNjViNjliM2I0YjYyYjUyZGJkZDNkNmVjMTQyMWE5NmY3MzdhNDRiYTI2NjUiLCJpYXQiOjE3NjUyNjQ4MTEuNjcwOTkyLCJuYmYiOjE3NjUyNjQ4MTEuNjcwOTk2LCJleHAiOjE3OTY4MDA4MTEuNjU4NDg3LCJzdWIiOiIxIiwic2NvcGVzIjpbImFkbWluZXJfc2VydmljZSJdfQ.W0qK2FeiVFpFBzxvDDUr8vDddU8jmOmvB7UJqwzrJW9I9kjlEjnZHcP8Fim1hhtJIx4CSHCdlrIS0yQeH12Uo9T33B4zaZiio4_EtsJEQoPUy3jAqklX-ikD1wwDYj0f8c82oU3u94KV6pW4Jx5e5jNNLoxnrOPFZkCwlZjWe8LZ20iirH3E5leIwAWPvjqzuzd0BPpkP35zHfY-98AztKnK9XM-B3Lwon19SAUPHqvFWR2VL4QzuTa6ZPj_ElTRCYWSRnniVSnQ_TanjN6gU4goMUUvrhUOd4Op1JM3Bjl_iRWltonBRwbYeJxwJm9E8vEn5Gsk1nMdWSl0lAbr-Rcx-JHHKmWRILDAIYsgovbGbJDb2jw7g7rkqX-OBMeLpTWw_NhudnOzZi4XOyIBWPUt1W5bsaOQOytnWYekmBU0kFR0iAA1Ed3T3iXkV57k9xvej06CB3Y7gMBDR0zfqHsXcut3oVtt-XjqMzyOWzopQLxr9cgRD5Hnl_d_UbctdOxt4gPWZ-0YK6Dpp5xbh16ws3NF4jSH8mqM2_jDYY3cggkJMp3PLYFdruEqGnWOYZBdw98cT6hvBQeYgAdJGh210yR1UGtGb3ytAiv37TaI5nEgAJB_VShhMs80xlamfOyf0UKSg8gLTOI8pPVYl9oaAl9XnpDBoHuBkcHToDw"
        NetworkClient.init(token)
    }
}



