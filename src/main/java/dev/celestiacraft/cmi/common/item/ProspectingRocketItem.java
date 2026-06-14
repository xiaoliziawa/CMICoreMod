package dev.celestiacraft.cmi.common.item;

import dev.celestiacraft.cmi.common.entity.prospecting_rocket.ProspectingRocketEntity;
import dev.celestiacraft.cmi.common.entity.prospecting_rocket.ProspectingRocketTier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProspectingRocketItem extends Item implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final ProspectingRocketTier tier;
	private final Supplier<EntityType<?>> entityType;

	public ProspectingRocketItem(ProspectingRocketTier tier, Supplier<EntityType<?>> entityType, Properties properties) {
		super(properties);
		this.tier = tier;
		this.entityType = entityType;
	}

	public ProspectingRocketTier getTier() {
		return tier;
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		if (context.getClickedFace() != Direction.UP) {
			return InteractionResult.PASS;
		}

		BlockPos pos = context.getClickedPos();
		Entity entity = entityType.get().create(level);
		if (!(entity instanceof ProspectingRocketEntity rocket)) {
			return InteractionResult.PASS;
		}

		Vec3 click = context.getClickLocation();
		rocket.setPos(pos.getX() + 0.5D, click.y, pos.getZ() + 0.5D);
		rocket.setYRot(context.getHorizontalDirection().getOpposite().toYRot());
		level.addFreshEntity(rocket);
		level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

		ItemStack stack = context.getItemInHand();
		if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
			stack.shrink(1);
		}
		return InteractionResult.CONSUME;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private GeoItemRenderer<ProspectingRocketItem> renderer;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null) {
					renderer = new GeoItemRenderer<>(new ProspectingRocketItemModel());
				}
				return renderer;
			}
		});
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}
