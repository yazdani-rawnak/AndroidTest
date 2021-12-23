package com.ruet_cse_1503008.rawnak.androidtest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruet_cse_1503008.rawnak.androidtest.network.MyResponse

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_single_image, container, false)

        var imageView:ImageView = root.findViewById(R.id.imageview)
        var textView: TextView = root.findViewById(R.id.textView)

        var position = arguments?.getInt("position")

        val list = getJsonData()

        Glide.with(requireActivity().applicationContext).load(list[position!!].downloadUrl)
            .placeholder(R.drawable.ic_baseline_downloading_24)
//            .error(R.drawable.ic_baseline_error_outline_24)
            .into(imageView)

        textView.text = "Author: ".plus(list[position!!].author)

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getJsonData(): List<MyResponse> {
        val PREFS_NAME = "my_shared_prefs"
        val sharedPref: SharedPreferences =
            activity?.applicationContext!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString("my_json_data", "")
        //-----------------------------------------------------
        // json to object
        //  raw string

        val jsonObjectType = object : TypeToken<List<MyResponse>>() {}.type
        val response: List<MyResponse> = Gson().fromJson(jsonString, jsonObjectType)
        println("student $response")
        //-----------------------------------------------------
        return response
    }
}