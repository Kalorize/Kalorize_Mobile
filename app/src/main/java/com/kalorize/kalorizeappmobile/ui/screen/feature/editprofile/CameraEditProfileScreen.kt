package com.kalorize.kalorizeappmobile.ui.screen.feature.editprofile


import com.kalorize.kalorizeappmobile.R
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.camera.core.*
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionSelector.ALLOWED_RESOLUTIONS_SLOW
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.theme.Orange0
import com.kalorize.kalorizeappmobile.util.getPath
import com.kalorize.kalorizeappmobile.util.reduceFileImage
import com.kalorize.kalorizeappmobile.util.rotateFile
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.lang.Float.max
import java.lang.Float.min
import java.text.SimpleDateFormat
import java.util.*


private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val PHOTO_TYPE = "image/jpeg"
private const val MIN_SCALE_RATIO = 1.0f


@Composable
fun CameraEditProfileScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val providerState = produceState(
        initialValue = null as ProcessCameraProvider?,
        key1 = context,
        key2 = lifecycleOwner,
        producer = {
            value = ProcessCameraProvider.getInstance(context).await()
        }
    )
    val isLoading = remember {
        mutableStateOf(false)
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val userPreferences = UserPreference(context)
    val user = userPreferences.getUser()
    val getMeResponse = remember {
        mutableStateOf(RecommendationResponse(null, ""))
    }

//    LaunchedEffect(key1 = getMeResponse.value, block = {
//        Log.d("Check response ", getMeResponse.value.toString())
//        isLoading.value = false
//        userPreferences.setUser(
//            LoginData(
//                token = user.token,
//                user = LoginUser(
//                    password = user.user.password,
//                    id = user.user.id,
//                    email = user.user.email,
//                    name = user.user.name,
//                    gender = user.user.gender,
//                    picture = getMeResponse.value.data!!.user.picture,
//                    weight = user.user.weight,
//                    age = user.user.age,
//                    height = user.user.height,
//                    activity = user.user.activity,
//                    target = user.user.target
//                )
//            )
//        )
//        navController.popBackStack()
//
//    })
    Box {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { innerPadding ->
            val provider = providerState.value
            if (provider != null) {
                if (provider.availableCameraInfos.isEmpty()) {
                    Text(
                        text = "Kamera tidak ditemukan",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .wrapContentSize()
                    )
                } else {
                    CameraContent(
                        provider = provider,
                        modifier = Modifier.padding(innerPadding),
                        onImageSaveSuccess = { uri ->
                            isLoading.value = true
                            Log.i("uri path", uri!!.toString())
                            if (uri != null) {
                                var imagePath = getPath(context, uri!!)
                                Log.i("file path", imagePath.toString())
                                val fileFromString = File(imagePath)
                                val file = reduceFileImage(fileFromString)
                                val rotateImage = rotateFile(file, false)
                                Log.i("file path file", rotateImage.toString())
                                val requestImageFile =
                                    fileFromString.asRequestBody("image/jpeg".toMediaType())
                                val imageMultipart: MultipartBody.Part =
                                    MultipartBody.Part.createFormData(
                                        "picture",
                                        fileFromString.name,
                                        requestImageFile
                                    )
                                viewModel.homeViewModel.uploadPhotoProfile(
                                    user.token,
                                    imageMultipart
                                )
                                viewModel.homeViewModel.uploadPhotoProfile.observe(lifecycleOwner) {
                                    getMeResponse.value = it
                                }
                                userPreferences.setUser(
                                    LoginData(
                                        token = user.token,
                                        user = LoginUser(
                                            password = user.user.password,
                                            id = user.user.id,
                                            email = user.user.email,
                                            name = user.user.name,
                                            gender = user.user.gender,
                                            picture = getMeResponse.value.data!!.user.picture, //error
                                            weight = user.user.weight,
                                            age = user.user.age,
                                            height = user.user.height,
                                            activity = user.user.activity,
                                            target = user.user.target
                                        )
                                    )
                                )
                                navController.popBackStack()

                            }
                            val message = if (uri != null) {
                                context.getString(R.string.message_success_upload_image)

                            } else {
                                context.getString(R.string.message_failed_upload_image)
                            }
                            scope.launch { snackBarHostState.showSnackbar(message) }
                        },
                        onImageSaveFailed = {
                            Log.e("CameraScreen", it.message, it)
                            scope.launch {
                                snackBarHostState.showSnackbar(context.getString(R.string.message_failed_upload_image))
                            }
                        },
                        onError = {
                            Log.e("CameraScreen", it.cause?.message, it.cause)
                            when (it.type) {
                                CameraState.ErrorType.RECOVERABLE -> Unit
                                CameraState.ErrorType.CRITICAL -> {
                                    val message = context.getString(
                                        if (it.code == CameraState.ERROR_DO_NOT_DISTURB_MODE_ENABLED) {
                                            R.string.message_error_do_not_disturb_mode_enabled
                                        } else {
                                            R.string.message_failed_binding_camera
                                        }
                                    )
                                    scope.launch { snackBarHostState.showSnackbar(message) }
                                }
                            }
                        }
                    )
                }
            } else {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }

        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                strokeWidth = 6.dp,
                color = Orange0
            )
        }

    }
}


@Composable
private fun CameraContent(
    provider: ProcessCameraProvider,
    modifier: Modifier = Modifier,
    onImageSaveSuccess: (Uri?) -> Unit,
    onImageSaveFailed: (Throwable) -> Unit,
    onError: (CameraState.StateError) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentLensFacing: CameraSelector by remember {
        mutableStateOf(
            if (provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                CameraSelector.DEFAULT_BACK_CAMERA
            } else {
                CameraSelector.DEFAULT_FRONT_CAMERA
            }
        )
    }
    val canFlipCamera = provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) &&
            provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    var currentAspectRatio: Int by remember { mutableStateOf(AspectRatio.RATIO_16_9) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember(currentAspectRatio) {
        ImageCapture.Builder()
            .setResolutionSelector(
                ResolutionSelector.Builder()
                    .setAspectRatioStrategy(
                        AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
                    )
                    .setAllowedResolutionMode(ALLOWED_RESOLUTIONS_SLOW)
                    .build()
            )
            .build()
    }
    var bindingCamera: Camera? by remember {
        mutableStateOf(null)
    }
    var isErrorCameraBinding: Boolean by remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val cameraState = bindingCamera?.cameraInfo?.cameraState?.observeAsState()

    val orientationEventListener = object : OrientationEventListener(context) {
        override fun onOrientationChanged(orientation: Int) {
            if (orientation == ORIENTATION_UNKNOWN) return
            val rotation = when (orientation) {
                in 45 until 135 -> Surface.ROTATION_270
                in 135 until 225 -> Surface.ROTATION_180
                in 225 until 315 -> Surface.ROTATION_90
                else -> Surface.ROTATION_0
            }
            imageCapture.targetRotation = rotation
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                orientationEventListener.enable()
            }
            if (event == Lifecycle.Event.ON_STOP) {
                orientationEventListener.disable()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(currentLensFacing, currentAspectRatio) {
        try {
            provider.unbindAll()
            val camera = provider.bindToLifecycle(
                lifecycleOwner,
                currentLensFacing,
                imageCapture,
                Preview.Builder()
                    .setResolutionSelector(
                        ResolutionSelector.Builder()
                            .setAspectRatioStrategy(
                                AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
                            )
                            .build()
                    )
                    .build()
                    .apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }
            )

            val gestureDetector = GestureDetector(
                context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDown(e: MotionEvent): Boolean = true

                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        val meteringPointFactory = previewView.meteringPointFactory
                        val focusPoint = meteringPointFactory.createPoint(e.x, e.y)
                        focus(camera, focusPoint)
                        return true
                    }
                }
            )

            val scaleGestureDetector = ScaleGestureDetector(
                context,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        scale(camera, detector.scaleFactor)
                        return true
                    }
                }
            )

            previewView.setOnTouchListener { view, event ->
                var didConsume = scaleGestureDetector.onTouchEvent(event)
                if (!scaleGestureDetector.isInProgress) {
                    didConsume = gestureDetector.onTouchEvent(event)
                }
                if (event.action == MotionEvent.ACTION_UP) {
                    view.performClick()
                }
                didConsume
            }

            bindingCamera = camera
        } catch (e: Exception) {
            Log.e("CameraContent", e.message, e)
            isErrorCameraBinding = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(currentAspectRatio.mapToFloat()),
            factory = {
                previewView.also {
                    it.clipToOutline = true
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraUiController(
                enabled = cameraState?.value?.type == CameraState.Type.OPEN,
                canFlipCamera = canFlipCamera,
                onClickShutter = {
                    isLoading.value = true
                    takePhoto(
                        context,
                        imageCapture,
                        onImageSaved = {
                            onImageSaveSuccess(it)
                            isLoading.value = false
                        },
                        onError = {
                            isLoading.value = false
                            onImageSaveFailed(it)
                        }
                    )
                },
                onClickFlipCamera = {
                    val camera = bindingCamera ?: return@CameraUiController
                    currentLensFacing =
                        if (camera.cameraInfo.lensFacing == CameraSelector.LENS_FACING_BACK) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                }
            )
        }
        if (isLoading.value) {
            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
        cameraState?.value?.let {
            when (it.type) {
                CameraState.Type.PENDING_OPEN -> {
                    isLoading.value = false
                    Text(
                        text = "Aplikasi lain sedang menggunakan kamera, harap tutup aplikasi tersebut",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .wrapContentSize()
                    )
                }

                CameraState.Type.OPEN -> {
                    isLoading.value = false
                }

                else -> {
                    isLoading.value = true
                }
            }

            it.error?.let { stateError ->
                onError(stateError)
            }
        }

        when {
            isErrorCameraBinding -> {
                Text(
                    text = "Gagal memulai kamera. Silakan coba mulai ulang perangkat Anda",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize()
                )
            }

            isLoading.value -> {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }

    }
}


@Composable
private fun CameraUiController(
    enabled: Boolean,
    onClickShutter: () -> Unit,
    modifier: Modifier = Modifier,
    canFlipCamera: Boolean,
    onClickFlipCamera: () -> Unit,
    ) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_shutter),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .clickable(
                    enabled = enabled,
                    onClick = onClickShutter
                )
                .align(Alignment.BottomCenter)
                .padding(32.dp)
                .size(70.dp)
        )

        if (canFlipCamera) {
            IconButton(
                onClick = onClickFlipCamera,
                enabled = enabled,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 52.dp)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_flip_camera),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

private fun @receiver:AspectRatio.Ratio Int.mapToFloat(): Float {
    return when (this) {
        AspectRatio.RATIO_16_9 -> 9f / 16f
        else -> 3f / 4f
    }
}

private fun focus(camera: Camera, meteringPoint: MeteringPoint) {
    val meteringAction = FocusMeteringAction.Builder(meteringPoint).build()
    camera.cameraControl.startFocusAndMetering(meteringAction)
}

private fun scale(camera: Camera, scaleFactor: Float) {
    val zoomState = camera.cameraInfo.zoomState.value ?: return
    val currentZoomRatio = zoomState.zoomRatio
    camera.cameraControl.setZoomRatio(
        min(
            max(
                currentZoomRatio * speedUpZoomBy2X(scaleFactor),
                MIN_SCALE_RATIO
            ),
            zoomState.maxZoomRatio
        )
    )
}

private fun speedUpZoomBy2X(scaleFactor: Float): Float {
    return if (scaleFactor > 1f) {
        1.0f + (scaleFactor - 1.0f) * 2
    } else {
        1.0f - (1.0f - scaleFactor) * 2
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageSaved: (Uri?) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val fileName = SimpleDateFormat(FILENAME, Locale.US).format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, PHOTO_TYPE)
    }

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            },
            contentValues
        )
        .build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    val contentUri = outputFileResults.savedUri ?: return
                    var filePath: String? = null

                    context.contentResolver.query(
                        contentUri,
                        arrayOf(MediaStore.MediaColumns.DATA),
                        null,
                        null,
                        null
                    )?.use {
                        if (it.moveToFirst()) {
                            filePath = it.getString(0)
                        }
                    }
                    filePath?.let {
                        MediaScannerConnection.scanFile(
                            context,
                            arrayOf(filePath),
                            arrayOf("image/jpeg"),
                            null
                        )
                    }
                }
                onImageSaved(outputFileResults.savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}