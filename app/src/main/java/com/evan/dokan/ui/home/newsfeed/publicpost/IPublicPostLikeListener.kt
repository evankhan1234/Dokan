package com.evan.dokan.ui.home.newsfeed.publicpost

interface IPublicPostLikeListener {
  fun onCount(count:Int?,type:Int,id:Int)
}