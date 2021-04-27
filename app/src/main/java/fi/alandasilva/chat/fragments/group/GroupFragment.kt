package fi.alandasilva.chat.fragments.group

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import fi.alandasilva.chat.R
import fi.alandasilva.chat.databinding.FragmentGroupBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GroupFragment : Fragment(), SearchView.OnQueryTextListener{

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

        // On checked filters
        binding.chipGroup.setOnCheckedChangeListener { group, checkedID ->
            getView()?.findViewById<Chip>(checkedID)?.text = "Hello"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_layout, menu)

        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val groups = viewModel.searchDatabase(query)
        binding.recyclerView.adapter = ItemAdapter(groups, findNavController())
    }


}