package org.d3if4075.Persegiku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4075.Persegiku.db.PersegiDao
import org.d3if6030.PenghitungPersegi.ViewModelUtama
import java.lang.IllegalArgumentException

class ViewModelFactoryUtama(
    private val db: PersegiDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelUtama::class.java)){
            return ViewModelUtama(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}