package com.mostafa.stockmarketapp.data.repository

import com.mostafa.stockmarketapp.data.csv.CSVParser
import com.mostafa.stockmarketapp.data.local.StockDatabase
import com.mostafa.stockmarketapp.data.mapper.toComapnyInfo
import com.mostafa.stockmarketapp.data.mapper.toCompanyListing
import com.mostafa.stockmarketapp.data.mapper.toCompanyListingEntity
import com.mostafa.stockmarketapp.data.remote.StockApi
import com.mostafa.stockmarketapp.domain.model.CompanyInfo
import com.mostafa.stockmarketapp.domain.model.CompanyListing
import com.mostafa.stockmarketapp.domain.model.IntraDayInfo
import com.mostafa.stockmarketapp.domain.repository.StockRepository
import com.mostafa.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntraDayInfo>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't Load Intraday Info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't Load Intraday Info"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toComapnyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't Load Intraday Info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't Load Intraday Info"
            )
        }
    }


}