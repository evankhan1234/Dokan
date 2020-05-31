package com.evan.dokan.ui.home.notice

import com.evan.dokan.data.db.entities.Notice
import com.evan.dokan.data.db.entities.Shop

interface INoticeUpdateListener {
    fun onUpdate(notice: Notice)

}