package com.dicoding.storyappsubmission.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.databinding.FragmentSettingBinding
import com.dicoding.storyappsubmission.ui.main.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutButton.setOnClickListener { handleLogout() }
        binding.languageButton.setOnClickListener { startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS)) }
        lifecycleScope.launch {
            settingViewModel.getAuthEmail().collect {
                binding.tvEmail.text = String.format(getString(R.string.email_profile), it)
            }
        }
        lifecycleScope.launch {
            settingViewModel.getAuthName().collect {
                binding.tvName.text = String.format(getString(R.string.name_profile), it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleLogout() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.logout_dialog_title))
            .setMessage(getString(R.string.logout_dialog_message))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                settingViewModel.saveAuthToken("")
                settingViewModel.saveAuthProfile("", "")
                Intent(requireContext(), MainActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.logout_message_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            .show()
    }


}