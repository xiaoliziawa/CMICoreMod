package dev.celestiacraft.cmi.common.entity.space_elevator;

import dev.celestiacraft.cmi.Cmi;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

@Mod.EventBusSubscriber(modid = Cmi.MODID, value = Dist.CLIENT)
public class SpaceElevatorEntity extends Entity implements GeoEntity {
	private static final EntityDataAccessor<BlockPos> ANCHOR_POS = SynchedEntityData.defineId(SpaceElevatorEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> HAS_ANCHOR = SynchedEntityData.defineId(SpaceElevatorEntity.class, EntityDataSerializers.BOOLEAN);
	private static final double ANCHOR_Y_OFFSET = 1.01D;
	private static CameraType previous;
	private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

	public SpaceElevatorEntity(EntityType<? extends Entity> type, Level level) {
		super(type, level);
	}

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
		if (event.getEntity().getVehicle() instanceof SpaceElevatorEntity) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) {
			return;
		}
		Entity vehicle = mc.player.getVehicle();
		if (vehicle instanceof SpaceElevatorEntity) {
			if (previous == null) {
				previous = mc.options.getCameraType();
				mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
			}
		} else if (previous != null) {
			mc.options.setCameraType(previous);
			previous = null;
		}
	}

	public void setAnchor(BlockPos anchorPos) {
		this.entityData.set(ANCHOR_POS, anchorPos.immutable());
		this.entityData.set(HAS_ANCHOR, true);
		snapToAnchor();
	}

	public boolean hasAnchor() {
		return this.entityData.get(HAS_ANCHOR);
	}

	public BlockPos getAnchor() {
		return this.entityData.get(ANCHOR_POS);
	}

	public boolean isAnchoredTo(BlockPos pos) {
		return hasAnchor() && getAnchor().equals(pos);
	}

	private void snapToAnchor() {
		if (!hasAnchor()) {
			return;
		}
		BlockPos anchorPos = getAnchor();
		setPos(anchorPos.getX() + 0.5D, anchorPos.getY() + ANCHOR_Y_OFFSET, anchorPos.getZ() + 0.5D);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide() && hasAnchor() && level().getBlockState(getAnchor()).isAir()) {
			discard();
			return;
		}
		setDeltaMovement(Vec3.ZERO);
		if (hasAnchor()) {
			snapToAnchor();
		}
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
		registrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
		state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(ANCHOR_POS, BlockPos.ZERO);
		this.entityData.define(HAS_ANCHOR, false);
	}

	@Override
	protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
		if (tag.contains("AnchorPos")) {
			setAnchor(BlockPos.of(tag.getLong("AnchorPos")));
		}
	}

	@Override
	protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
		if (hasAnchor()) {
			tag.putLong("AnchorPos", getAnchor().asLong());
		}
	}

	@Override
	protected boolean canAddPassenger(@NotNull Entity entity) {
		return getPassengers().isEmpty();
	}

	@Override
	public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResult.PASS;
		}
		if (!level().isClientSide()) {
			if (player.isPassenger()) {
				return InteractionResult.FAIL;
			}
			return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.FAIL;
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		return false;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public void positionRider(@NotNull Entity passenger, @NotNull MoveFunction function) {
		if (hasPassenger(passenger)) {
			function.accept(passenger, getX(), getY() - 0.5D, getZ());
		}
	}
}
