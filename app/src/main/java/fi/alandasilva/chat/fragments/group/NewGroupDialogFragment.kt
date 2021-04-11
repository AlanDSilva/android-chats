package fi.alandasilva.chat.fragments.group

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import fi.alandasilva.chat.viewmodel.ChatViewModel
import fi.alandasilva.chat.databinding.FragmentNewGroupDialogBinding
import java.io.ByteArrayOutputStream


class NewGroupDialogFragment : DialogFragment() {
    private val TAG = "LoginDialogFragment"
    private val REQUEST_IMAGE_FILE = 1

    //View Model
    private val viewModel: ChatViewModel by activityViewModels()

    private var _binding: FragmentNewGroupDialogBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewGroupDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set click listeners
        binding.imageView.setOnClickListener { choosePic() }
        binding.cancelButton.setOnClickListener{ dialog?.cancel()}
        binding.saveButton.setOnClickListener { savePic() }

    }

    private fun savePic() {
        val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()
        val name = binding.nameEdit.text.toString()

        viewModel.addGroup(data, binding.nameEdit.text.toString())
    }

    private fun choosePic() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        try {
            startActivityForResult(gallery, REQUEST_IMAGE_FILE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            binding.imageView.setImageURI(imageUri)
        }
    }
}
