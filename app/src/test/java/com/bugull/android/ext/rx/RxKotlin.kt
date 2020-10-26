package com.bugull.android.ext.rx
//破额山前碧玉流，
//骚人遥驻木兰舟。
//春风无限潇湘意，
//欲采蘋花不自由。
/**
 * 被观察者，装饰
 */
class ObservableKt<T>(/*正真的被观察对象*/private var source: ObservableOnSubscribeKt<T>) {

    // 设置观察者
    fun setObserver(downStream: ObserverKt<T>) {
        // 观察前动作
        downStream.onSubscribe()
        // 绑定观察者和被观察者
        source.setObserver(downStream)
    }

    // map 方法
    fun <R> map(block: (T) -> R): ObservableKt<R> {
        // 创建一个ObservableMap,处理变换，然后返回一个新的被观察者给下游
        val observableMap = ObservableMap(this.source, block)
        return ObservableKt(observableMap)
    }

    companion object {
        fun <T> create(emmit: ObservableOnSubscribeKt<T>): ObservableKt<T> {
            return ObservableKt(emmit)
        }
    }
}

interface ObserverKt<T> {
    fun onSubscribe()
    fun onNext(item: T)
    fun onError(e: Throwable)
    fun onComplete()
}

interface ObservableOnSubscribeKt<T> {
    fun setObserver(emmit: ObserverKt<T>)
}

/**
 * map 对被观察者进行变换map操作
 */
class ObservableMap<T, R>(
    /*上游的被观察者*/
    private val source: ObservableOnSubscribeKt<T>,
    /*变换*/
    private val map: (T) -> R
) :
    ObservableOnSubscribeKt<R> {

    override fun setObserver(emmit: ObserverKt<R>) {
        // 将上游的被观察者，设置一个新的观察者，新的观察者持有下游的对象。
        source.setObserver(MapObserver<T, R>(emmit, map))
    }

    /**
     *  map观察者
     *     持有下游的引用ObserverKt
     *     在执行ObserverKt的方法的时候，进行额外的变换操作
     */
    private class MapObserver<T, R>(val downStream: ObserverKt<R>, val map: (T) -> R) :
        ObserverKt<T> {
        override fun onSubscribe() {
            downStream.onSubscribe()
        }

        override fun onNext(item: T) {
            // 变换
            downStream.onNext(map(item))
        }

        override fun onError(e: Throwable) {
            downStream.onError(e)
        }

        override fun onComplete() {
            downStream.onComplete()
        }

    }
}

fun main() {
    RxTest.test01()
}

object RxTest {
    fun test01() {
        ObservableKt
            .create(object : ObservableOnSubscribeKt<String> {
                override fun setObserver(emmit: ObserverKt<String>) {
                    emmit.onNext("hello")
                    emmit.onNext("world")
                    emmit.onComplete()
                }
            })
            .map {
                "$it - 1"
            }
            .setObserver(object : ObserverKt<String> {
                override fun onSubscribe() {
                    println("onSubscribe ")
                }

                override fun onNext(item: String) {
                    println("onNext $item")
                }

                override fun onError(e: Throwable) {
                    println("onError $e")
                }

                override fun onComplete() {
                    println("onComplete ")
                }

            })
    }
}