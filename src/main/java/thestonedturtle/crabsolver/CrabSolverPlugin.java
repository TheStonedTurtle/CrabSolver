package thestonedturtle.crabsolver;

import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Crab Solver"
)
public class CrabSolverPlugin extends Plugin
{
	@Inject
	private CrabSolverOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CrabSolverConfig config;

	@Inject
	private Client client;

	@Provides
	CrabSolverConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CrabSolverConfig.class);
	}

	@Getter
	private final Map<CrabCrystal, LocalPoint> crystalMap = new HashMap<>();

	@Override
	public void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	public void shutDown()
	{
		crystalMap.clear();
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned e)
	{
		final CrabCrystal crystal = CrabCrystal.getByObjectID(e.getGameObject().getId());
		if (crystal == null)
		{
			return;
		}

		crystalMap.put(crystal, e.getGameObject().getLocalLocation());
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned e)
	{
		final CrabCrystal crystal = CrabCrystal.getByObjectID(e.getGameObject().getId());
		if (crystal == null)
		{
			return;
		}

		crystalMap.remove(crystal);
	}
}
