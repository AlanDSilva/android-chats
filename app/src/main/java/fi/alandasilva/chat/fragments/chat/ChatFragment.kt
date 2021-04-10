package fi.alandasilva.chat.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import fi.alandasilva.chat.databinding.FragmentChatBinding
import fi.alandasilva.chat.viewmodel.ChatViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatFragment : Fragment() {

    //Navigation arguments
    val args: ChatFragmentArgs by navArgs()

    //Binding
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: ChatViewModel by activityViewModels()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}