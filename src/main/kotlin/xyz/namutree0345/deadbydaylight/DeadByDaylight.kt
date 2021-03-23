package xyz.namutree0345.deadbydaylight

import org.bukkit.plugin.java.JavaPlugin

class DeadByDaylight : JavaPlugin() {

    override fun onEnable() {
        getCommand("startdbd")?.setExecutor(StartCommand())
        server.pluginManager.registerEvents(KillListener(), this)
    }

}