package top.nebula.cmi.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import top.nebula.cmi.client.block.resource.CmiBlockPartialModel;
import top.nebula.cmi.common.block.belt_grinder.BeltGrinderBlock;
import top.nebula.cmi.common.register.ModBlocks;

public class AnimatedBeltGrinder extends AnimatedKinetics {
	@Override
	public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
		PoseStack matrixStack = graphics.pose();
		matrixStack.pushPose();
		matrixStack.translate(xOffset, yOffset, 0);
		matrixStack.translate(2, 22, 200);
		matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
		matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f + 90f));
		int scale = 25;

		defaultBlockElement(shaft(Direction.Axis.X))
				.rotateBlock(-getCurrentAngle(), 0, 0)
				.scale(scale)
				.render(graphics);

		defaultBlockElement(ModBlocks.BELT_GRINDER.getDefaultState()
				.setValue(BeltGrinderBlock.HORIZONTAL_FACING, Direction.WEST))
				.rotateBlock(0, 0, 0)
				.scale(scale)
				.render(graphics);

		defaultBlockElement(CmiBlockPartialModel.GRINDER_BELT)
				.rotateBlock(0, -90, -90)
				.scale(scale)
				.render(graphics);

		matrixStack.popPose();
	}
}