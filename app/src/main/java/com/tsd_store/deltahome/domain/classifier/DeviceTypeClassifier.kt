package com.tsd_store.deltahome.domain.classifier

import com.tsd_store.deltahome.domain.models.DeviceCategory

object DeviceTypeClassifier {

    fun classify(systemName: String?, name: String): DeviceCategory {
        val sn = systemName?.lowercase()?.trim()
        val n = name.lowercase().trim()

        return when {
            sn == "sensor_work_and_alarm" -> DeviceCategory.EMERGENCY_SENSOR
            sn == "sensor_water"          -> DeviceCategory.WATER_METER
            sn == "sensor_tree_data"      -> DeviceCategory.THREE_TARIFF_METER
            sn == "sensor_coordinat"      -> DeviceCategory.COORDINATE_TRACKER
            sn == "sensor_light"          -> DeviceCategory.CONTROLLED_LIGHTING
            sn == "sensor_gate"           -> DeviceCategory.GATE

            // подстраховка по name, если system_name нет
            "датчик пожар" in n || "газ" in n || "утечки" in n ->
                DeviceCategory.EMERGENCY_SENSOR

            "счётчик воды" in n || "счетчик воды" in n ->
                DeviceCategory.WATER_METER

            "3-х тариф" in n || "трёх тариф" in n || "трех тариф" in n ->
                DeviceCategory.THREE_TARIFF_METER

            "трекер координат" in n || "gps" in n || "координат" in n ->
                DeviceCategory.COORDINATE_TRACKER

            "освещение" in n || "свет" in n ->
                DeviceCategory.CONTROLLED_LIGHTING

            "задвижка" in n || "ворота" in n || "gate" in n ->
                DeviceCategory.GATE

            else -> DeviceCategory.UNKNOWN
        }
    }
}