package com.jimbonlemu.chicken_man

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetailCategoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }


    var description: String? = null

    companion object {
        var ARGS_NAME = "args_name"
        var ARGS_DESCRIPTION = "args_description"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_profile).setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        view.findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            val optionDialogFragment = OptionDialogFragment()
            val fragmentManager = childFragmentManager

            optionDialogFragment.show(fragmentManager, OptionDialogFragment::class.java.simpleName)
        }

        if (savedInstanceState != null) {
            description = savedInstanceState.getString(ARGS_DESCRIPTION)
        }
        if (arguments != null) {
            view.findViewById<TextView>(R.id.tv_category_name).text =
                arguments?.getString(ARGS_NAME)
            view.findViewById<TextView>(R.id.tv_category_description).text = description


        }


    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String) {
                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            }
        }



}