package org.cnzd.najboljija.ui.photo_challenge.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_photo_challenge_add_photo.*
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.ui.photo_challenge.view_model.PhotoChallengeVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.icu.text.SimpleDateFormat
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.util.*
import android.media.MediaScannerConnection
import org.cnzd.najboljija.R
import org.cnzd.najboljija.common.fragment_dialog.ChallengeCompletedFragmentDialog
import org.cnzd.najboljija.common.utils.*

class PhotoChallengeAddPhotoFragment : BaseFragment<PhotoChallengeVM>() {

    override val viewModel by sharedViewModel<PhotoChallengeVM>()
    override fun getLayoutResources() = org.cnzd.najboljija.R.layout.fragment_photo_challenge_add_photo

    private var filePath: Uri? = null


    fun newInstance(position: Int): PhotoChallengeAddPhotoFragment {
        val args = Bundle()
        args.putInt(EXTRA_POSITION, position)
        val fragment = PhotoChallengeAddPhotoFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pathIdentifier = sharedPrefs.getString(CHILD, "")!!.toString() + "-" + sharedPrefs.getString(MENTOR, "")
        val position = arguments!!.getInt(EXTRA_POSITION)
        tvTaskText.text = viewModel.photoChallengeData.value!![position].taskText
        if (viewModel.photoChallengeData.value!![position].completed) {
            tvTakePhoto.toInvisible()
            tvUploadPhoto.toInvisible()
            viewModel.getPhoto(pathIdentifier, position).doOnNext {
                Glide.with(activity!!.baseContext)
                        .load(it)
                        .apply(RequestOptions.fitCenterTransform())
                        .into(ivTaskPhoto)
                ivTaskPhoto.apply {
                    alpha = 1F
                    setPadding(0, 0, 0, 0)
                    background = null
                }
            }.longSubscribe()
        }

        tvUploadPhoto.setOnClickListener {
            chooseImage()
        }
        tvTakePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        btnSave.setOnClickListener {
            if (filePath != null) {
                viewModel.savePhoto(filePath!!, pathIdentifier, position)
                        .doOnSuccess { context!!.showToast(resources.getString(R.string.image_saved_successfully))
                            btnSave.toInvisible()
                            tvTakePhoto.toInvisible()
                            tvUploadPhoto.toInvisible() }
                        .longSubscribe()
            }
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!!.data != null) {
            filePath = data.data
            Glide.with(activity!!.baseContext)
                    .load(filePath)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(ivTaskPhoto)
            ivTaskPhoto.apply {
                alpha = 1F
                setPadding(0, 0, 0, 0)
                background = null
            }
            btnSave.toVisible()

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras!!.get("data") as Bitmap
            Glide.with(activity!!.baseContext)
                    .load(imageBitmap)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(ivTaskPhoto)
            saveImage(imageBitmap)
            ivTaskPhoto.apply {
                alpha = 1F
                setPadding(0, 0, 0, 0)
                background = null
            }
            btnSave.toVisible()
        }
    }

    private fun saveImage(finalBitmap: Bitmap) {

        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/NajboljiJA")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "$timeStamp.jpg"

        val file = File( myDir, fileName)
        if (file.exists())
            file.delete()
        try {

            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        MediaScannerConnection.scanFile(activity, arrayOf(file.toString()), null
        ) { path, uri ->
            filePath = uri
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 71
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}