package com.ckds.movieapp.data.model.credits

data class CreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)