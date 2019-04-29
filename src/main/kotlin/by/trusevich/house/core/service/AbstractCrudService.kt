package by.trusevich.house.core.service

import by.trusevich.house.core.exception.EntityNoContentException
import by.trusevich.house.core.exception.EntityNotFoundException
import by.trusevich.house.core.model.BaseEntity
import by.trusevich.house.core.repository.BaseRepository
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

    open fun create(model: T): T = repository.save(model)

    fun update(modelId: Long, model: T): T = repository.findByIdOrNull(modelId)?.let {

        repository.save(model.apply {
            id = it.id
            created = it.created
            updatedBy = it.updatedBy
            updated = it.updated
        })
    } ?: throw EntityNotFoundException()

    fun find(id: Long) = repository.findByIdOrNull(id) ?: throw EntityNoContentException()

    open fun delete(id: Long) = repository.deleteById(id)

    fun findAll(page: Int, limit: Int): Page<T> = repository.findAll(of(page, limit))

}
