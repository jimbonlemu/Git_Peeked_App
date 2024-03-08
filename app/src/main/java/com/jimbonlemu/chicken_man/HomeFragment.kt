package com.jimbonlemu.chicken_man

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class HomeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_category).setOnClickListener(this)
    }

    override fun onClick(action: View?) {
        if (action?.id == R.id.btn_category) {
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.frame_container,
                    CategoryFragment(),
                    CategoryFragment::class.java.simpleName
                ).addToBackStack(null).commit()
            }
        }
    }


}