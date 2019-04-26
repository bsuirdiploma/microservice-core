package by.trusevich.house.core.service

import by.trusevich.house.core.exception.EntityNoContentException
import by.trusevich.house.core.exception.EntityNotFoundException
import by.trusevich.house.core.model.BaseEntity
import by.trusevich.house.core.repository.BaseRepository
import by.trusevich.house.core.util.merge
import org.springframework.core.GenericTypeResolver.resolveTypeArguments
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest.of
import org.springframework.data.repository.findByIdOrNull
import kotlin.reflect.KClass

@Suppress("unused")
abstract class AbstractCrudService<T : BaseEntity>(private val repository: BaseRepository<T>) {

    @Suppress("UNCHECKED_CAST")
    private val entityClass by lazy {
        resolveTypeArguments(
            javaClass,
            AbstractCrudService::class.java
        )!![0].kotlin as KClass<T>
    }

    fun create(model: T): T = repository.save(model)

    fun update(id: Long, model: T): T = repository.findByIdOrNull(id)?.let {
        repository.save(model.merge(it, entityClass))
    } ?: throw EntityNotFoundException()

    fun find(id: Long) = repository.findByIdOrNull(id) ?: throw EntityNoContentException()

    fun delete(id: Long) = repository.deleteById(id)

    fun findAll(page: Int, limit: Int): Page<T> = repository.findAll(of(page, limit))

}
