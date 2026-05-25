package dev.celestiacraft.cmi.common.block.space_elevator_base_console.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.client.render.SpaceElevatorConsoleScreenState;
import dev.celestiacraft.cmi.common.block.space_elevator_base_console.SpaceElevatorBaseConsoleBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SpaceElevatorScreenLayer extends GeoRenderLayer<SpaceElevatorBaseConsoleBlockEntity> {
	private static final ResourceLocation BASE_TEXTURE = Cmi.loadResource("textures/block/space_elevator_base.png");
	private static final String TARGET_BONE = "displays";

	private static final float TEX_WIDTH = 512.0F;
	private static final float TEX_HEIGHT = 512.0F;

	private static final float CMI_U_MIN = 456.0F / TEX_WIDTH;
	private static final float CMI_U_MAX = 484.0F / TEX_WIDTH;
	private static final float CMI_V_MIN = 492.0F / TEX_HEIGHT;
	private static final float CMI_V_MAX = 512.0F / TEX_HEIGHT;

	private static final float NO_ELEVATOR_U_MIN = 456.0F / TEX_WIDTH;
	private static final float NO_ELEVATOR_U_MAX = 484.0F / TEX_WIDTH;
	private static final float NO_ELEVATOR_V_MIN = 472.0F / TEX_HEIGHT;
	private static final float NO_ELEVATOR_V_MAX = 492.0F / TEX_HEIGHT;

	private static final float FLUID_TRANSFER_ACTIVE_U_MIN = 484.0F / TEX_WIDTH;
	private static final float FLUID_TRANSFER_ACTIVE_U_MAX = 512.0F / TEX_WIDTH;
	private static final float FLUID_TRANSFER_ACTIVE_V_MIN = 492.0F / TEX_HEIGHT;
	private static final float FLUID_TRANSFER_ACTIVE_V_MAX = 512.0F / TEX_HEIGHT;

	private static final float FLUID_TRANSFER_FINISHED_U_MIN = 484.0F / TEX_WIDTH;
	private static final float FLUID_TRANSFER_FINISHED_U_MAX = 512.0F / TEX_WIDTH;
	private static final float FLUID_TRANSFER_FINISHED_V_MIN = 472.0F / TEX_HEIGHT;
	private static final float FLUID_TRANSFER_FINISHED_V_MAX = 492.0F / TEX_HEIGHT;

	private static final float LEFT_MONITOR_X_MIN = -22.0F / 16.0F;
	private static final float LEFT_MONITOR_X_MAX = -8.0F / 16.0F;
	private static final float CENTER_MONITOR_X_MIN = -7.0F / 16.0F;
	private static final float CENTER_MONITOR_X_MAX = 7.0F / 16.0F;
	private static final float RIGHT_MONITOR_X_MIN = 8.0F / 16.0F;
	private static final float RIGHT_MONITOR_X_MAX = 22.0F / 16.0F;
	private static final float MONITOR_Y_MIN = 17.0F / 16.0F;
	private static final float MONITOR_Y_MAX = 27.0F / 16.0F;
	private static final float MONITOR_Z = -39.0F / 16.0F - 1.0F / 256.0F;

	public SpaceElevatorScreenLayer(GeoRenderer<SpaceElevatorBaseConsoleBlockEntity> renderer) {
		super(renderer);
	}

	@Override
	public void renderForBone(PoseStack poseStack, SpaceElevatorBaseConsoleBlockEntity animatable, GeoBone bone,
							  RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
							  float partialTick, int packedLight, int packedOverlay) {
		if (!TARGET_BONE.equals(bone.getName())) {
			return;
		}

		VertexConsumer screenBuffer = bufferSource.getBuffer(ForgeRenderTypes.getUnlitTranslucent(BASE_TEXTURE, false));
		Matrix4f pose = poseStack.last().pose();

		if (animatable.isElevatorPresent()) {
			if (SpaceElevatorConsoleScreenState.isFluidTransferActive(animatable.getBlockPos())) {
				addQuad(screenBuffer, pose, LEFT_MONITOR_X_MIN, LEFT_MONITOR_X_MAX, FLUID_TRANSFER_ACTIVE_U_MIN, FLUID_TRANSFER_ACTIVE_U_MAX, FLUID_TRANSFER_ACTIVE_V_MIN, FLUID_TRANSFER_ACTIVE_V_MAX, packedOverlay);
			} else if (SpaceElevatorConsoleScreenState.isFluidTransferFinished(animatable.getBlockPos())) {
				addQuad(screenBuffer, pose, LEFT_MONITOR_X_MIN, LEFT_MONITOR_X_MAX, FLUID_TRANSFER_FINISHED_U_MIN, FLUID_TRANSFER_FINISHED_U_MAX, FLUID_TRANSFER_FINISHED_V_MIN, FLUID_TRANSFER_FINISHED_V_MAX, packedOverlay);
			} else {
				addQuad(screenBuffer, pose, LEFT_MONITOR_X_MIN, LEFT_MONITOR_X_MAX, CMI_U_MIN, CMI_U_MAX, CMI_V_MIN, CMI_V_MAX, packedOverlay);
			}
			addQuad(screenBuffer, pose, CENTER_MONITOR_X_MIN, CENTER_MONITOR_X_MAX, CMI_U_MIN, CMI_U_MAX, CMI_V_MIN, CMI_V_MAX, packedOverlay);
			return;
		}

		addQuad(screenBuffer, pose, LEFT_MONITOR_X_MIN, LEFT_MONITOR_X_MAX, NO_ELEVATOR_U_MIN, NO_ELEVATOR_U_MAX, NO_ELEVATOR_V_MIN, NO_ELEVATOR_V_MAX, packedOverlay);
		addQuad(screenBuffer, pose, CENTER_MONITOR_X_MIN, CENTER_MONITOR_X_MAX, NO_ELEVATOR_U_MIN, NO_ELEVATOR_U_MAX, NO_ELEVATOR_V_MIN, NO_ELEVATOR_V_MAX, packedOverlay);
		addQuad(screenBuffer, pose, RIGHT_MONITOR_X_MIN, RIGHT_MONITOR_X_MAX, NO_ELEVATOR_U_MIN, NO_ELEVATOR_U_MAX, NO_ELEVATOR_V_MIN, NO_ELEVATOR_V_MAX, packedOverlay);
	}

	private static void addQuad(VertexConsumer buffer, Matrix4f pose, float xMin, float xMax, float uMin, float uMax, float vMin, float vMax, int packedOverlay) {
		addVertex(buffer, pose, xMin, MONITOR_Y_MAX, MONITOR_Z, uMax, vMin, LightTexture.FULL_BRIGHT, packedOverlay);
		addVertex(buffer, pose, xMax, MONITOR_Y_MAX, MONITOR_Z, uMin, vMin, LightTexture.FULL_BRIGHT, packedOverlay);
		addVertex(buffer, pose, xMax, MONITOR_Y_MIN, MONITOR_Z, uMin, vMax, LightTexture.FULL_BRIGHT, packedOverlay);
		addVertex(buffer, pose, xMin, MONITOR_Y_MIN, MONITOR_Z, uMax, vMax, LightTexture.FULL_BRIGHT, packedOverlay);
	}

	private static void addVertex(VertexConsumer buffer, Matrix4f pose, float x, float y, float z, float u, float v, int packedLight, int packedOverlay) {
		buffer.vertex(pose, x, y, z)
				.color(1.0F, 1.0F, 1.0F, 1.0F)
				.uv(u, v)
				.overlayCoords(packedOverlay)
				.uv2(packedLight)
				.normal(0.0F, 0.0F, -1.0F)
				.endVertex();
	}
}
