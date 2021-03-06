package com.isaiahvonrundstedt.fokus.features.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import com.isaiahvonrundstedt.fokus.components.extensions.android.getCompoundDrawableAtStart
import com.isaiahvonrundstedt.fokus.components.extensions.android.setCompoundDrawableAtStart
import com.isaiahvonrundstedt.fokus.components.interfaces.Swipeable
import com.isaiahvonrundstedt.fokus.databinding.LayoutItemEventBinding
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseAdapter
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseEditor

class EventAdapter(private var actionListener: ActionListener)
    : BaseAdapter<EventPackage, EventAdapter.EventViewHolder>(EventPackage.DIFF_CALLBACK), Swipeable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = LayoutItemEventBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return EventViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onSwipe(position: Int, direction: Int) {
        if (direction == ItemTouchHelper.START)
            actionListener.onActionPerformed(getItem(position), ActionListener.Action.DELETE,
                null)
    }

    inner class EventViewHolder(itemView: View): BaseViewHolder(itemView) {

        private val binding = LayoutItemEventBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is EventPackage) {
                with(t.event) {
                    binding.root.transitionName = BaseEditor.TRANSITION_ELEMENT_ROOT + eventID

                    binding.locationView.text = location
                    binding.nameView.text = name
                    binding.timeView.text = formatScheduleTime(binding.root.context)
                }

                if (t.subject != null) {
                    with(binding.subjectView) {
                        text = t.subject?.code
                        setCompoundDrawableAtStart(t.subject?.tintDrawable(getCompoundDrawableAtStart()))
                    }
                } else binding.subjectView.isVisible = false

                binding.root.setOnClickListener {
                    actionListener.onActionPerformed(t, ActionListener.Action.SELECT, it)
                }
            }
        }
    }
}