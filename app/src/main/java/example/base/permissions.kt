package example.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastJoinToString
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat

/**
 * 权限申请失败后显示的弹窗
 * @param deniedMap 被拒绝的权限列表， key: 权限名， value: 权限描述
 * @param onDismiss 弹窗关闭后的回调
 */
@Composable
fun PermissionDeniedDialog(context: Context,
                           deniedMap: Map<String, String>,
                           onDismiss: () -> Unit
) {
    AlertDialog(
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                )
                onDismiss()
            }) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("取消")
            }
        },
        title = {
            Text("权限申请失败")
        },
        text = {
            var text = "无法申请以下权限，您可在【权限管理】中手动开启\n"

            text += deniedMap.keys
                .mapIndexed { index, permission -> "${index + 1}. $permission : ${deniedMap[permission]}" }
                .fastJoinToString("\n")

            Text(text = text)
        }
    )

}

/**
 * 创建权限申请对象
 * @param context 上下文
 * @param permissionMap 权限列表， key: 权限名， value: 权限描述
 * @param onGrant 权限申请成功后的回调
 * @return 权限申请对象, 通过调用 execute 方法，会自动判断权限，并申请权限， 如果权限被拒绝，则会显示权限申请失败的弹窗
 * 如果权限申请通过，则会调用 onGrant 方法，执行业务逻辑
 */
@Composable
fun rememberExecuteOnPermissions(context: Context,
                                 permissionMap: Map<String, String>,
                                 onGrant: () -> Unit
): ExecuteOnPermissions {
    val curGrant by rememberUpdatedState(newValue = onGrant)

    var deniedMap: Map<String, String> by remember { mutableStateOf(emptyMap()) }

    // 申请权限
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        // 判断权限是否被拒绝
        deniedMap = permissions.filter { !it.value }.map {
            it.key to permissionMap[it.key]!!
        }.toMap()

        if (deniedMap.isEmpty()) {
            curGrant()
        }
    }

    // 如果存在被拒绝的权限， 显示权限申请失败的弹窗
    if (deniedMap.isNotEmpty()) {
        PermissionDeniedDialog(context = context, deniedMap = deniedMap) {
            deniedMap = emptyMap()
        }
    }

    // 创建权限申请对象，通过调用 execute 方法，会自动判断权限，并申请权限
    val executeOnPermissions =
        ExecuteOnPermissions(permissionMap.keys.toList(), requestPermissionLauncher, curGrant)

    return executeOnPermissions
}


data class ExecuteOnPermissions(
    val permissions: List<String>,
    val requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    val onGrant: () -> Unit
) {

    /**
     * 执行权限申请
     * @param context 上下文
     */
    fun execute(context: Context) {
        var isGrant = true
        for (permission in permissions) {
            if (isPermissionGrant(context = context, permission = permission)) {
                continue
            }
            isGrant = false
            break        }
        if (!isGrant) {
            // 申请权限
            requestPermissionLauncher.launch(permissions.toTypedArray())
        } else {
            onGrant()
        }
    }
}

/**
 * 判断权限是否被授予
 */
fun isPermissionGrant(context: Context, permission: String) =
    ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
