package ch.timofey.grader.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

val Icons.CalendarToday: ImageVector
    get() {
        if (_calendarToday != null) {
            return _calendarToday!!
        }
        _calendarToday = materialIcon(name = "CalendarToday") {
            materialPath {
                moveTo(20.0F, 3.0F)
                horizontalLineToRelative(-1.0F)
                lineTo(19.0F, 1.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(2.0F)
                lineTo(7.0F, 3.0F)
                lineTo(7.0F, 1.0F)
                lineTo(5.0F, 1.0F)
                verticalLineToRelative(2.0F)
                lineTo(4.0F, 3.0F)
                curveToRelative(-1.1F, 0.0F, -2.0F, 0.9F, -2.0F, 2.0F)
                verticalLineToRelative(16.0F)
                curveToRelative(0.0F, 1.1F, 0.9F, 2.0F, 2.0F, 2.0F)
                horizontalLineToRelative(16.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                lineTo(22.0F, 5.0F)
                curveToRelative(0.0F, -1.1F, -0.9F, -2.0F, -2.0F, -2.0F)

                moveTo(20.0F, 21.0F)
                lineTo(4.0F, 21.0F)
                lineTo(4.0F, 10.0F)
                horizontalLineToRelative(16.0F)
                verticalLineToRelative(11.0F)

                moveTo(20.0F, 8.0F)
                lineTo(4.0F, 8.0F)
                lineTo(4.0F, 5.0F)
                horizontalLineToRelative(16.0F)
                verticalLineToRelative(3.0F)
                close()
            }
        }
        return _calendarToday!!
    }

private var _calendarToday: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconCalendarTodayPreview() {
    Image(imageVector = Icons.CalendarToday, contentDescription = null)
}