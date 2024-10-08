// this file is generated by auto.please do not modify!!!
package com.niu.jetpack.plugin.runtime

import com.niu.jetpack.plugin.runtime.NavDestination.NavType.Activity
import com.niu.jetpack.plugin.runtime.NavDestination.NavType.Dialog
import com.niu.jetpack.plugin.runtime.NavDestination.NavType.Fragment
import com.niu.jetpack.plugin.runtime.NavDestination.NavType.None
import kotlin.collections.ArrayList
import kotlin.collections.List

object NavRegistry {
    private val navList: ArrayList<NavData> = ArrayList<NavData>()


    init {
        navList.add(NavData("tags_fragment","com.niu.jetpack_android_online.navigation.TagsFragment",false,Fragment))
                navList.add(NavData("user_fragment","com.niu.jetpack_android_online.navigation.UserFragment",false,Fragment))
                navList.add(NavData("home_fragment","com.niu.jetpack_android_online.pages.home.HomeFragment",true,Fragment))
                navList.add(NavData("activity_capture","com.niu.jetpack_android_online.pages.publish.CaptureActivity",false,Activity))
                navList.add(NavData("category_fragment","com.niu.jetpack_android_online.pages.category.CategoryFragment",false,Fragment))
                navList.add(NavData("tags_fragment","com.niu.jetpack_android_online.navigation.TagsFragment",false,Fragment))
                navList.add(NavData("user_fragment","com.niu.jetpack_android_online.navigation.UserFragment",false,Fragment))
                navList.add(NavData("home_fragment","com.niu.jetpack_android_online.pages.home.HomeFragment",true,Fragment))
                navList.add(NavData("activity_capture","com.niu.jetpack_android_online.pages.publish.CaptureActivity",false,Activity))
                navList.add(NavData("category_fragment","com.niu.jetpack_android_online.pages.category.CategoryFragment",false,Fragment))

    }

    fun get(): List<NavData> {
        val list = ArrayList<NavData>()
                 list.addAll(navList)
                 return list

    }
}
