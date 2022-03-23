package com.mahmoudalim.data.models

enum class PopularRates(val value: String) {
    AED("AED"),
    USD("USD"),
    EUR("EUR"),
    GGP("GGP"),
    EGP("EGP"),
    SAR("SAR"),
    BTC("BTC"),
    RSD("RSD"),
    CUC("CUC"),
    QAR("QAR");

    companion object {
        fun fromType(value: String?): PopularRates {
            if (value == null) return AED
            return values()
                .firstOrNull { it.value == value } ?: AED
        }
    }
}

