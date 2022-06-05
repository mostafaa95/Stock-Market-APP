package com.mostafa.stockmarketapp.data.mapper

import com.mostafa.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.mostafa.stockmarketapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntraDayInfoDto.toIntradayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfo(
        date = localDateTime,
        close = close
    )
}
