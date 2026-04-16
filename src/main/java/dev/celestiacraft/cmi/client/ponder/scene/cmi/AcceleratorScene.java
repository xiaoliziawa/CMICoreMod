package dev.celestiacraft.cmi.client.ponder.scene.cmi;

import dev.celestiacraft.cmi.common.register.CmiBlock;
import dev.celestiacraft.cmi.common.register.CmiMechanism;
import dev.celestiacraft.libs.client.ponder.NebulaSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class AcceleratorScene {
	public static void usage(SceneBuilder builder, SceneBuildingUtil util) {
		builder.title("accelerator", "如何使用催生器");

		builder.showBasePlate();
		builder.idle(20);

		builder.world().setBlock(util.grid().at(3, 0, 3), CmiBlock.ACCELERATOR.getDefaultState(), false);
		builder.idle(10);

		builder.overlay().showOutline(
				PonderPalette.BLUE,
				NebulaSceneBuilder.OBJECT,
				util.select().position(3, 0, 3),
				35
		);

		builder.overlay().showText(30)
				.text("这是一个构件催生器")
				.pointAt(util.vector().topOf(3, 1, 3))
				.placeNearTarget();
		builder.idle(40);

		builder.overlay().showText(30)
				.text("右键对它使用一个磁力构件...")
				.pointAt(util.vector().topOf(3, 1, 3))
				.placeNearTarget();
		builder.idle(20);

		builder.overlay().showControls(
						util.vector().topOf(3, 1, 3),
						Pointing.DOWN,
						40
				).rightClick()
				.withItem(CmiMechanism.IRON.get().getDefaultInstance());

		builder.idle(40);

		builder.world().setBlock(util.grid().at(3, 0, 4), Blocks.IRON_ORE.defaultBlockState(), false);
		builder.world().setBlock(util.grid().at(4, 0, 3), Blocks.IRON_ORE.defaultBlockState(), false);
		builder.world().setBlock(util.grid().at(3, 0, 5), Blocks.REDSTONE_ORE.defaultBlockState(), false);
		builder.world().setBlock(util.grid().at(3, 0, 2), Blocks.IRON_ORE.defaultBlockState(), false);
		builder.world().setBlock(util.grid().at(2, 0, 5), Blocks.IRON_ORE.defaultBlockState(), false);
		builder.world().setBlock(util.grid().at(1, 0, 1), Blocks.IRON_ORE.defaultBlockState(), false);

		builder.overlay().showText(30)
				.text("...随后它周围的石头便会变成矿石!")
				.pointAt(util.vector().topOf(3, 1, 3))
				.placeNearTarget();

		builder.idle(40);

		builder.overlay().showText(30)
				.text("催生器也支持其它构件")
				.placeNearTarget();

		Vec3 motion = util.vector().of(0, -0.08, 0);

		builder.world().createItemEntity(
				util.vector().centerOf(2, 1, 2),
				motion,
				CmiMechanism.STONE.get().getDefaultInstance()
		);
		builder.world().createItemEntity(
				util.vector().centerOf(3, 1, 2),
				motion,
				CmiMechanism.COPPER.get().getDefaultInstance()
		);
		builder.world().createItemEntity(
				util.vector().centerOf(4, 1, 2),
				motion,
				CmiMechanism.ANDESITE.get().getDefaultInstance()
		);
		builder.world().createItemEntity(
				util.vector().centerOf(5, 1, 2),
				motion,
				CmiMechanism.GOLD.get().getDefaultInstance()
		);

		builder.idle(40);

		builder.overlay().showOutline(
				PonderPalette.RED,
				NebulaSceneBuilder.OBJECT,
				util.select().fromTo(1, 0, 1, 5, 0, 5),
				45
		);

		builder.overlay().showText(45)
				.text("只有周围5x5范围内的方块才能被转换")
				.pointAt(util.vector().topOf(3, 1, 3))
				.placeNearTarget();

		builder.markAsFinished();
	}
}