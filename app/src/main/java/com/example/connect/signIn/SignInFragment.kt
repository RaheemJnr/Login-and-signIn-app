package com.example.connect.signIn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.FragmentSignInBinding
import com.example.connect.network.Result
import com.example.connect.network.User
import com.example.connect.network.UserApiService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)


        binding.signInButton.setOnClickListener {
            userSignIn()
        }

        return binding.root
    }

    private fun userSignIn() {
        binding.progressBar.visibility = View.VISIBLE

        val email: String = binding.signInEmail.text.toString().trim()
        val password: String = binding.signInPassword.text.toString().trim()

        val sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val defaultUser = "User"
        val name = sharedPreferences?.getString(getString(R.string.user_name_key), defaultUser)!!
        //
        val user = User(name, email, password)
        //
        UserApiService.retrofitService.userLogin(email, password)
            ?.enqueue(object : Callback<Result?> {
                override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                    //remove progress bar
                    binding.progressBar.visibility = View.GONE
                    // save user data to
                    if (!response.body()?.error!!) {
                        //shared pref
                        val sharedPref = activity?.getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                        with(sharedPref?.edit()) {
                            this?.putString(getString(R.string.user_email_key), user.Email)
                            this?.putString(getString(R.string.user_password_key), user.Password)
                            this?.apply()
                        }
                        // navigate
                        with(this@SignInFragment) {
                            findNavController()
                                .navigate(SignInFragmentDirections
                                    .actionSignInFragmentToDashBoardFragment(
                                        User(user.Name, user.Email, user.Password)))
                        }
                    } else {
                        Snackbar.make(view!!, "Invalid Email or PassWord", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Result?>, t: Throwable) {
                    // remove progress bar
                    binding.progressBar.visibility = View.GONE
                    // display error message in a snackBar
                    t.message?.let { Snackbar.make(view!!, it, Snackbar.LENGTH_LONG).show() }


                }

            })

    }


}
