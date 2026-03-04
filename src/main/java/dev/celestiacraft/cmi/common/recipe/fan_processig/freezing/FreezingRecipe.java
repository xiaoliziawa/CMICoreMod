package dev.celestiacraft.cmi.common.recipe.fan_processig.freezing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import dev.celestiacraft.cmi.common.register.CmiCreateRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FreezingRecipe extends ProcessingRecipe<FreezingWrapper> {
	public FreezingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		super(CmiCreateRecipe.FREEZING, params);
	}

	@Override
	public boolean matches(FreezingWrapper inv, Level worldIn) {
		if (inv.isEmpty()) {
			return false;
		}
		return ingredients.get(0).test(inv.getItem(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 12;
	}
}