package by.trusevich.house.core.repository

import by.trusevich.house.core.model.BaseEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface BaseRepository<T : BaseEntity> : CrudRepository<T, Long>, PagingAndSortingRepository<T, Long>