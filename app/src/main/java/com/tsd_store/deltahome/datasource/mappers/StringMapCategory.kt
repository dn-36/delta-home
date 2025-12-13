package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.domain.models.DeviceCategory


fun String.mapCategory(
): DeviceCategory =
    when (this) {
        "sensor_work_and_alarm" -> DeviceCategory.EMERGENCY_SENSOR
        "sensor_water"          -> DeviceCategory.WATER_METER
        "sensor_tree_data"      -> DeviceCategory.THREE_TARIFF_METER
        "sensor_coordinat"      -> DeviceCategory.COORDINATE_TRACKER
        "sensor_light"          -> DeviceCategory.CONTROLLED_LIGHTING
        "sensor_gate"           -> DeviceCategory.GATE
        else                    -> DeviceCategory.UNKNOWN
    }