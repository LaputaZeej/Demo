package com.bugull.android.extension.base

import androidx.lifecycle.*

data class ErrorMsg(val msg: String) : ViewData()
data class Loading(val msg: String = "", val loading: Boolean) : ViewData()

abstract class ViewData

abstract class BaseViewModel() : ViewModel() {

    private val _error: MutableLiveData<ErrorMsg> by lazy { MutableLiveData<ErrorMsg>() }
    val error: LiveData<ErrorMsg> = _error

    private val _loading: MutableLiveData<Loading> by lazy { MutableLiveData<Loading>() }
    val loading: LiveData<Loading> = _loading

    protected fun error(errorMsg: () -> ErrorMsg) {
        _error.value = errorMsg()
    }

    protected fun error(msg: String) {
        _error.value = ErrorMsg(msg)
    }

    protected fun loading(loading: () -> Loading) {
        _loading.value = loading()
    }

    protected fun loading(loading: Boolean, msg: String = "") {
        _loading.value = Loading(msg, loading)
    }

    companion object {

    }


}

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModels(factory: ViewModelProvider.Factory? = null) =
    factory?.let {
        ViewModelProvider(this, it).get(T::class.java)
    } ?: ViewModelProvider(this).get(T::class.java)

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModelsFactory(factory: () -> ViewModelProvider.Factory) =
    viewModels<T>(factory())

//inline fun <reified T : BaseViewModel> FragmentActivity.initViewModel():T = viewModels<T>().apply {
//    error.observe(this@initViewModel, Observer {
//        // todo toast
//        toast("error:${it}")
//    })
//
//    loading.observe(this@initViewModel, Observer {
//        it?.run {
//            // todo loading
//        }
//    })
//}

