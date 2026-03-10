package eu.pw.foodorigin.presentation.screen.scanBarcode

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import eu.pw.foodorigin.domain.service.HapticFeedbackService
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.BarcodeBoundingBox
import eu.pw.foodorigin.presentation.screen.scanBarcode.model.DetectedBarcode
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class ScanBarcodeViewModelTest {

	private lateinit var viewModel: ScanBarcodeViewModel
	private val hapticFeedbackService: HapticFeedbackService = mockk(relaxed = true)
	private val testDispatcher = StandardTestDispatcher()
	private val dummyBox = BarcodeBoundingBox(0f, 0f, 0.5f, 0.5f)

	private val afterDetectionDelay = ScanBarcodeViewModel.AFTER_DETECTION_DELAY

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		mockkStatic(Timber::class)
		viewModel = ScanBarcodeViewModel(hapticFeedbackService)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
		unmockkStatic(Timber::class)
	}

	@Test
	fun `getUiState initializes state without detected barcode`() = runTest {
		assertNull(viewModel.uiState.value.detectedBarcode)
	}

	@Test
	fun `onCodeDetected emits event`() = runTest {
		viewModel.events.test {
			val barcode = DetectedBarcode("123", dummyBox)
			viewModel.onCodeDetected(barcode)

			advanceTimeBy(201)
			assertEquals(ScanBarcodeEvent.BarcodeScanned("123"), awaitItem())
		}
	}

	@Test
	fun `onCodeDetected updates uiState`() = runTest {
		val barcode = DetectedBarcode("123456", dummyBox)
		viewModel.onCodeDetected(barcode)

		assertEquals(barcode, viewModel.uiState.value.detectedBarcode)
	}

	@Test
	fun `onCodeDetected triggers haptic feedback`() = runTest {
		val barcode = DetectedBarcode("123456", dummyBox)
		viewModel.onCodeDetected(barcode)

		advanceTimeBy(1)
		coVerify(exactly = 1) { hapticFeedbackService.vibrate() }
	}

	@Test
	fun `onCodeDetected event emits with delay`() = runTest {
		viewModel.events.test {
			val barcode = DetectedBarcode("123", dummyBox)
			viewModel.onCodeDetected(barcode)

			advanceTimeBy(afterDetectionDelay / 2)
			expectNoEvents()

			advanceTimeBy(afterDetectionDelay / 2 + 1)
			assertEquals(ScanBarcodeEvent.BarcodeScanned("123"), awaitItem())
		}
	}

	@Test
	fun `onCodeDetected processing flag concurrency`() = runTest {
		val barcode = DetectedBarcode("123", dummyBox)

		viewModel.onCodeDetected(barcode)
		viewModel.onCodeDetected(barcode.copy(code = "456"))

		advanceTimeBy(afterDetectionDelay + 1)

		assertEquals("123", viewModel.uiState.value.detectedBarcode?.code)
		coVerify(exactly = 1) { hapticFeedbackService.vibrate() }
	}

	@Test
	fun `onCodeDetected blank barcode validation`() = runTest {
		val blankBarcode = DetectedBarcode("  ", dummyBox)
		viewModel.onCodeDetected(blankBarcode)

		assertNull(viewModel.uiState.value.detectedBarcode)
		coVerify(exactly = 0) { hapticFeedbackService.vibrate() }
	}

	@Test
	fun `onCodeDetected reset processing guard`() = runTest {
		val barcode1 = DetectedBarcode("123", dummyBox)
		val barcode2 = DetectedBarcode("456", dummyBox)

		viewModel.onCodeDetected(barcode1)
		advanceTimeBy(afterDetectionDelay + 100)

		viewModel.onCodeDetected(barcode2)

		assertEquals("123", viewModel.uiState.value.detectedBarcode?.code)
	}

	@Test
	fun `onCodeDetected event emission payload`() = runTest {
		val code = "987654321"
		viewModel.events.test {
			viewModel.onCodeDetected(DetectedBarcode(code, dummyBox))
			advanceTimeBy(afterDetectionDelay + 1)
			val event = awaitItem() as ScanBarcodeEvent.BarcodeScanned
			assertEquals(code, event.code)
		}
	}

	@Test
	fun `onCodeDetected viewModelScope cancellation`() = runTest {
		viewModel.events.test {
			viewModel.onCodeDetected(DetectedBarcode("123", dummyBox))

			advanceTimeBy(afterDetectionDelay / 2)
			viewModel.viewModelScope.cancel()

			advanceTimeBy(afterDetectionDelay)
			expectNoEvents()
		}
	}
}
