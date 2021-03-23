package xyz.namutree0345.deadbydaylight

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

var gameStarted = false
var killer: Player? = null
var remainHumans = 0

class StartCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        broadcastTitle("${ChatColor.RED}Dead by Daylight", "", 60, 180, 180)
        val scheduleId: Int?
        var num = 120
        scheduleId = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(DeadByDaylight::class.java), Runnable {
            broadcastTitle("${ChatColor.RED}${num}초 남음", "", 0, 60, 0)
            num--
        }, 180, 20)
        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(DeadByDaylight::class.java), Runnable {
            if (scheduleId != null) {
                Bukkit.getScheduler().cancelTask(scheduleId)
            }

            remainHumans = Bukkit.getOnlinePlayers().size - 1
            Bukkit.broadcastMessage("${ChatColor.RED}인식된 플레이어 수: $remainHumans")

            val plrs = Bukkit.getOnlinePlayers().toTypedArray()
            killer = plrs[Random().nextInt(plrs.size)]
            killer?.inventory?.addItem(ItemStack(Material.DIAMOND_AXE))
            Bukkit.broadcastMessage("${ChatColor.RED}살인마: ${killer?.name}")
            gameStarted = true
            broadcastTitle("${ChatColor.RED}시작!", "", 60, 180, 180)
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(DeadByDaylight::class.java), Runnable {
                if(remainHumans != 0) {
                    broadcastTitle("${ChatColor.GREEN}생존자 승리", "", 60, 180, 180)
                    gameStarted = false
                }
            }, getMinuteAsTick(5, 0))
        }, getMinuteAsTick(0, 120))
        return true
    }

}

fun broadcastTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
    for (player in Bukkit.getOnlinePlayers()) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut)
    }
}

fun getMinuteAsTick(minute: Long, second: Long) : Long {
    return (minute * (20 * 60)) + second * 20
}
