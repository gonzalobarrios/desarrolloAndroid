package com.example.listas.views.add

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.listas.R
import com.example.listas.data.Action
import com.example.listas.extensions.textString
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(input: Action)
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton.setOnClickListener {
            onAddButtonPressed()
        }
    }

    private fun onAddButtonPressed() {
        if (radio_categories.checkedRadioButtonId == -1) {
            Toast.makeText(activity!!, "Debe seleccionar una categoría.", Toast.LENGTH_LONG).show()
        } else if (priorities.checkedRadioButtonId == -1) {
            Toast.makeText(activity!!, "Debe seleccionar una prioridad.", Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(action_description_text.text.trim())) {
            Toast.makeText(activity!!, "Debe ingresar una tarea.", Toast.LENGTH_LONG).show()
        } else {
            //Category
            var priority = checkPriority()
            var category = checkCategory()
            var descripcion: String = ""

            if (!action_description_text.textString().isEmpty()) {
                descripcion = action_description_text.textString()

            }

            var action: Action = Action(
                isChecked = false,
                description = descripcion,
                priority = priority,
                category = category
            )
            listener?.onFragmentInteraction(action)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    public fun checkPriority(): Action.ActionPriority {
        if (radio_low.isChecked) {
            return Action.ActionPriority.BAJA
        } else if (radio_medium.isChecked) {
            return Action.ActionPriority.MEDIA
        } else {
            return Action.ActionPriority.ALTA
        }
    }

    public fun checkCategory(): Action.ActionCategory {
        if (radio_trabajo.isChecked) {
            return Action.ActionCategory.TRABAJO
        } else if (radio_estudio.isChecked) {
            return Action.ActionCategory.ESTUDIO
        } else if (radio_ocio.isChecked) {
            return Action.ActionCategory.OCIO
        } else {
            return Action.ActionCategory.COMPRAS
        }
    }

}
