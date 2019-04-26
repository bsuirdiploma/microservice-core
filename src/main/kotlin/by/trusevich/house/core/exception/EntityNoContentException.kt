package by.trusevich.house.core.exception

class EntityNoContentException : Exception(NO_CONTENT_REASON) {

    companion object {

        const val NO_CONTENT_REASON = "There is no data with provided id exist"

        private const val serialVersionUID = -6437899378354513140L
    }
}
