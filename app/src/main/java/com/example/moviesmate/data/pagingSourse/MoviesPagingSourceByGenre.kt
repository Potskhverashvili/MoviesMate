package com.example.moviesmate.data.pagingSourse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesmate.core.OperationStatus
import com.example.moviesmate.data.repository.MoviesRepositoryImpl
import com.example.moviesmate.domain.model.CategoryMovies
import com.example.moviesmate.domain.usecases.FetchMoviesByGenreUseCase

class MoviesPagingSourceByGenre(
    private val fetchMoviesByGenreUseCase: FetchMoviesByGenreUseCase,
    private var genreId: Int
) : PagingSource<Int, CategoryMovies.Result>() {
    override fun getRefreshKey(state: PagingState<Int, CategoryMovies.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryMovies.Result> {
        if (genreId == -1) return LoadResult.Error(IllegalArgumentException("Invalid genre ID"))
        val currentPage = params.key ?: 1
        return when (val response = fetchMoviesByGenreUseCase.execute(genreId, currentPage)) {
            is OperationStatus.Success -> {
                val categoryMovies = response.value
                LoadResult.Page(
                    data = categoryMovies.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < categoryMovies.total_pages) currentPage + 1 else null
                )
            }
            is OperationStatus.Failure -> {
                LoadResult.Error(response.exception)
            }
        }
    }
}