package by.trusevich.house.core.exception

class UnauthorizedException : Exception(UNAUTHORIZED_REASON) {

    companion object {

        const val UNAUTHORIZED_REASON = "You are not authorized to view the resource"

        private const val serialVersionUID = -4623155798041769848L
    }
}
