package by.trusevich.house.core.exception

class EntityNotFoundException : Exception(NOT_FOUND_REASON) {

    companion object {

        const val NOT_FOUND_REASON = "Requested resource was not found"

        private const val serialVersionUID = 884583546335899593L
    }
}
