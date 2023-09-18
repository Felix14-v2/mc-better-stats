package io.github.thecsdev.betterstats.api.client.gui.stats.panel;

import static io.github.thecsdev.betterstats.api.client.registry.BSStatsTabs.COLOR_SPECIAL;
import static io.github.thecsdev.tcdcommons.api.util.TextUtils.literal;
import static io.github.thecsdev.tcdcommons.api.util.TextUtils.translatable;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import io.github.thecsdev.betterstats.api.client.gui.panel.BSComponentPanel;
import io.github.thecsdev.betterstats.api.util.io.IStatsProvider;
import io.github.thecsdev.tcdcommons.api.client.gui.other.TEntityRendererElement;
import io.github.thecsdev.tcdcommons.api.client.gui.other.TLabelElement;
import io.github.thecsdev.tcdcommons.api.client.gui.panel.TPanelElement;
import net.minecraft.entity.EntityType;

public final class GameProfilePanel extends BSComponentPanel
{
	// ==================================================
	public static final int HEIGHT = 128;
	// --------------------------------------------------
	private final IStatsProvider statsProvider;
	// ==================================================
	public GameProfilePanel(int x, int y, int width, IStatsProvider statsProvider) throws NullPointerException
	{
		super(x, y, width, HEIGHT);
		setScrollFlags(TPanelElement.SCROLL_VERTICAL);
		setScrollPadding(7);
		setOutlineColor(0);
		
		this.statsProvider = Objects.requireNonNull(statsProvider);
	}
	// ==================================================
	protected final @Override void init()
	{
		//calculations
		final int col = COLOR_SPECIAL;
		final int sp = getScrollPadding();
		final int erWidth = (getWidth() - (sp*2)) / 4;
		final int erHeight = (getHeight() - (sp*2));
		final int inWidth = (getWidth() - (sp*2)) - (erWidth + 5);
		
		//entity renderer
		final var er = new TEntityRendererElement(sp, sp, erWidth, erHeight, EntityType.PLAYER);
		er.setEntityScale(1.2d);
		addChild(er, true);
		//FIXME - GameProfilePanel display proper player when looking someone else's stats
		
		//info
		final @Nullable var profile = this.statsProvider.getGameProfile();
		final var txt_name = (profile != null) ? literal(Objects.toString(profile.getName())) : literal("-");
		final var txt_uuid = (profile != null) ? literal(Objects.toString(profile.getId())) : literal("-");
		
		final var lbl_nameKey = new TLabelElement(getEndX() - (sp + inWidth), getY() + sp, inWidth, 10);
		lbl_nameKey.setText(translatable("entity.minecraft.player"));
		lbl_nameKey.setTextColor(col);
		addChild(lbl_nameKey, false);
		
		final var lbl_nameVal = new TLabelElement(
				lbl_nameKey.getX(), lbl_nameKey.getEndY(),
				lbl_nameKey.getWidth(), lbl_nameKey.getHeight());
		lbl_nameVal.setText(txt_name);
		addChild(lbl_nameVal, false);
		
		final var lbl_uuidKey = new TLabelElement(
				lbl_nameVal.getX(), lbl_nameVal.getEndY() + 10,
				lbl_nameVal.getWidth(), lbl_nameVal.getHeight());
		lbl_uuidKey.setText(literal("UUID"));
		lbl_uuidKey.setTextColor(col);
		addChild(lbl_uuidKey, false);
		
		final var lbl_uuidVal = new TLabelElement(
				lbl_uuidKey.getX(), lbl_uuidKey.getEndY(),
				lbl_uuidKey.getWidth(), lbl_uuidKey.getHeight());
		lbl_uuidVal.setText(txt_uuid);
		addChild(lbl_uuidVal, false);
		
		final var lbl_pbKey = new TLabelElement(
				lbl_uuidVal.getX(), lbl_uuidVal.getEndY() + 10,
				lbl_uuidVal.getWidth(), lbl_uuidVal.getHeight());
		lbl_pbKey.setText(translatable("tcdcommons.api.badge.playerbadge.plural"));
		lbl_pbKey.setTextColor(col);
		addChild(lbl_pbKey, false);
		
		final var pnl_pbs = new PBSummaryPanel(
				lbl_uuidVal.getX(), lbl_pbKey.getEndY() + 4,
				lbl_uuidVal.getWidth(), 44 - 4,
				this.statsProvider);
		pnl_pbs.setScrollPadding(0);
		pnl_pbs.setBackgroundColor(0);
		pnl_pbs.setOutlineColor(0);
		addChild(pnl_pbs, false);
	}
	// ==================================================
}