package com.isaiahvonrundstedt.fokus.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.preference.Preference
import com.isaiahvonrundstedt.fokus.R
import com.isaiahvonrundstedt.fokus.databinding.ActivityNoticesBinding
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseActivity
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BasePreference

class NoticesActivity: BaseActivity() {

    private lateinit var binding: ActivityNoticesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPersistentActionBar(binding.appBarLayout.toolbar)
        setToolbarTitle(R.string.activity_notices)
    }

    companion object {

        const val URL_LAUNCHER_ICON_BASE = "https://flaticon.com/authors/freepik"
        const val URL_NOTIFICATION_SOUND = "https://www.zapsplat.com/music/ui-alert-prompt-warm-wooden-mallet-style-notification-tone-generic-11/"
        const val URL_USER_INTERFACE_ICONS = "https://heroicons.dev"

        class NoticesFragment: BasePreference() {

            override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
                setPreferencesFromResource(R.xml.xml_about_notices, rootKey)
            }

            override fun onStart() {
                super.onStart()

                findPreference<Preference>(R.string.key_credit_libraries)
                    ?.setOnPreferenceClickListener {
                        startActivity(Intent(requireContext(), LibrariesActivity::class.java))
                        true
                    }


                findPreference<Preference>(R.string.key_credit_notification_sound)
                    ?.setOnPreferenceClickListener {
                        CustomTabsIntent.Builder().build()
                            .launchUrl(requireContext(), Uri.parse(URL_NOTIFICATION_SOUND))

                        true
                    }


                findPreference<Preference>(R.string.key_credit_launcher_icon)
                    ?.setOnPreferenceClickListener {
                        CustomTabsIntent.Builder().build()
                            .launchUrl(requireContext(), Uri.parse(URL_LAUNCHER_ICON_BASE))

                        true
                    }

                findPreference<Preference>(R.string.key_credit_user_interface_icons)
                    ?.setOnPreferenceClickListener {
                        CustomTabsIntent.Builder().build()
                            .launchUrl(requireContext(), Uri.parse(URL_USER_INTERFACE_ICONS))

                        true
                    }

            }
        }
    }
}