package by.trusevich.house.core.exception

class MalformedRequestDataException : Exception(MALFORMED_REASON) {

    companion object {

        const val MALFORMED_REASON =
            "Request contains no message or the message provided can not be transformed to any known internal type"

        private const val serialVersionUID = 9166118024356415247L
    }

}
