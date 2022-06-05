package com.mostafa.stockmarketapp.domain.repository

import com.mostafa.stockmarketapp.domain.model.CompanyListing
import com.mostafa.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}