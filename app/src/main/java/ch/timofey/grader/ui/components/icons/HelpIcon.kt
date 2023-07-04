package ch.timofey.grader.ui.components.icons

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

val Icons.Help: ImageVector
    get() {
        if (_help != null) {
            return _help!!
        }
        _help = materialIcon(name = "Help") {
            materialPath {
                moveTo(12.0F, 2.0F)
                curveTo(6.48F, 2.0F, 2.0F, 6.48F, 2.0F, 12.0F)
                reflectiveCurveToRelative(4.48F, 10.0F, 10.0F, 10.0F)
                reflectiveCurveToRelative(10.0F, -4.48F, 10.0F, -10.0F)
                reflectiveCurveTo(17.52F, 2.0F, 12.0F, 2.0F)

                moveTo(13.0F, 19.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(-2.0F)
                horizontalLineToRelative(2.0F)
                verticalLineToRelative(2.0F)

                moveTo(15.07F, 11.25F)
                lineToRelative(-0.9F, 0.92F)
                curveTo(13.45F, 12.9F, 13.0F, 13.5F, 13.0F, 15.0F)
                horizontalLineToRelative(-2.0F)
                verticalLineToRelative(-0.5F)
                curveToRelative(0.0F, -1.1F, 0.45F, -2.1F, 1.17F, -2.83F)
                lineToRelative(1.24F, -1.26F)
                curveToRelative(0.37F, -0.36F, 0.59F, -0.86F, 0.59F, -1.41F)
                curveToRelative(0.0F, -1.1F, -0.9F, -2.0F, -2.0F, -2.0F)
                reflectiveCurveToRelative(-2.0F, 0.9F, -2.0F, 2.0F)
                lineTo(8.0F, 9.0F)
                curveToRelative(0.0F, -2.21F, 1.79F, -4.0F, 4.0F, -4.0F)
                reflectiveCurveToRelative(4.0F, 1.79F, 4.0F, 4.0F)
                curveToRelative(0.0F, 0.88F, -0.36F, 1.68F, -0.93F, 2.25F)
                close()
            }
        }
        return _help!!
    }

private var _help: ImageVector? = null

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun IconHelpPreview() {
    Image(imageVector = Icons.Help, contentDescription = null)
}