package com.bugull.android.ext.operators

class XXX : (() -> Unit){
    override operator fun invoke() {
    }

    operator fun invoke(type:String){

    }

    operator fun invoke(type:Float,hello:String):Float{
        return 1.0f
    }

    operator fun invoke(type:Float):Int{
        return 1
    }

}