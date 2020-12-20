package com.example.connect.signUp

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.FragmentSignUpBinding
import com.example.connect.network.Result
import com.example.connect.network.User
import com.example.connect.network.UserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    // email validation pattern
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)


        /**
         * when we click on the signUp button we validate the user input using [validateUserInput()] method
         * after that we forward the input to the server using [userSignUp()] method
         */
        binding.buttonSignUp.setOnClickListener {
            validateUserInput()
            userSignUp()
        }
        // return our binding
        return binding.root
    }

    // method that get user data from editText and send to server
    private fun userSignUp() {
        //show progress bar
        binding.progressBar.visibility = View.VISIBLE

        // get input from our editTexts
        val mName = binding.signUpName.text.toString().trim()
        val mEmail = binding.signUpEmail.text.toString().trim()
        val mPassword = binding.signUpPassword.text.toString().trim()

        // match our input to our User Data Class
        val user = User(mName, mEmail, mPassword)

        // call the api service
        UserApiService.retrofitService.regUser(
            user.Name, user.Email, user.Password).enqueue(object : Callback<Result> {
            // success response gotten from server
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                //remove progress bar
                binding.progressBar.visibility = View.GONE

                /**
                 * if response body doesn't have error we save input into Shared Preference
                 * and navigate to DashBoard Fragment
                 */
                if (!response.body()?.error!!) {
                    // shared pref
                    val sharedPref = activity?.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString(getString(R.string.user_name_key), user.Name)
                            putString(getString(R.string.user_email_key), user.Email)
                            putString(getString(R.string.user_password_key), user.Password)
                            apply()
                        }
                    }
                    // navigate
                    let {
                        this@SignUpFragment.findNavController()
                            .navigate(SignUpFragmentDirections
                                .actionSignUpFragmentToDashBoardFragment(
                                    User(user.Name, user.Email, user.Password)))

                    }
                }
                // Toast to display success message
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_LONG).show()
            }

            // failure response gotten from server
            override fun onFailure(call: Call<Result>, t: Throwable) {
                //remove progress bar
                binding.progressBar.visibility = View.GONE

                // Toast to display error message
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // validate user input
    private fun validateUserInput() {
        //check if sign up name is empty
        if (TextUtils.isEmpty(binding.signUpName.text)) {
            binding.signUpName.error = getString(R.string.name_error)
        }
        /**
         * check if signUp email is empty and follows the right email pattern
         */
        if (TextUtils.isEmpty(binding.signUpEmail.text)) {
            binding.signUpEmail.error = getString(R.string.email_error_empty_text)
        } else {
            if (binding.signUpEmail.text?.toString()?.trim()
                    ?.matches(emailPattern.toRegex()) == true
            ) else {
                // remove progress bar
                binding.progressBar.visibility = View.GONE
                // show error text
                binding.signUpEmail.error = getString(R.string.email_error_text)

            }
        }

    }

}