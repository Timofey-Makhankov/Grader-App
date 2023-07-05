package ch.timofey.grader.ui.screen.module_list

import ch.timofey.grader.db.domain.module.Module
import java.util.UUID

sealed class ModuleListEvent {
    object OnReturnBack : ModuleListEvent()
    object OnFABClick : ModuleListEvent()
    data class OnCheckChange(val id: UUID, val value: Boolean) : ModuleListEvent()
    data class OnSwipeDelete(val module: Module) : ModuleListEvent()
}
