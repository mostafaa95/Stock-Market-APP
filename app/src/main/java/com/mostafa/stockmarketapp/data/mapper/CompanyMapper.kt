package com.mostafa.stockmarketapp.data.mapper

import com.mostafa.stockmarketapp.data.local.CompanyListingEntity
import com.mostafa.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.mostafa.stockmarketapp.domain.model.CompanyInfo
import com.mostafa.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toComapnyIndo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}