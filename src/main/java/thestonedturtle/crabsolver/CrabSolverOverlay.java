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

import com.google.common.base.Strings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
public class CrabSolverOverlay extends Overlay
{
	// Must be within this many tiles for the overlay to render
	private static final int MAX_RENDER_DISTANCE = 15;

	private final CrabSolverPlugin plugin;
	private final CrabSolverConfig config;
	private final SkillIconManager iconManager;
	private final Client client;

	@Inject
	private CrabSolverOverlay(final CrabSolverPlugin plugin, final CrabSolverConfig config,
							final SkillIconManager iconManager, final Client client)
	{
		this.plugin = plugin;
		this.config = config;
		this.iconManager = iconManager;
		this.client = client;

		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (client.getLocalPlayer() == null)
		{
			return null;
		}

		final WorldPoint playerPosition = client.getLocalPlayer().getWorldLocation();
		plugin.getCrystalMap().forEach((crabCrystal, gameObject) ->
		{
			if (playerPosition.distanceTo(gameObject.getWorldLocation()) > MAX_RENDER_DISTANCE)
			{
				return;
			}

			final Point point = Perspective.localToCanvas(client, gameObject.getLocalLocation(), client.getPlane(), 0);
			if (point == null)
			{
				return;
			}

			if (config.displayColor())
			{
				graphics.setColor(crabCrystal.getSolutionColor());
				graphics.fillOval(point.getX() - 11, point.getY() - 11, 22, 22);
			}

			if (config.displayStyle() && crabCrystal.getSolutionSkill() != null)
			{
				final BufferedImage icon = iconManager.getSkillImage(crabCrystal.getSolutionSkill(), true);
				if (icon == null)
				{
					return;
				}

				OverlayUtil.renderImageLocation(client, graphics, gameObject.getLocalLocation(), icon, 0);
			}
		});

		if (config.markTiles())
		{
			final Stroke borderStroke = new BasicStroke((float) config.borderWidth());
			for (final CrabTile tile : plugin.getTiles())
			{
				for (final WorldPoint p : tile.getWorldPoints())
				{
					if (p.getPlane() != client.getPlane())
					{
						continue;
					}

					if (playerPosition.distanceTo(p) > MAX_RENDER_DISTANCE)
					{
						continue;
					}

					drawTile(graphics, p, tile, borderStroke);
				}
			}
		}

		return null;
	}

	private void drawTile(Graphics2D graphics, WorldPoint p, CrabTile tile, Stroke borderStroke)
	{
		LocalPoint lp = LocalPoint.fromWorld(client, p);
		if (lp == null)
		{
			return;
		}

		Polygon poly = Perspective.getCanvasTilePoly(client, lp);
		if (poly != null)
		{
			OverlayUtil.renderPolygon(graphics, poly, tile.getColor(), new Color(0, 0, 0, config.fillOpacity()), borderStroke);
		}

		if (!Strings.isNullOrEmpty(tile.getLabel()))
		{
			Point canvasTextLocation = Perspective.getCanvasTextLocation(client, graphics, lp, tile.getLabel(), 0);
			if (canvasTextLocation != null)
			{
				OverlayUtil.renderTextLocation(graphics, canvasTextLocation, tile.getLabel(), tile.getColor());
			}
		}
	}
}
