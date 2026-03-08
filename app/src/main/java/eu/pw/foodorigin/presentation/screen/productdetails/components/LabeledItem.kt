package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LabeledItem(
	modifier: Modifier,
	label: String,
	labelTextColor: Color,
	backgroundColor: Color,
	onClick: (() -> Unit)?,
	content: @Composable RowScope.() -> Unit,
               ) {
	Column(
		modifier = modifier,
	      ) {
		Text(
			text = label,
			style = MaterialTheme.typography.labelLarge,
			color = labelTextColor,
			modifier = Modifier.padding(horizontal = 16.dp)
		    )
		Spacer(modifier = Modifier.height(2.dp))
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clip(RoundedCornerShape(12.dp))
				.background(backgroundColor)
				.clickable(enabled = onClick != null) {
					onClick?.invoke()
				}
				.padding(horizontal = 16.dp, vertical = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		   ) {
			content.invoke(this)
		}
	}
}
