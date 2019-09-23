package com.example.listas.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(var isChecked: Boolean, val description: String, val priority: ActionPriority, val category: ActionCategory) : Parcelable {

    enum class ActionCategory { TRABAJO, ESTUDIO, COMPRAS, OCIO }
    enum class ActionPriority { BAJA, MEDIA, ALTA }

}