package com.mostafa.stockmarketapp.di

import com.mostafa.stockmarketapp.data.csv.CSVParser
import com.mostafa.stockmarketapp.data.csv.CompanyListingsParser
import com.mostafa.stockmarketapp.data.repository.StockRepositoryImpl
import com.mostafa.stockmarketapp.domain.model.CompanyListing
import com.mostafa.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}