package top.nebula.cmi.mixin;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import earth.terrarium.adastra.common.systems.OxygenApiImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = OxygenApiImpl.class)
public class OxygenApiMixin {

	/**
	 * @author dayp308
	 * @reason 添加下界为缺氧维度
	 */
	@Inject(method = "hasOxygen(Lnet/minecraft/resources/ResourceKey;)Z",
			at = @At("RETURN"),
			cancellable = true,
			remap = false
	)
	public void exampleMod$hasOxygen(ResourceKey<Level> level, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValueZ() && level != Level.NETHER);
	}

	@Inject(method = "entityTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
			cancellable = true,
			remap = false

	)
	public void exampleMod$entityTick(ServerLevel level, LivingEntity entity, CallbackInfo ci) {
		ItemStack helmet = DivingHelmetItem.getWornItem(entity);
		if (helmet.isEmpty()) {
			return;
		}
		List<ItemStack> tanks = BacktankUtil.getAllWithAir(entity);
		if (!tanks.isEmpty()) ci.cancel();
	}
}
