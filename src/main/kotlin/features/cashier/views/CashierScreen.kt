package features.cashier.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import features.auth.utils.PreferenceManager
import features.cashier.components.CashierTopBar
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun CashierScreen(
    onBack: () -> Unit,
    viewModel: CashierViewModel = koinViewModel<CashierViewModel>()
) {
    val code by viewModel.code.collectAsState()

    val numList = listOf(
        listOf("1", "2", "3", "Back Space"),
        listOf("4", "5", "6", "000"),
        listOf("7", "8", "9", "Enter"),
        listOf("C", "0", ".", "OK")
    )

    val availableCameras by viewModel.availableCameras.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CashierTopBar {
                viewModel.stopCapture()
                onBack()
            }
        }
    ) { contentPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.width(300.dp)
                    ) {
                        OutlinedTextField(
                            value = PreferenceManager.getUserId()!!,
                            onValueChange = { },
                            enabled = false,
                            readOnly = true,
                            label = { Text("ID") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = MaterialTheme.colors.onBackground,
                                disabledLabelColor = MaterialTheme.colors.onBackground,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = code,
                            onValueChange = { viewModel.setCode(it) },
                            label = { Text("Kode") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    AnimatedContent(availableCameras) { listCamera ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if(listCamera.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                Text(text = "Pilih Kamera")
                                Row(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colors.secondary)
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if(!viewModel.cameraOpened.collectAsState().value) {
                                        availableCameras.forEach {
                                            Text(
                                                text = "Kamera $it",
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clickable {
                                                        viewModel.startCapture(it) {
                                                            println("Kamera $it di pilih")
                                                        }
                                                    },
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colors.onSecondary
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = "Tutup Kamera",
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    viewModel.stopCapture()
                                                },
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colors.onSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                ) {

                }
                Box(modifier = Modifier.weight(1f))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.secondary)
            ) {
                Box(modifier = Modifier.weight(1f)) {

                }
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    numList.forEach { row ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            row.forEach { num ->
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .clip(MaterialTheme.shapes.large)
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .background(MaterialTheme.colors.background)
                                            .clickable {

                                            }
                                    ) {
                                        Text(
                                            text = num,
                                            style = MaterialTheme.typography.h6,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}