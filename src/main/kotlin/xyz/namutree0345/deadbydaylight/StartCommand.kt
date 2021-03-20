package xyz.namutree0345.deadbydaylight

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

var gameStarted = false
var killer: Player? = null

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

            val plrs = Bukkit.getOnlinePlayers().toTypedArray()
            killer = plrs[Random().nextInt(plrs.size)]
            Bukkit.broadcastMessage("${ChatColor.RED}살인마: ${killer?.name}")
            gameStarted = true
            broadcastTitle("${ChatColor.RED}시작!", "", 60, 180, 180)
        }, 20 * 120)
        return true
    }

    private fun broadcastTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut)
        }
    }

}
