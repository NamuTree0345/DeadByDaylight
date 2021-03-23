package xyz.namutree0345.deadbydaylight

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class KillListener : Listener {

    @EventHandler
    fun onKill(event: PlayerDeathEvent) {
        if(gameStarted) {
            if (event.entity.killer == killer) {
                event.deathMessage = "${ChatColor.RED}살인마가 ${event.entity.name}를 죽였습니다."
                event.entity.gameMode = GameMode.SPECTATOR
            }
        }
    }

}