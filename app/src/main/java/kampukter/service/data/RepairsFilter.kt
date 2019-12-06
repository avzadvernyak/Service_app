package kampukter.service.data

const val FILTER_ALL = "ALL"
const val FILTER_WITH_DATE = "WITH_DATE"
const val FILTER_WITHOUT_DATE = "WITHOUT_DATE"
const val FILTER_IN_WORK = "IN_WORK"

enum class Filter(val value: String) {
    ALL(FILTER_ALL), WITH_DATE(FILTER_WITH_DATE), WITHOUT_DATE(FILTER_WITHOUT_DATE),IN_WORK(FILTER_IN_WORK)
}

data class RepairsFilter (
    val searchString : String?,
    val filterSwitch: Filter
)