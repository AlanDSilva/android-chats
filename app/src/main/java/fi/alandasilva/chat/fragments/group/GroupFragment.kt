package fi.alandasilva.chat.fragments.group

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fi.alandasilva.chat.R
import fi.alandasilva.chat.databinding.FragmentGroupBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GroupFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    //View Binding
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        binding.fab.setOnClickListener {
            val newGroupDialogFragment =
                NewGroupDialogFragment()
            newGroupDialogFragment.show(childFragmentManager, "signup")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val action = GroupFragmentDirections.actionGroupFragmentToSettingsFragments()
                findNavController().navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}