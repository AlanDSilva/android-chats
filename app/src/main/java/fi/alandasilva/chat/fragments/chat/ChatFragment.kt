package fi.alandasilva.chat.fragments.chat

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import fi.alandasilva.chat.databinding.FragmentChatBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatFragment : Fragment() {
    private val TAG = "ChatFragment"

    //Navigation arguments
    val args: ChatFragmentArgs by navArgs()

    //Binding
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: ChatViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.addUser(args.id)
    }

    override fun onStop() {
        super.onStop()
        viewModel.removeUser(args.id)


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setMessagesRef(args.id)
        viewModel.messages.observe(viewLifecycleOwner) {messages ->
            binding.recyclerView.adapter = ItemAdapter(messages)
        }

        binding.editText.setOnKeyListener {v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.addMessage(binding.editText.text.toString())

                binding.editText.setText("")
                v.hideKeyboard()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}