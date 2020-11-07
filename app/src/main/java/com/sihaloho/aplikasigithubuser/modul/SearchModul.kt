package com.sihaloho.aplikasigithubuser.modul

data class SearchModul(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserModul>
)
