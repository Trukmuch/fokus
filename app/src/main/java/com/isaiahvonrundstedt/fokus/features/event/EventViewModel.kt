package com.isaiahvonrundstedt.fokus.features.event

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.isaiahvonrundstedt.fokus.database.repository.EventRepository
import com.isaiahvonrundstedt.fokus.features.event.widget.EventWidgetProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class EventViewModel @ViewModelInject constructor(
    @ApplicationContext
    private val context: Context,
    private val repository: EventRepository
) : ViewModel() {

    private val _events: LiveData<List<EventPackage>> = repository.fetchLiveData()

    val dates: MediatorLiveData<List<LocalDate>> = MediatorLiveData()
    val events: MediatorLiveData<List<EventPackage>> = MediatorLiveData()
    val eventsEmpty: LiveData<Boolean> = Transformations.map(events) { it.isNullOrEmpty() }

    val today: LocalDate
        get() = LocalDate.now()
    val currentMonth: YearMonth
        get() = YearMonth.now()

    var selectedDate: LocalDate = today
        set(value) {
            field = value
            events.value = _events.value?.filter { it.event.schedule!!.toLocalDate() == selectedDate }
        }

    var startMonth: YearMonth = currentMonth.minusMonths(10)
    var endMonth: YearMonth = currentMonth.plusMonths(10)

    init {
        events.addSource(_events) { items ->
            events.value = items.filter { it.event.schedule!!.toLocalDate() == selectedDate }
        }
        dates.addSource(_events) { items ->
            dates.value = items.map { it.event.schedule!!.toLocalDate() }.distinct()
        }
    }

    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)

        EventWidgetProvider.triggerRefresh(context)
    }

    fun remove(event: Event) = viewModelScope.launch {
        repository.remove(event)

        EventWidgetProvider.triggerRefresh(context)
    }

    fun update(event: Event) = viewModelScope.launch {
        repository.update(event)

        EventWidgetProvider.triggerRefresh(context)
    }

}