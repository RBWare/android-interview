package com.png.interview.weather.ui.model

sealed class AutoCompleteViewRepresentation {
    class AutoCompleteViewRep(val data: List<String>) : AutoCompleteViewRepresentation()
    object Empty : AutoCompleteViewRepresentation()
    object Error : AutoCompleteViewRepresentation()
}