package fi.alandasilva.chat.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fi.alandasilva.chat.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    //View Binding
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Go back if there is user authenticated
        viewModel.user.observe(viewLifecycleOwner) {user ->
            if(user != null)
                findNavController().popBackStack()
        }

        binding.loginButton.setOnClickListener{
            val loginDialogFragment =
                    LoginDialogFragment.newInstance("Login")
            loginDialogFragment.show(childFragmentManager, "login")
        }
        binding.signupButton.setOnClickListener{
            val loginDialogFragment =
                    LoginDialogFragment.newInstance("Signup")
            loginDialogFragment.show(childFragmentManager, "signup")
        }
        binding.continueButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}