package com.example.nycschool.ui.fragment

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.nycschool.ui.MainActivity
import com.example.nycschool.R
import com.example.nycschool.databinding.FragmentNYCSchoolDetailBinding
import com.example.nycschool.model.SatScoreResponseItem
import com.example.nycschool.utils.ApiResult
import com.example.nycschool.utils.Constants.Companion.DB_REFERENCE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_ADDRESS_LINE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_CITY
import com.example.nycschool.utils.Constants.Companion.SCHOOL_EMAIL
import com.example.nycschool.utils.Constants.Companion.SCHOOL_LATITUDE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_LONGITUDE
import com.example.nycschool.utils.Constants.Companion.SCHOOL_NAME
import com.example.nycschool.utils.Constants.Companion.SCHOOL_PHONE_NUM
import com.example.nycschool.utils.Constants.Companion.SCHOOL_WEBSITE
import com.example.nycschool.viewmodel.NYCSchoolViewModel

/**
 * Detail fragment to display the selected school data like SAT average scores, address,
 * contact information
 */
class NYCSchoolDetail : Fragment() {

    lateinit var viewModel: NYCSchoolViewModel
    lateinit var binding: FragmentNYCSchoolDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_n_y_c_school_detail,
            container,
            false
        )

        return binding.root
    }

    private fun showBackButton() {
        /*binding.myToolbar.apply {
            navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }*/
        //val a = AppBarConfiguration(findNavController().graph)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        showBackButton()
        val dbn = arguments?.getString(DB_REFERENCE)

        /**
         * View model call to fetch list of sat scores and populate in detail screen
         */
        dbn?.let { viewModel.getSATScore(it) }
        viewModel.satScoreData.observe(this) { response ->
            if (response?.isSuccess() == true && !response.apiResponse.isNullOrEmpty()) {
                binding.schoolName.text = arguments?.getString(SCHOOL_NAME)
                binding.address.text = String.format(
                    getString(R.string.school_data),
                    arguments?.getString(SCHOOL_ADDRESS_LINE),
                    arguments?.getString(SCHOOL_CITY)
                )

                //openDirection()
                loadScores(response)
                loadPhoneNumber()
                binding.email.text = arguments?.getString(SCHOOL_EMAIL)
                loadWebSite()
            } else {
                updateErrorDetails()
            }
        }
    }

    /**
     * method to load the SAT score card views
     */
    private fun loadScores(response: ApiResult<List<SatScoreResponseItem>>) {
        binding.readingScore.text = response.apiResponse?.get(0)?.let {
            String.format(
                getString(R.string.readingScore),
                it.sat_critical_reading_avg_score
            )
        }
        binding.mathScore.text = response.apiResponse?.get(0)?.let {
            String.format(
                getString(R.string.mathScore),
                it.sat_math_avg_score
            )
        }
        binding.writingScore.text = response.apiResponse?.get(0)?.let {
            String.format(
                getString(R.string.writingScore),
                it.sat_writing_avg_score
            )
        }
    }

    /**
     * method to load google maps with the selected school lat-long
     */
    private fun openDirection() {
        binding.direction.setOnClickListener {
            val latitude = arguments?.getString(SCHOOL_LATITUDE)
            val longitude = arguments?.getString(SCHOOL_LONGITUDE)
            val locationName = "School Location"
            val uriLocation = Uri.parse("geo:$latitude,$longitude?q=$locationName")
            val mapIntent = Intent(Intent.ACTION_VIEW, uriLocation)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (context?.let { it1 -> mapIntent.resolveActivity(it1.packageManager) } != null) {
                context?.startActivity(mapIntent)
            }
        }
    }

    /**
     * method to load phone number, on click opens dialer screen with the selected contact number
     */
    private fun loadPhoneNumber() {
        val phone = arguments?.getString(SCHOOL_PHONE_NUM).toString().replace("-", "")
        binding.phoneNumber.apply {
            text = arguments?.getString(SCHOOL_PHONE_NUM)
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phone")
                context?.startActivity(intent)
            }
        }
    }

    /**
     * method to load website info, on click opens school website in browser
     */
    private fun loadWebSite() {
        val website = arguments?.getString(SCHOOL_WEBSITE)
        var webpage: Uri = Uri.parse(website)
        if (website?.startsWith("http://") == false && !website.startsWith("https://")) {
            webpage = Uri.parse("http://$website")
        }
        binding.website.apply {
            text = arguments?.getString(SCHOOL_WEBSITE)
            linksClickable = true
            setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                context.startActivity(intent)
            }
        }
    }

    /**
     * method to ui when no data is received from api
     */
    private fun updateErrorDetails() {
        binding.schoolName.text = getString(R.string.school_detail_error_msg)
        binding.readingScore.text = getString(R.string.empty_score)
        binding.mathScore.text = getString(R.string.empty_score)
        binding.writingScore.text = getString(R.string.empty_score)
        binding.phoneNumber.text = getString(R.string.empty_score)
        binding.email.text = getString(R.string.empty_score)
        binding.website.text = getString(R.string.empty_score)
    }
}