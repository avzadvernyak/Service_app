package kampukter.service.data

sealed class RepairState {
    object Success : RepairState()
    data class Failure (
        val reason: String
    ) : RepairState()

}