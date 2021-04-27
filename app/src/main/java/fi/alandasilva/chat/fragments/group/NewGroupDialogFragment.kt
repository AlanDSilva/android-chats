package fi.alandasilva.chat.fragments.group

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import fi.alandasilva.chat.R
import fi.alandasilva.chat.databinding.FragmentNewGroupDialogBinding
import java.io.ByteArrayOutputStream


class NewGroupDialogFragment : DialogFragment() {
    private val TAG = "NewGroupDialogFragment"
    private val REQUEST_IMAGE_FILE = 1

    //View Model
    private val viewModel: GroupViewModel by viewModels()

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

        setupArrayAdapter()
        //Set click listeners
        binding.imageView.setOnClickListener { choosePic() }
        binding.cancelButton.setOnClickListener{ dialog?.cancel()}
        binding.saveButton.setOnClickListener { saveGroup() }

    }

    private fun setupArrayAdapter() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.categoriesSpinner.adapter = adapter
        }
    }

    private fun saveGroup() {
        val bitmap = (binding.imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()
        val name = binding.nameEdit.text.toString()

        viewModel.addGroup(data, binding.nameEdit.text.toString(), binding.categoriesSpinner.selectedItem as String)
        dialog?.cancel()
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
