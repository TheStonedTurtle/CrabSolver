package thestonedturtle.crabsolver;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Crab Solver"
)
public class CrabSolverPlugin extends Plugin
{
	@Inject
	private CrabSolverConfig config;

	@Provides
	CrabSolverConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CrabSolverConfig.class);
	}
}
