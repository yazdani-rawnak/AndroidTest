package com.ruet_cse_1503008.rawnak.androidtest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruet_cse_1503008.rawnak.androidtest.network.MyResponse
import com.ruet_cse_1503008.rawnak.androidtest.recyclerview.CustomAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GalleryFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class GalleryFragment2 : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_gallery2, container, false)

        editRecyclerView(root)

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GalleryFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GalleryFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun editRecyclerView(root: View) {
        // getting the recyclerview by its id
        val recyclerview = root.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        // ArrayList of class ItemsViewModel
//        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (i in 1..20) {
//            data.add(ItemsViewModel(i, "Item " + i))
//        }

        // This will pass the ArrayList to our Adapter
//        val adapter = CustomAdapter(data)
        var list = getJsonData()
        val adapter = CustomAdapter(
            list,
            requireActivity().applicationContext,
            object : CustomAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        (position + 1).toString().plus(" NO. image"),
                        Toast.LENGTH_LONG
                    ).show()
                    val clickedItem = list[position]
                    clickedItem.author = "Clicked"
//                    adapter.notifyItemChanged(position)
                    recyclerview.adapter?.notifyItemChanged(position)

                    val nextFrag = SingleImageFragment()
                    var bundle = Bundle()
                    bundle.putInt("position", position)
                    nextFrag.arguments = bundle
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit()
                }
            })

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
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