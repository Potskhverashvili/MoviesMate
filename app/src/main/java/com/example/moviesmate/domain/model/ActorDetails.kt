package com.example.moviesmate.domain.model

data class ActorDetails(
    val cast: List<Cast>?,
    val id: Int?
){
    data class Cast(
        val cast_id: Int?,
        val credit_id: String?,
        val id: Int?,
        val original_name: String?,
        val profile_path: String?
    )
}