package thestonedturtle.crabsolver;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CrabSolverPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CrabSolverPlugin.class);
		RuneLite.main(args);
	}
}
