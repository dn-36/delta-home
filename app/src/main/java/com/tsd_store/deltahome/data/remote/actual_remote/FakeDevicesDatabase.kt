package com.tsd_store.deltahome.data.remote.actual_remote

import com.tsd_store.deltahome.data.remote.actual_remote.models.DeviceActionDto
import com.tsd_store.deltahome.data.remote.actual_remote.models.DeviceDto
import com.tsd_store.deltahome.data.remote.actual_remote.models.DeviceFieldDto
import com.tsd_store.deltahome.data.remote.actual_remote.models.DeviceTypeDto
import com.tsd_store.deltahome.data.remote.actual_remote.models.UnitDto

object FakeDevicesDatabase {


    fun getDevices(): List<DeviceDto> = listOf(
        // id = 6
        DeviceDto(
            id = 6,
            name = "GPS Датчик",
            ui = "ex3i-fq2g-krud",
            createdAt = "2025-12-07T03:13:05.000000Z",
            updatedAt = "2025-12-07T03:13:05.000000Z",
            typeId = 4,
            token = "3paw-2ymg-467i",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 4,
                name = "Счетчик электроэнергии",
                createdAt = "2025-03-05T17:08:20.000000Z",
                updatedAt = "2025-03-05T17:08:20.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 5,
                        name = "С1",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    ),
                    DeviceFieldDto(
                        id = 6,
                        name = "С2",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    ),
                    DeviceFieldDto(
                        id = 7,
                        name = "С3",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 13
        DeviceDto(
            id = 13,
            name = "датчик влажности",
            ui = "nwu5-kdzr-ups9",
            createdAt = "2025-12-10T01:11:52.000000Z",
            updatedAt = "2025-12-10T01:11:52.000000Z",
            typeId = 2,
            token = "1w76-d6tb-zcbm",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 2,
                name = "Датчик влажности",
                createdAt = "2025-03-05T17:06:43.000000Z",
                updatedAt = "2025-03-05T17:06:43.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 2,
                        name = "влажность",
                        type = "string",
                        deviceTypeId = 2,
                        createdAt = "2025-03-05T17:06:43.000000Z",
                        updatedAt = "2025-03-05T17:06:43.000000Z",
                        unitId = 1,
                        unit = UnitDto(
                            id = 1,
                            name = "шт",
                            ui = null,
                            createdAt = "2025-02-09T10:07:22.000000Z",
                            updatedAt = "2025-02-09T10:07:22.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 14
        DeviceDto(
            id = 14,
            name = "test",
            ui = "bc47-826o-zqha",
            createdAt = "2025-12-10T01:12:23.000000Z",
            updatedAt = "2025-12-10T01:18:03.000000Z",
            typeId = 2,
            token = "u1tz-x4on-zvb3",
            status = "work",
            alarm = "yes",
            type = DeviceTypeDto(
                id = 2,
                name = "Датчик влажности",
                createdAt = "2025-03-05T17:06:43.000000Z",
                updatedAt = "2025-03-05T17:06:43.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 2,
                        name = "влажность",
                        type = "string",
                        deviceTypeId = 2,
                        createdAt = "2025-03-05T17:06:43.000000Z",
                        updatedAt = "2025-03-05T17:06:43.000000Z",
                        unitId = 1,
                        unit = UnitDto(
                            id = 1,
                            name = "шт",
                            ui = null,
                            createdAt = "2025-02-09T10:07:22.000000Z",
                            updatedAt = "2025-02-09T10:07:22.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 15
        DeviceDto(
            id = 15,
            name = "Датчик ток - напляжения",
            ui = "omwu-l4kd-z9hv",
            createdAt = "2025-12-10T01:12:35.000000Z",
            updatedAt = "2025-12-10T01:12:35.000000Z",
            typeId = 3,
            token = "1x9e-q2r3-xp0h",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 3,
                name = "Датчик тока - напляжения",
                createdAt = "2025-03-05T17:07:08.000000Z",
                updatedAt = "2025-03-05T17:07:08.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 3,
                        name = "ТОК",
                        type = "string",
                        deviceTypeId = 3,
                        createdAt = "2025-03-05T17:07:08.000000Z",
                        updatedAt = "2025-03-05T17:07:48.000000Z",
                        unitId = 5,
                        unit = UnitDto(
                            id = 5,
                            name = "Ампер",
                            ui = null,
                            createdAt = "2025-03-05T17:07:21.000000Z",
                            updatedAt = "2025-03-05T17:07:21.000000Z"
                        )
                    ),
                    DeviceFieldDto(
                        id = 4,
                        name = "Напряжение",
                        type = "string",
                        deviceTypeId = 3,
                        createdAt = "2025-03-05T17:07:08.000000Z",
                        updatedAt = "2025-03-05T17:07:48.000000Z",
                        unitId = 6,
                        unit = UnitDto(
                            id = 6,
                            name = "вольт",
                            ui = null,
                            createdAt = "2025-03-05T17:07:39.000000Z",
                            updatedAt = "2025-03-05T17:07:39.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 16
        DeviceDto(
            id = 16,
            name = "счетчик электроэнергии",
            ui = "dxzc-skxb-3gaq",
            createdAt = "2025-12-10T01:13:02.000000Z",
            updatedAt = "2025-12-10T01:13:02.000000Z",
            typeId = 4,
            token = "628b-vacf-jn8e",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 4,
                name = "Счетчик электроэнергии",
                createdAt = "2025-03-05T17:08:20.000000Z",
                updatedAt = "2025-03-05T17:08:20.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 5,
                        name = "С1",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    ),
                    DeviceFieldDto(
                        id = 6,
                        name = "С2",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    ),
                    DeviceFieldDto(
                        id = 7,
                        name = "С3",
                        type = "string",
                        deviceTypeId = 4,
                        createdAt = "2025-03-05T17:08:20.000000Z",
                        updatedAt = "2025-03-05T17:08:35.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 17
        DeviceDto(
            id = 17,
            name = "Датчик потока",
            ui = "905c-o067-gie0",
            createdAt = "2025-12-10T01:13:34.000000Z",
            updatedAt = "2025-12-10T01:13:34.000000Z",
            typeId = 5,
            token = "bcml-fsyi-ohn5",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 5,
                name = "Датчик потока",
                createdAt = "2025-03-05T17:08:58.000000Z",
                updatedAt = "2025-03-05T17:09:14.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 8,
                        name = "Расход",
                        type = "integer",
                        deviceTypeId = 5,
                        createdAt = "2025-03-05T17:08:58.000000Z",
                        updatedAt = "2025-12-07T02:59:01.000000Z",
                        unitId = 7,
                        unit = UnitDto(
                            id = 7,
                            name = "кВт",
                            ui = null,
                            createdAt = "2025-03-05T17:08:28.000000Z",
                            updatedAt = "2025-03-05T17:08:28.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 18
        DeviceDto(
            id = 18,
            name = "Датчик задымленности",
            ui = "hxfs-jlfb-y6un",
            createdAt = "2025-12-10T01:13:53.000000Z",
            updatedAt = "2025-12-10T01:13:53.000000Z",
            typeId = 6,
            token = "lsko-viex-u3lw",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 6,
                name = "Датчик задымленности",
                createdAt = "2025-03-05T17:09:59.000000Z",
                updatedAt = "2025-03-05T17:09:59.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 9,
                        name = "Аларм",
                        type = "string",
                        deviceTypeId = 6,
                        createdAt = "2025-03-05T17:09:59.000000Z",
                        updatedAt = "2025-03-05T17:09:59.000000Z",
                        unitId = 1,
                        unit = UnitDto(
                            id = 1,
                            name = "шт",
                            ui = null,
                            createdAt = "2025-02-09T10:07:22.000000Z",
                            updatedAt = "2025-02-09T10:07:22.000000Z"
                        )
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 19
        DeviceDto(
            id = 19,
            name = "Датчик пожарный, газа, утечки и пр.",
            ui = "y05d-jqbc-h5n1",
            createdAt = "2025-12-10T01:18:28.000000Z",
            updatedAt = "2025-12-10T01:18:28.000000Z",
            typeId = 7,
            token = "1jlv-xvry-icap",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 7,
                name = "Датчик пожарный, газа, утечки и пр.",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_work_and_alarm",
                fields = emptyList(),
                actions = emptyList()
            )
        ),

        // id = 20
        DeviceDto(
            id = 20,
            name = "счетчик воды",
            ui = "pzgn-tbng-6ngz",
            createdAt = "2025-12-10T01:20:09.000000Z",
            updatedAt = "2025-12-10T01:20:09.000000Z",
            typeId = 8,
            token = "plch-6m3q-s81n",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 8,
                name = "Счетчик воды",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_water",
                fields = listOf(
                    DeviceFieldDto(
                        id = 10,
                        name = "Показание",
                        type = "integer",
                        deviceTypeId = 8,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 21
        DeviceDto(
            id = 21,
            name = "3-х тарифный счетчик",
            ui = "6km2-ws9x-gh52",
            createdAt = "2025-12-10T01:20:36.000000Z",
            updatedAt = "2025-12-10T01:20:36.000000Z",
            typeId = 9,
            token = "ytas-ekif-wl9j",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 9,
                name = "3-х тарифный счетчик",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_tree_data",
                fields = listOf(
                    DeviceFieldDto(
                        id = 11,
                        name = "Показание 1",
                        type = "integer",
                        deviceTypeId = 9,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    ),
                    DeviceFieldDto(
                        id = 12,
                        name = "Показание 2",
                        type = "integer",
                        deviceTypeId = 9,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    ),
                    DeviceFieldDto(
                        id = 13,
                        name = "Показание 3",
                        type = "integer",
                        deviceTypeId = 9,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 22
        DeviceDto(
            id = 22,
            name = "Трекер координат",
            ui = "1oup-s9yr-zhwf",
            createdAt = "2025-12-10T01:20:59.000000Z",
            updatedAt = "2025-12-10T01:20:59.000000Z",
            typeId = 10,
            token = "4a83-r6x4-8b1i",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 10,
                name = "Трекер координат",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_coordinat",
                fields = listOf(
                    DeviceFieldDto(
                        id = 14,
                        name = "Ширина",
                        type = "string",
                        deviceTypeId = 10,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    ),
                    DeviceFieldDto(
                        id = 15,
                        name = "Долгота",
                        type = "string",
                        deviceTypeId = 10,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    )
                ),
                actions = emptyList()
            )
        ),

        // id = 23
        DeviceDto(
            id = 23,
            name = "Управляемое освещение",
            ui = "1mvp-ra5j-h4j2",
            createdAt = "2025-12-10T01:21:26.000000Z",
            updatedAt = "2025-12-10T01:21:26.000000Z",
            typeId = 11,
            token = "lim0-ewdu-rqx2",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 11,
                name = "Управляемое освещение",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_light",
                fields = listOf(
                    DeviceFieldDto(
                        id = 16,
                        name = "Яркость(процент)",
                        type = "integer",
                        deviceTypeId = 11,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    ),
                    DeviceFieldDto(
                        id = 17,
                        name = "Вкл./Выкл.",
                        type = "bool",
                        deviceTypeId = 11,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    )
                ),
                actions = listOf(
                    DeviceActionDto(
                        id = 1,
                        name = "Увеличить яркость",
                        command = "add_light",
                        ui = "18dg-jf3t-cgbl",
                        deviceFieldId = 16,
                        deviceTypeId = 11,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z"
                    ),
                    DeviceActionDto(
                        id = 2,
                        name = "Уменьшить яркость",
                        command = "cut_light",
                        ui = "wgnj-9xtn-npa3",
                        deviceFieldId = 16,
                        deviceTypeId = 11,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z"
                    ),
                    DeviceActionDto(
                        id = 3,
                        name = "Вкл./Выкл.",
                        command = "on_off",
                        ui = "ck0v-jnpo-46mx",
                        deviceFieldId = 17,
                        deviceTypeId = 11,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z"
                    )
                )
            )
        ),

        // id = 24
        DeviceDto(
            id = 24,
            name = "задвижка",
            ui = "hafj-rbtc-kxpw",
            createdAt = "2025-12-10T01:21:45.000000Z",
            updatedAt = "2025-12-10T01:21:45.000000Z",
            typeId = 12,
            token = "rtmb-y1d4-x7be",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 12,
                name = "Задвижка",
                createdAt = "2025-12-03T13:15:19.000000Z",
                updatedAt = "2025-12-03T13:15:19.000000Z",
                systemName = "sensor_gate",
                fields = listOf(
                    DeviceFieldDto(
                        id = 18,
                        name = "Открыта/Закрыта",
                        type = "bool",
                        deviceTypeId = 12,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z",
                        unitId = null,
                        unit = null
                    )
                ),
                actions = listOf(
                    DeviceActionDto(
                        id = 4,
                        name = "Открыть/Закрыть",
                        command = "on_off",
                        ui = "6fik-ys1t-x6ef",
                        deviceFieldId = 18,
                        deviceTypeId = 12,
                        createdAt = "2025-12-03T13:15:19.000000Z",
                        updatedAt = "2025-12-03T13:15:19.000000Z"
                    )
                )
            )
        )
    )
}