package package;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin {

	public void onEnable() {
		// Create a JavaPlugin instance of this and pass it into the util
		// Create DB connection then pass it into the util
		
		this.playerStatModel = new PlayerStatModel();
        this.playerStatModel.createTable();
	}

	public void onDisable() {
		this.chestParticles.stopBlock(this.particleBlock);
	}

	// Normally, you would put the code below in a command, event, etc. but I have it here just to show people how it works
	public void insert(){
		this.playerStatModel.insert("tokyojack");
	}
	
	public void addDeath(){
		this.playerStatModel.addDeath("tokyojack");
	}
	
	public void select(){
		Valeon.getAPI().getPlayerStatModel().select("tokyojack", new SelectQuery() {
            @Override
            public void resault(Object resault) throws SQLException {
                PlayerStat playerStat = (PlayerStat) resault;
				System.out.println("Kills " + playerStat.getTotalKills());
				System.out.println("Deaths " + playerStat.getTotalDeaths());
            }
        });
	}
	
}