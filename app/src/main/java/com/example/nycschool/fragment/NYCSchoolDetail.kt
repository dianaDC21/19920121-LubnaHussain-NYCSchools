package com.example.nycschool.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.nycschool.MainActivity
import com.example.nycschool.R
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.databinding.FragmentNYCSchoolDetailBinding
import com.example.nycschool.utils.Constants.Companion.DB_REFERENCE
import com.example.nycschool.viewmodel.NYCSchoolViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NYCSchoolDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class NYCSchoolDetail : Fragment() {
    lateinit var viewModel: NYCSchoolViewModel
    lateinit var binding: FragmentNYCSchoolDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_y_c_school_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val dbn = arguments?.getString(DB_REFERENCE)
        if (dbn != null) {
            viewModel.getSATScore(dbn).observe(viewLifecycleOwner) { response ->
                updateDetailUI(response)
            }
        }
    }

    private fun updateDetailUI(response: List<SatScoreResponseItem>?) {
        binding.schoolName.text = response?.get(0)?.school_name ?: ""
        binding.readingScore.text = String.format(getString(R.string.readingScore), response?.get(0)?.sat_critical_reading_avg_score)
        binding.mathScore.text = String.format(getString(R.string.mathScore), response?.get(0)?.sat_math_avg_score)
        binding.writingScore.text = String.format(getString(R.string.writingScore), response?.get(0)?.sat_writing_avg_score)
    }
}