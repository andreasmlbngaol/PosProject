package model.utils

import java.util.prefs.Preferences

object PreferenceManager {
    private val prefs = Preferences.userRoot().node("pos_prefs")

    fun saveLogin(id: String) {
        prefs.put("USER_ID", id)
    }

    fun getUserId(): String? {
        return prefs.get("USER_ID", null)
    }

    fun clearLogin() {
        prefs.remove("USER_ID")
    }
}