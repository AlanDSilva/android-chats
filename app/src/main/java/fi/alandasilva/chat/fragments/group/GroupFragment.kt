package fi.alandasilva.chat.fragments.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fi.alandasilva.chat.databinding.FragmentGroupBinding
import fi.alandasilva.chat.viewmodel.ChatViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GroupFragment : Fragment() {

    //Binding
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: ChatViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observe the current state of messages
        viewModel.groups.observe(viewLifecycleOwner) {groups ->
            binding.recyclerView.adapter = ItemAdapter(groups, findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}