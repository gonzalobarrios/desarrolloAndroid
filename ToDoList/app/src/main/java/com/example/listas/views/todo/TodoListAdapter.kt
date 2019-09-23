package com.example.listas.views.todo

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.listas.R
import com.example.listas.data.Action
import kotlinx.android.synthetic.main.layout_todo_row.view.*
import kotlinx.android.synthetic.main.layout_todo_row_secondary.view.*

class TodoListAdapter(private var actions: List<Action>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    var data: List<Action>
        get() = actions
        set(value) {
            actions = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

//    override fun getItemViewType(position: Int): Int = data[position].actionType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoSecondaryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_todo_row_secondary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val action = data[position]

        when (holder) {
            is TodoSecondaryViewHolder -> {
                holder.actionPriority.text = "${action.priority}"
                holder.actionText.text = action.description
                holder.checkBox.setOnCheckedChangeListener { btnView, isChecked ->
                    action.isChecked = isChecked
                    holder.updateItem(isChecked)
                }
                holder.updateItem(action.isChecked)

                var ctx = holder.colorView.context
                when(action.category) {
                    Action.ActionCategory.COMPRAS -> {
                        holder.colorView.setBackgroundColor(getColor(ctx, R.color.colorCompras))
                    }
                    Action.ActionCategory.ESTUDIO -> {
                        holder.colorView.setBackgroundColor(getColor(ctx, R.color.colorEstudio))
                    }
                    Action.ActionCategory.OCIO -> {
                        holder.colorView.setBackgroundColor(getColor(ctx, R.color.colorOcio))
                    }
                    Action.ActionCategory.TRABAJO -> {
                        holder.colorView.setBackgroundColor(getColor(ctx, R.color.colorTrabajo))
                    }
                }

                when (action.priority) {
                    Action.ActionPriority.BAJA -> {
                        holder.actionPriority.text = "Prioridad Baja"
                    }
                    Action.ActionPriority.MEDIA -> {
                        holder.actionPriority.text = "Prioridad Media"
                    }
                    Action.ActionPriority.ALTA -> {
                        holder.actionPriority.text = "Prioridad Alta"
                    }
                }
            }
        }
    }

    fun getColor(ctx: Context, color: Int) : Int {
        return ContextCompat.getColor(ctx, color)
    }

    inner class TodoPrimaryViewHolder(view: View) : TodoViewHolder(view) {
        val actionText: TextView = view.todoAction
    }

    inner class TodoSecondaryViewHolder(view: View) : TodoViewHolder(view) {
        val actionText: TextView = view.actionText
        val actionPriority: TextView = view.actionPriorityText
        val checkBox: CheckBox = view.checkBox
        val colorView: View = view.colorView

        fun updateItem(isChecked: Boolean) {
            checkBox.isChecked = isChecked

            var actionTextStr = actionText.text
            var actionPriorityStr = actionPriority.text

            if(isChecked) {
                var sText = SpannableString(actionTextStr)
                sText.setSpan(StrikethroughSpan(), 0, actionTextStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                actionText.text = sText

                var sPriority = SpannableString(actionPriorityStr)
                sPriority.setSpan(StrikethroughSpan(), 0, actionPriorityStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                actionPriority.text = sPriority
            } else {
                actionText.text = actionTextStr.toString()
                actionPriority.text = actionPriorityStr.toString()
            }
        }
    }

    abstract inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)

}