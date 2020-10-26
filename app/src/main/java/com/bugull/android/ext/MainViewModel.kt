package com.bugull.android.ext

import androidx.lifecycle.*
import com.bugull.android.ext.data.UserData
import com.bugull.android.ext.repository.TestRepository
import com.bugull.android.ext.ui.data.LoginDataSource
import com.bugull.android.ext.ui.data.LoginRepository
import com.bugull.android.ext.ui.ui.login.LoginViewModel
import com.bugull.android.extension.base.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: TestRepository) : BaseViewModel() {
    private val _userData: MutableLiveData<UserData> = MutableLiveData()
    private val _userData2: MutableLiveData<UserData> = MutableLiveData()

    val userData: LiveData<UserData> = _userData

    val mineLiveData: MediatorLiveData<UserData> = MediatorLiveData()


    fun login(userName: String, password: String) {
        viewModelScope.launch {
            loading {
                Loading("加载中...", true)
            }
            val result = withContext(Dispatchers.IO) {
                repository.login(userName, password)
            }
            when {
                result.isSuccess -> {
                    _userData.value = result.getOrNull()
                }
                result.isFailure -> {
                    error { ErrorMsg(result.exceptionOrNull()?.message ?: "") }
                }
            }
            loading { Loading("加载完毕...", false) }
        }

    }

    fun initMine() {
        mineLiveData.addSource(_userData) {
            mineLiveData.value = it
        }
        mineLiveData.addSource(_userData2) {
            mineLiveData.value = it
        }
    }

    fun change(name: String) {
        _userData.value = UserData(name)
    }

    fun change2(name: String) {
        _userData2.value = UserData(name)
    }

    lateinit var life:LifecycleOwner

    fun  test(){

        userData.observe(life, Observer{})
    }

    fun test01(){
        test02 {
            ""
        }
    }

    inline fun  test02(block:()->String){

    }

}


interface M

class A<T> {
    fun test(tag:String,m: T) {
        // ...
    }
}

class B {
    fun <T> test(tag:String,m: T) {
        // ...
    }
}


class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository = TestRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}