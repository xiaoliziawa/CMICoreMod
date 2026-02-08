package top.nebula.cmi.common.register;

import com.simibubi.create.foundation.damageTypes.DamageTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import top.nebula.cmi.Cmi;

public class CmiDamageType {
	public static final ResourceKey<DamageType> GRINDER;

	static {
		GRINDER = register("grinder");
	}

	private static ResourceKey<DamageType> register(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, Cmi.loadResource(name));
	}

	public static void bootstrap(BootstapContext<DamageType> context) {
		new DamageTypeBuilder(GRINDER).register(context);
	}

	private static DamageSource source(ResourceKey<DamageType> key, LevelReader level) {
		Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
		return new DamageSource(registry.getHolderOrThrow(key));
	}

	public static DamageSource grinder(Level level) {
		return source(GRINDER, level);
	}
}