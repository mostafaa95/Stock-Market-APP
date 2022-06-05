package com.mostafa.stockmarketapp.presentation.company_info

import com.mostafa.stockmarketapp.domain.model.CompanyInfo
import com.mostafa.stockmarketapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfos: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
