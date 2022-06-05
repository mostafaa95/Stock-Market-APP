package com.mostafa.stockmarketapp.domain.repository

import com.mostafa.stockmarketapp.domain.model.CompanyInfo
import com.mostafa.stockmarketapp.domain.model.CompanyListing
import com.mostafa.stockmarketapp.domain.model.IntraDayInfo
import com.mostafa.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}