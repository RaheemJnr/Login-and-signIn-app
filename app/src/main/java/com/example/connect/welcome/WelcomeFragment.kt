package com.example.connect.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false)

//        //
//        val sharedPreferences = activity?.getSharedPreferences(
//            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
//        val isLoggedIn = sharedPreferences?.getString(getString(R.string.user_email_key), null).toBoolean()
//        if (isLoggedIn){
//            let {
//                this.findNavController().navigate(R.id.action_welcomeFragment_to_dashBoardFragment2)
//            }
//        }


        //navigate to signIn fragment
        binding.signInButton.setOnClickListener {
            this.findNavController()
                .navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment())
        }
        //navigate to signUp fragment
        binding.signUpButton.setOnClickListener {
            this.findNavController()
                .navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment())
        }

        //
        return binding.root
    }

}