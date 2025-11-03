package com.howtokaise.nexttune.presentation.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.howtokaise.nexttune.presentation.viewmodel.RoomViewmodel

@Composable
fun SettingScreen(
    viewmodel: RoomViewmodel,
    navController: NavHostController? = null
) {
    val context = LocalContext.current
    val roomData by viewmodel.roomData.collectAsState()
    val showLeaveDialog by viewmodel.showLeaveDialog.collectAsState()

    val roomCode = roomData?.optInt("roomCode")?.toString() ?: ""

    if (showLeaveDialog) {
        AlertDialog(
            onDismissRequest = { viewmodel.hideLeaveConfirmation() },
            title = { Text("Leave Room?", color = Color.White) },
            text = { Text("Are you sure you want to leave this room?", color = Color.LightGray) },
            confirmButton = {
                Button(
                    onClick = { viewmodel.leaveRoom(navController) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF4444)
                    )
                ) {
                    Text("Leave")
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewmodel.hideLeaveConfirmation() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF666666)
                    )
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color(0xFF1E293B),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }

    Dialog(
        onDismissRequest = { viewmodel.closeSettings() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E3A8A),
                            Color(0xFF0F172A)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "Room Settings",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Room Code Section
                SettingItem(
                    icon = Icons.Default.Group,
                    title = "Room Code",
                    subtitle = roomCode.ifEmpty { "No room code" },
                    actionIcon = Icons.Default.ContentCopy,
                    onActionClick = {
                        if (roomCode.isNotEmpty()) {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Room Code", roomCode)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Invite Friends Section
                SettingItem(
                    icon = Icons.Default.Share,
                    title = "Invite Friends",
                    subtitle = "Share room code with friends",
                    actionIcon = null,
                    onActionClick = {
                        if (roomCode.isNotEmpty()) {
                            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                                "Join my NextTune room! Room Code: $roomCode")
                            context.startActivity(android.content.Intent.createChooser(shareIntent, "Share Room Code"))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Leave Room Section
                SettingItem(
                    icon = Icons.Default.ExitToApp,
                    title = "Leave Room",
                    subtitle = "Exit from this room",
                    actionIcon = null,
                    onActionClick = { viewmodel.showLeaveConfirmation() },
                    isDanger = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Close Button
                Button(
                    onClick = { viewmodel.closeSettings() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF334155)
                    )
                ) {
                    Text("Close", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionIcon: ImageVector?,
    onActionClick: () -> Unit,
    isDanger: Boolean = false
) {
    val textColor = if (isDanger) Color(0xFFFF6B6B) else Color.White
    val subtitleColor = if (isDanger) Color(0xFFFF6B6B) else Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x33FFFFFF))
            .padding(16.dp)
            .clickable { onActionClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = subtitleColor,
                fontSize = 14.sp
            )
        }

        actionIcon?.let {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = it,
                    contentDescription = "Action",
                    tint = Color.White
                )
            }
        }
    }
}
