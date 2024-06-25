package com.example.diploma.trainer_part.users_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentUsersListBinding
import com.example.diploma.trainer_part.user_change.UserSetChanges
import com.example.diploma.trainer_part.user_change.model.UserData
import com.example.diploma.user_screen.model.TrainerUser
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class UsersListFragment : BaseFragment(), UsersListAdapter.OnButtonClick {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersListAdapter()
        adapter.onCLick = this
        binding.fragmentUsersListRv.adapter = adapter
        (requireActivity() as MainActivity).makeNavigationVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            val users = RetrofitClient.getUsersList(userToken)
            adapter.items = users
        }
    }

    override fun invoke(item: TrainerUser) {
        val fragment = UserSetChanges()
        fragment.arguments = bundleOf(Pair(UserSetChanges.USER_DATA, UserData(item.id, item.name, item.email, item.telegramUrl)))
        navigateTo(fragment)
    }
}