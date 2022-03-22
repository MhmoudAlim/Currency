package com.mahmoudalim.data.utils

import com.mahmoudalim.data.models.Ratings

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
object RateFromCurrencyParser {

    operator fun invoke(Currency: String, rates: Ratings?): Double? {
        if (rates == null) return null
        return when (Currency) {
            "AED" -> rates.AED
            "AFN" -> rates.AFN
            "ALL" -> rates.ALL
            "AMD" -> rates.AMD
            "ANG" -> rates.ANG
            "AOA" -> rates.AOA
            "ARS" -> rates.ARS
            "AUD" -> rates.AUD
            "AWG" -> rates.AWG
            "AZN" -> rates.AZN
            "BAM" -> rates.BAM
            "BBD" -> rates.BBD
            "BDT" -> rates.BDT
            "BGN" -> rates.BGN
            "BHD" -> rates.BHD
            "BIF" -> rates.BIF
            "BMD" -> rates.BMD
            "BND" -> rates.BND
            "BOB" -> rates.BOB
            "BRL" -> rates.BRL
            "BSD" -> rates.BSD
            "BTC" -> rates.BTC
            "BTN" -> rates.BTN
            "BWP" -> rates.BWP
            "BYN" -> rates.BYN
            "BYR" -> rates.BYR
            "BZD" -> rates.BZD
            "CAD" -> rates.CAD
            "CDF" -> rates.CDF
            "CHF" -> rates.CHF
            "CLF" -> rates.CLF
            "CLP" -> rates.CLP
            "CNY" -> rates.CNY
            "COP" -> rates.COP
            "CRC" -> rates.CRC
            "CUC" -> rates.CUC
            "CUP" -> rates.CUP
            "CVE" -> rates.CVE
            "CZK" -> rates.CZK
            "DJF" -> rates.DJF
            "DKK" -> rates.DKK
            "DOP" -> rates.DOP
            "DZD" -> rates.DZD
            "EGP" -> rates.EGP
            "ERN" -> rates.ERN
            "ETB" -> rates.ETB
            "EUR" -> rates.EUR
            "FJD" -> rates.FJD
            "FKP" -> rates.FKP
            "GBP" -> rates.GBP
            "GEL" -> rates.GEL
            "GGP" -> rates.GGP
            "GHS" -> rates.GHS
            "GIP" -> rates.GIP
            "GMD" -> rates.GMD
            "GNF" -> rates.GNF
            "GTQ" -> rates.GTQ
            "GYD" -> rates.GYD
            "HKD" -> rates.HKD
            "HNL" -> rates.HNL
            "JPY" -> rates.JPY
            "KES" -> rates.KES
            "KGS" -> rates.KGS
            "KHR" -> rates.KHR
            "KMF" -> rates.KMF
            "KPW" -> rates.KPW
            "LSL" -> rates.LSL
            "LTL" -> rates.LTL
            "LVL" -> rates.LVL
            "MGA" -> rates.MGA
            "MKD" -> rates.MKD
            "MMK" -> rates.MMK
            "PAB" -> rates.PAB
            "PEN" -> rates.PEN
            "PHP" -> rates.PHP
            "PKR" -> rates.PKR
            "PYG" -> rates.PYG
            "QAR" -> rates.QAR
            "SAR" -> rates.SAR
            "SBD" -> rates.SBD
            "SCR" -> rates.SCR
            "SDG" -> rates.SDG
            "SEK" -> rates.SEK
            "SGD" -> rates.SGD
            "SHP" -> rates.SHP
            "SLL" -> rates.SLL
            "SOS" -> rates.SOS
            "SRD" -> rates.SRD
            "STD" -> rates.STD
            "USD" -> rates.USD
            "UYU" -> rates.UYU
            "UZS" -> rates.UZS
            "VEF" -> rates.VEF
            "VND" -> rates.VND
            "VUV" -> rates.VUV
            "WST" -> rates.WST
            "XAF" -> rates.XAF
            "XAG" -> rates.XAG
            "XAU" -> rates.XAU
            "XCD" -> rates.XCD
            "XDR" -> rates.XDR
            "XOF" -> rates.XOF
            "XPF" -> rates.XPF
            "YER" -> rates.YER
            "ZAR" -> rates.ZAR
            "ZMK" -> rates.ZMK
            "ZMW" -> rates.ZMW
            "ZWL" -> rates.ZWL
            else -> null
        }

    }
}