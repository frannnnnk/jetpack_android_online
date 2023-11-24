package com.niu.jetpack_android_online.pages.publish

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.niu.jetpack.plugin.runtime.NavDestination
import com.niu.jetpack_android_online.R
import com.niu.jetpack_android_online.base.BaseActivity
import com.niu.jetpack_android_online.databinding.ActivityLayoutCaptureBinding
import java.lang.Exception
import java.lang.IllegalStateException

@SuppressLint("RestrictedApi")
@NavDestination(route = "activity_capture", type = NavDestination.NavType.Activity)
class CaptureActivity : BaseActivity<ActivityLayoutCaptureBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            val deniedPermissions = mutableListOf<String>()
            for (i in PERMISSIONS.indices) {
                val permission = permissions[i]
                val result = grantResults[i]
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission)
                }
            }

            if (deniedPermissions.isEmpty()) {
                //全部授权成功
                startCamera()
            } else {
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.capture_permission_message))
                    .setNegativeButton(getString(R.string.capture_permission_no)) { dialog, _ ->
                        dialog.dismiss()
                        this@CaptureActivity.finish()
                    }.setPositiveButton(getString(R.string.capture_permission_ok)) { dialog, _ ->
                        //申请被拒绝的权限
                        ActivityCompat.requestPermissions(
                            this@CaptureActivity,
                            deniedPermissions.toTypedArray(),
                            PERMISSION_CODE
                        )
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }

    //开启相机以及预览能力
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            // 创建ProcessCameraProvider实例
            // 用于将相机的生命周期绑定到生命周期所有者
            // 这消除了打开和关闭相机的任务，因为CameraX具有生命周期感知能力
            val cameraProvider = cameraProviderFuture.get()
            val cameraSelector = when {
                cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> CameraSelector.DEFAULT_FRONT_CAMERA
                else -> throw IllegalStateException("Back and Front camera are unavailable")
            }
            val displayRotation = binding.previewView.display.rotation

            // preview use case
            val preview = Preview.Builder()
                .setCameraSelector(cameraSelector)
                .setTargetRotation(displayRotation)
                .build().also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this,cameraSelector,preview)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    companion object {
        private const val TAG = "CaptureActivity"

        //动态权限申请
        private val PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) Manifest.permission.WRITE_EXTERNAL_STORAGE else null
        ).filterNotNull().toTypedArray()

        private const val REQ_CAPTURE = 10001
        private const val PERMISSION_CODE = 1000
    }
}