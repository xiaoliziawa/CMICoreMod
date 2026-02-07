package top.nebula.cmi.compat.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import top.nebula.cmi.common.block.belt_grinder.GrindingRecipe;
import top.nebula.cmi.compat.jei.ModJeiPlugin;
import top.nebula.cmi.compat.jei.category.animations.AnimatedBeltGrinder;
import top.nebula.libs.compat.jei.categoty.SimpleJeiCategory;

import java.util.List;

public abstract class GrindingCategory extends CreateRecipeCategory<GrindingRecipe> {
	private static final AnimatedBeltGrinder ANIMATED_BELT_GRINDER = new AnimatedBeltGrinder();

	public GrindingCategory(Info<GrindingRecipe> info) {
		super(info);
	}

	public static final RecipeType<GrindingRecipe> GRINDING_TYPE = ModJeiPlugin.createRecipeType(
			"grinding",
			GrindingRecipe.class
	);

	public static SimpleJeiCategory<GrindingRecipe> builder(IGuiHelper helper) {
		return SimpleJeiCategory.builder(GRINDING_TYPE)
				.setRecipe((builder, recipe, group) -> {
					builder.addSlot(RecipeIngredientRole.INPUT, 44, 5)
							.setBackground(getRenderedSlot(), -1, -1)
							.addIngredients(recipe.getIngredients().get(0));

					List<ProcessingOutput> results = recipe.getRollableResults();
					int i = 0;
					for (ProcessingOutput output : results) {
						int xOffset = i % 2 == 0 ? 0 : 19;
						int yOffset = (i / 2) * -19;
						builder.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
								.setBackground(getRenderedSlot(output), -1, -1)
								.addItemStack(output.getStack())
								.addTooltipCallback(addStochasticTooltip(output));
						i++;
					}
				})
				.setDraw((recipe, view, graphics, mouseX, mouseY) -> {
					AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
					AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);

					ANIMATED_BELT_GRINDER.draw(graphics, 72, 42);
				})
				.build();
	}
}