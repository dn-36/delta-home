package com.tsd_store.deltahome.data.remote.actual_remote

object FakeDevicesDatabase {

    fun getDevices(): List<DeviceDto> = listOf(
        DeviceDto(
            id = 1,
            name = "test",
            ui = "pf6n-r1dz-dr17",
            createdAt = "2025-02-10T20:18:28.000000Z",
            updatedAt = "2025-10-21T15:53:11.000000Z",
            typeId = 6,
            token = "iw6b-pg80-5d1g",
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

        DeviceDto(
            id = 3,
            name = "Тестовый",
            ui = "t73n-h6x1-ofq1",
            createdAt = "2025-03-14T12:51:53.000000Z",
            updatedAt = "2025-10-21T15:53:11.000000Z",
            typeId = 3,
            token = "qf1c-rmfb-cauh",
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

        DeviceDto(
            id = 4,
            name = "xxcxzc",
            ui = "g71f-dbi8-sovn",
            createdAt = "2025-10-27T10:57:58.000000Z",
            updatedAt = "2025-10-27T10:57:58.000000Z",
            typeId = 1,
            token = "qcal-3pd9-0tp4",
            status = "no_work",
            alarm = "no",
            type = DeviceTypeDto(
                id = 1,
                name = "GPS Датчик",
                createdAt = "2025-03-05T17:06:24.000000Z",
                updatedAt = "2025-03-05T17:06:24.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 1,
                        name = "кооржинаты",
                        type = "integer",
                        deviceTypeId = 1,
                        createdAt = "2025-03-05T17:06:24.000000Z",
                        updatedAt = "2025-12-07T02:09:33.000000Z",
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

        DeviceDto(
            id = 5,
            name = "домофон",
            ui = "deum-cfby-vync",
            createdAt = "2025-10-27T17:02:27.000000Z",
            updatedAt = "2025-10-27T17:02:27.000000Z",
            typeId = 5,
            token = "wt7e-9ksr-h6g5",
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
                name = "Счетчик элетроэнергии",
                createdAt = "2025-03-05T17:08:20.000000Z",
                updatedAt = "2025-03-05T17:08:20.000000Z",
                systemName = null,
                fields = listOf(
                    DeviceFieldDto(
                        id = 5,
                        name = "Т1",
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
                        name = "Т2",
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
                        name = "Т3",
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

        DeviceDto(
            id = 7,
            name = "p1",
            ui = "al9b-2s56-3lg6",
            createdAt = "2025-12-09T03:56:30.000000Z",
            updatedAt = "2025-12-09T03:56:30.000000Z",
            typeId = 10,
            token = "s8xr-m24a-w2rn",
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
        )
    )
}