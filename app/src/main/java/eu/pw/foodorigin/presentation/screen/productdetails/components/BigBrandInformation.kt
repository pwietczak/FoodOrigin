package eu.pw.foodorigin.presentation.screen.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.foodorigin.R
import eu.pw.foodorigin.presentation.screen.productdetails.ProductDetailsUiState
import eu.pw.foodorigin.presentation.screen.productdetails.model.ProductUi

@Composable
fun BigBrandInformation(
	modifier: Modifier = Modifier,
	uiState: ProductDetailsUiState.Success,
                       ) {
	uiState.relatedBigBrandName?.let { brandName ->
		Row(
			modifier = modifier
				.clip(RoundedCornerShape(12.dp))
				.background(MaterialTheme.colorScheme.errorContainer)
				.padding(horizontal = 16.dp, vertical = 12.dp),
			verticalAlignment = Alignment.CenterVertically,
		   ) {
			Icon(
				imageVector = Icons.Default.Warning,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.error,
				modifier = Modifier
					.padding(end = 8.dp)
					.size(32.dp),
			    )
			Text(
				text = stringResource(R.string.product_details_big_brand_information, brandName),
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.onErrorContainer,
			    )
		}

	}
}

@PreviewLightDark
@Composable
fun BigBrandInformationPreview() {
	BigBrandInformation(
		modifier = Modifier,
		uiState = ProductDetailsUiState.Success(
			productCode = "123",
			productUi = ProductUi(
				id = "123",
				productName = "Test Product Name",
				brand = "Test Brand",
				brandsTags = null,
				imageUrl = "image_url",
				quantity = "2137 kilo",
				link = "www.niezesrajsie.edu",
			                     ),
			relatedBigBrandName = "ECorp",
		                                       ),
	                   )
}
