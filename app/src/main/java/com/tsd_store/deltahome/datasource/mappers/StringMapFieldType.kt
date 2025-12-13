package com.tsd_store.deltahome.datasource.mappers

import com.tsd_store.deltahome.domain.models.FieldValueType

fun String.mapFieldType(): FieldValueType =
    when (this) {
        "bool", "boolean"  -> FieldValueType.BOOLEAN
        "int", "integer"   -> FieldValueType.INTEGER
        "float", "double"  -> FieldValueType.FLOAT
        "string"           -> FieldValueType.STRING
        else               -> FieldValueType.UNKNOWN
    }