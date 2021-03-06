package com.isaiahvonrundstedt.fokus.features.attachments.attach

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.isaiahvonrundstedt.fokus.R
import com.isaiahvonrundstedt.fokus.components.custom.ItemDecoration
import com.isaiahvonrundstedt.fokus.components.extensions.android.createToast
import com.isaiahvonrundstedt.fokus.databinding.ActivitySendAttachmentBinding
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttachToTaskActivity: BaseActivity(), AttachToTaskAdapter.AttachmentListener {
    private lateinit var binding: ActivitySendAttachmentBinding

    private val sendAdapter = AttachToTaskAdapter(this)
    private val viewModel: AttachToTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendAttachmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPersistentActionBar(binding.appBarLayout.toolbar)
        setToolbarTitle(R.string.intent_add_as_attachment)

        intent?.also {
            viewModel.subject = it.getStringExtra(Intent.EXTRA_SUBJECT)
            viewModel.url = it.getStringExtra(Intent.EXTRA_TEXT)
        }

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            layoutManager = LinearLayoutManager(context)
            adapter = sendAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        binding.titleView.text = viewModel.subject
        binding.summaryView.text = viewModel.url
        viewModel.tasks.observe(this) { sendAdapter.submitList(it) }
    }

    override fun onAttachedToTask(taskID: String) {
        viewModel.addAttachment(taskID)
        createToast(R.string.feedback_attachment_added)
        finish()
    }

}