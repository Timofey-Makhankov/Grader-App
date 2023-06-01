package ch.timofey.grader.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

val Icons.Calculate: ImageVector
    get() {
        if (_calculate != null) {
            return _calculate!!
        }
        _calculate = materialIcon(name = "Calculate") {
            materialPath {
                moveTo(19.0F, 3.0F)
                horizontalLineTo(5.0F)
                curveTo(3.9F, 3.0F, 3.0F, 3.9F, 3.0F, 5.0F)
                verticalLineToRelative(14.0F)
                curveToRelative(0.0F, 1.1F, 0.9F, 2.0F, 2.0F, 2.0F)
                horizontalLineToRelative(14.0F)
                curveToRelative(1.1F, 0.0F, 2.0F, -0.9F, 2.0F, -2.0F)
                verticalLineTo(5.0F)
                curveTo(21.0F, 3.9F, 20.1F, 3.0F, 19.0F, 3.0F)

                moveTo(13.03F, 7.06F)
                lineTo(14.09F, 6.0F)
                lineToRelative(1.41F, 1.41F)
                lineTo(16.91F, 6.0F)
                lineToRelative(1.06F, 1.06F)
                lineToRelative(-1.41F, 1.41F)
                lineToRelative(1.41F, 1.41F)
                lineToRelative(-1.06F, 1.06F)
                lineTo(15.5F, 9.54F)
                lineToRelative(-1.41F, 1.41F)
                lineToRelative(-1.06F, -1.06F)
                lineToRelative(1.41F, -1.41F)
                lineTo(13.03F, 7.06F)

                moveTo(6.25F, 7.72F)
                horizontalLineToRelative(5.0F)
                verticalLineToRelative(1.5F)
                horizontalLineToRelative(-5.0F)
                verticalLineTo(7.72F)

                moveTo(11.5F, 16.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(2.0F)
                horizontalLineTo(8.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineTo(6.0F)
                verticalLineToRelative(-1.5F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(1.5F)
                verticalLineToRelative(2.0F)
                horizontalLineToRelative(2.0F)
                verticalLineTo(16.0F)

                moveTo(18.0F, 17.25F)
                horizontalLineToRelative(-5.0F)
                verticalLineToRelative(-1.5F)
                horizontalLineToRelative(5.0F)
                verticalLineTo(17.25F)

                moveTo(18.0F, 14.75F)
                horizontalLineToRelative(-5.0F)
                verticalLineToRelative(-1.5F)
                horizontalLineToRelative(5.0F)
                verticalLineTo(14.75F)
                close()
            }
        }
        return _calculate!!
    }

private var _calculate: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconCalculatePreview() {
    Image(imageVector = Icons.Calculate, contentDescription = null)
}