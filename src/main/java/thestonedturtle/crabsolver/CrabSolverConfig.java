/*
 * Copyright (c) 2020, TheStonedTurtle <https://github.com/TheStonedTurtle>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package thestonedturtle.crabsolver;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("crabsolver")
public interface CrabSolverConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "displayColor",
		name = "Display Orb Color",
		description = "Configures whether to display the orb color needed to solve the crystal"
	)
	default boolean displayColor()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "displayStyle",
		name = "Display Combat Style",
		description = "Configures whether to display the combat style needed to solve the crystal"
	)
	default boolean displayStyle()
	{
		return true;
	}

	@ConfigSection(
		name = "Ground Markers",
		description = "The settings for controlling how ground marker are displayed",
		position = 2
	)
	String tileSection = "tileSection";

	@ConfigItem(
		position = 0,
		keyName = "markTiles",
		name = "Mark  Tiles",
		description = "Configures whether to mark the tiles the crabs need to be on to solve the puzzle",
		section = tileSection
	)
	default boolean markTiles()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "borderWidth",
		name = "Border Width",
		description = "Width of the marked tile border",
		section = tileSection
	)
	default double borderWidth()
	{
		return 2;
	}

	@ConfigItem(
		position = 2,
		keyName = "fillOpacity",
		name = "Fill Opacity",
		description = "Opacity of the tile fill color",
		section = tileSection
	)
	default int fillOpacity()
	{
		return 50;
	}
}
