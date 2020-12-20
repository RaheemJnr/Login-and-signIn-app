package com.example.connect.dashBoard

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.connect.R
import com.example.connect.databinding.FragmentDashBoardBinding


class DashBoardFragment : Fragment() {

    private lateinit var binding: FragmentDashBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false)

        val userArgs = DashBoardFragmentArgs.fromBundle(requireArguments()).currentUser

        // bind textView to userName
        binding.userNameTextView.text = getString(R.string.dashboard_welcomeText, userArgs.Name)

        //overFlow menu
        setHasOptionsMenu(true)

        //
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // handle overFlow menu click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> clearSharedPref()

        }


        return true
    }

    // if user click logout clear shared pref
    private fun clearSharedPref() {
        val sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        let {
            this.findNavController().navigate(R.id.action_dashBoardFragment_to_welcomeFragment)
        }
    }


}