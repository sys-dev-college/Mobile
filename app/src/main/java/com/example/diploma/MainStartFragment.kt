package com.example.diploma

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment


class MainStartFragment : Fragment() {

    private lateinit var fragmentContainer: FrameLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        fragmentContainer = view.findViewById(R.id.fragment_content_container)

        val changeElementsButton = view.findViewById<Button>(R.id.btn_continue)
        changeElementsButton.setOnClickListener {
            val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_content_container)
            val nextFragment: Fragment = when (currentFragment) {
                is MainStartFragment -> FragmentStart()
                is FragmentStart -> FragmentRegistration()
                else -> MainStartFragment()
            }

            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_content_container, nextFragment)
            transaction.commit()
        }

        return view
    }
}