package com.bugull.android.selector.photo

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType


fun openGallery(fragment: Fragment) {
    PictureSelector.create(fragment)
        .openGallery(PictureMimeType.ofImage())
        .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
        .compress(true)
        .compressQuality(30)
        .maxSelectNum(1)
        .selectionMode(PictureConfig.SINGLE)
        .isCamera(false)// 是否显示拍照按钮
        .forResult(PictureConfig.CHOOSE_REQUEST)
}

fun openCamera(fragment: Fragment) {
    if (ActivityCompat.checkSelfPermission(fragment.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == -1) {
        ActivityCompat.requestPermissions(fragment.requireActivity(), arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
    }else{
        PictureSelector.create(fragment)
            .openCamera(PictureMimeType.ofImage())
            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            .compress(true)
            .compressQuality(30)
            .maxSelectNum(1)
            .selectionMode(PictureConfig.SINGLE)
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

}