package xyz.namutree0345.deadbydaylight

import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.potion.PotionEffectType

class KillListener : Listener {

    @EventHandler
    fun onKill(event: PlayerDeathEvent) {
        if(gameStarted) {
            if (event.entity.killer == killer) {
                remainHumans -= 1
                event.deathMessage = "${ChatColor.RED}살인마가 ${event.entity.name}를 죽였습니다. 남은 인간 수: $remainHumans"
                event.entity.gameMode = GameMode.SPECTATOR
                if(remainHumans == 0) {
                    killer?.removePotionEffect(PotionEffectType.GLOWING)
                    broadcastTitle("${ChatColor.RED}살인마 승리..", "", 60, 180, 180)
                    gameStarted = false
                }
            }
        }
    }

}