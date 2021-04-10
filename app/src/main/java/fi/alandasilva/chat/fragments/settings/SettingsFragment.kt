package fi.alandasilva.chat.fragments.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fi.alandasilva.chat.R
import fi.alandasilva.chat.databinding.FragmentSettingsBinding
import fi.alandasilva.chat.viewmodel.ChatViewModel

class SettingsFragment : Fragment() {
    private val TAG = "SettingsFragment"

    //View Binding
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: ChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) {user ->
            if(user != null){
                Log.d(TAG, "There is a user!")
            } else {
                val action = SettingsFragmentDirections.actionSettingsFragment2ToLogin()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}