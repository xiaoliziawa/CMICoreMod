package dev.celestiacraft.cmi.config.common.mbd2;

import dev.celestiacraft.cmi.config.base.ConfigModule;
import net.minecraftforge.common.ForgeConfigSpec;

public class ReinforcedCokeOvenConfig extends ConfigModule {
	public static ForgeConfigSpec.BooleanValue SMOKING_AT_WORKING;
	public static ForgeConfigSpec.IntValue SMOKING_FREQUENCY;

	public ReinforcedCokeOvenConfig(ForgeConfigSpec.Builder builder) {
		super(builder, "reinforced_coke_oven_config", "Reinforced Coke Oven");
	}

	@Override
	protected void addConfigs() {
		SMOKING_AT_WORKING = builder.comment("Reinforced coke oven will smoke at work")
				.define("smoking_at_working", true);

		SMOKING_FREQUENCY = builder.comment("Reinforced coke oven frequency of smoke at work (Tick)")
				.defineInRange("smoking_frequency", 5, 1, 20);
	}
}