package com.bugull.android.selector.photo

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bugull.android.extension.ToastUtil
import com.bugull.android.extension.base.viewModels
import com.bugull.android.extension.logger
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia

class SelectorPhotoFragment() : Fragment() {
    private lateinit var type: SelectorPhotoFragmentFactory.Type
    private lateinit var viewModel: SelectorPhotoViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().viewModels<SelectorPhotoViewModel>()
        type = toType(arguments?.getInt(TYPE))
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == -1
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                0
            )
        } else {
            handle()
        }
    }

    private fun handle() {
        when (type) {
            SelectorPhotoFragmentFactory.Type.Album -> {
                openGallery(this)
            }
            SelectorPhotoFragmentFactory.Type.Camera -> {
                openCamera(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logger("onActivityResult $requestCode")
        when (resultCode) {
            RESULT_OK -> {
                when (requestCode) {
                    PictureConfig.CHOOSE_REQUEST -> {
                        val obtainMultipleResult: List<LocalMedia> =
                            PictureSelector.obtainMultipleResult(data)
                        viewModel.setList(obtainMultipleResult)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0->{
                if(grantResults[0]==0){
                    handle()
                }else{
                    ToastUtil.show(requireContext(),"权限打开失败")
                }
            }
        }
    }

    companion object {
        private const val TYPE = "type"
        fun newInstance(type: SelectorPhotoFragmentFactory.Type) = SelectorPhotoFragment().apply {
            arguments = bundleOf(
                TYPE to type.toInt()
            )
        }
    }
}


class SelectorPhotoViewModel : ViewModel() {
    private val _list: MutableLiveData<List<LocalMedia>> = MutableLiveData()
    var localMediaList: LiveData<List<LocalMedia>> = _list

    fun setList(list: List<LocalMedia>) {
        _list.value = list
    }
}

class SelectorPhotoFragmentFactory(private val type: Type) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            SelectorPhotoFragment::class.java.name -> SelectorPhotoFragment.newInstance(type)
            else -> super.instantiate(classLoader, className)
        }

    companion object {

    }

    sealed class Type {
        object Camera : Type()
        object Album : Type()

        companion object {

        }
    }
}

private fun SelectorPhotoFragmentFactory.Type.toInt() = when (this) {
    SelectorPhotoFragmentFactory.Type.Album -> 0
    SelectorPhotoFragmentFactory.Type.Camera -> 1
}

private fun toType(type: Int?) = when (type) {
    0 -> SelectorPhotoFragmentFactory.Type.Album
    1 -> SelectorPhotoFragmentFactory.Type.Camera
    else -> SelectorPhotoFragmentFactory.Type.Album
}


