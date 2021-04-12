package fi.alandasilva.chat.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import fi.alandasilva.chat.databinding.FragmentLoginDialogBinding

private const val ARG_DIALOG_TYPE = "dialog_type"

class LoginDialogFragment : DialogFragment() {
    private val TAG = "LoginDialogFragment"

    private var param: String?= null
    //View Model
    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            param = it.getString(ARG_DIALOG_TYPE)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = param
        binding.loginButton.text = param

        //Set click listeners
        binding.cancelButton.setOnClickListener{
            dialog?.cancel()
        }
        binding.loginButton.setOnClickListener{
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            if(param == "Login") {
                viewModel.login(email, password)
            } else {
                viewModel.signup(email, password)
            }
            dialog?.cancel()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
                LoginDialogFragment().apply{
                    arguments = Bundle().apply {
                        putString(ARG_DIALOG_TYPE, param)
                    }
                }
    }



}
