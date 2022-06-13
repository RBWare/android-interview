package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.ui.model.AutoCompleteViewRepresentation
import javax.inject.Inject

interface CreateAutoCompleteRepFromQueryUseCase {
    suspend operator fun invoke(query: String): AutoCompleteViewRepresentation
}

class DefaultCreateAutoCompleteRepFromQueryUseCase @Inject constructor(
    private val getAutoCompleteDataUseCase: GetAutoCompleteDataUseCase
) : CreateAutoCompleteRepFromQueryUseCase {
    override suspend fun invoke(query: String): AutoCompleteViewRepresentation {
        return when (val result = getAutoCompleteDataUseCase(query)) {
            is NetworkResponse.Success -> {
                val autoCompleteList: MutableList<String> = mutableListOf()
                result.body.forEach {
                    autoCompleteList.add("${it.name}, ${it.region}, ${it.country}")
                }
                AutoCompleteViewRepresentation.AutoCompleteViewRep(autoCompleteList)
            }
            else -> {
                AutoCompleteViewRepresentation.Error
            }
        }
    }
}