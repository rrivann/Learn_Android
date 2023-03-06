package com.dicoding.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dicoding.myflexiblefragment.databinding.FragmentDetailCategoryBinding

class DetailCategoryFragment : Fragment(), View.OnClickListener {

    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailCategoryBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailCategoryBinding.bind(view)
        binding.btnProfile.setOnClickListener(this)
        binding.btnShowDialog.setOnClickListener(this)

        if (arguments != null) {
            val categoryName = arguments?.getString(EXTRA_NAME)
            val categoryDesc = arguments?.getString(EXTRA_DESCRIPTION)
            binding.tvCategoryName.text = categoryName
            binding.tvCategoryDescription.text = categoryDesc
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_profile -> {
                val mIntent = Intent(requireActivity(),ProfileActivity::class.java)
                startActivity(mIntent)
            }
            R.id.btn_show_dialog -> {
                val mOptionDialogFragment = OptionDialogFragment()

                val mFragmentManager = childFragmentManager
                mOptionDialogFragment.show(
                    mFragmentManager,
                    OptionDialogFragment::class.java.simpleName
                )
            }
        }
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String?) {
                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            }
        }
}