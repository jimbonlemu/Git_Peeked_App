package com.jimbonlemu.chicken_man

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimbonlemu.chicken_man.databinding.FragmentDetailCategoryBinding


class DetailCategoryFragment : Fragment() {

    private var _binding : FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container,false )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategoryName.text =  arguments?.getString(CategoryFragment.ARGS_NAME)
        binding.tvCategoryDescription.text = "Stock : ${arguments?.getLong(CategoryFragment.ARGS_STOCK)}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}