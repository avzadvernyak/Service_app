package kampukter.service.ui

interface ClickEventDelegate<T> {

    fun onClick(item: T)
    fun onLongClick(item: T)

}