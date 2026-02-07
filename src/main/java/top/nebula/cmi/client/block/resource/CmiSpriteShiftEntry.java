package top.nebula.cmi.client.block.resource;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import net.minecraft.resources.ResourceLocation;
import top.nebula.cmi.Cmi;

@SuppressWarnings("unused")
public class CmiSpriteShiftEntry {
	public static final SpriteShiftEntry SAND_PAPER_BELT;
	public static final SpriteShiftEntry RED_SAND_PAPER_BELT;

	static {
		SAND_PAPER_BELT = addShift(
				"block/grinder_belt/sand_paper",
				"block/grinder_belt/sand_paper_scroll"
		);

		RED_SAND_PAPER_BELT = addShift(
				"block/grinder_belt/red_sand_paper",
				"block/grinder_belt/red_sand_paper_scroll"
		);
	}

	private static SpriteShiftEntry addShift(String from, String to) {
		return SpriteShifter.get(
				Cmi.loadResource(from),
				Cmi.loadResource(to)
		);
	}

	private static CTSpriteShiftEntry addCT(CTType type, String name) {
		ResourceLocation base = Cmi.loadResource("block/" + name);
		return CTSpriteShifter.getCT(
				type,
				base,
				Cmi.loadResource("block/" + name + "_connected")
		);
	}

	private static CTSpriteShiftEntry rectangleCT(String name) {
		return addCT(AllCTTypes.RECTANGLE, name);
	}

	private static CTSpriteShiftEntry omniCT(String name) {
		return addCT(AllCTTypes.OMNIDIRECTIONAL, name);
	}

	public static void init() {
	}
}