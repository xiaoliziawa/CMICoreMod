package dev.celestiacraft.cmi.common.advancement.academic_cost;

import com.google.gson.JsonObject;
import dev.celestiacraft.cmi.Cmi;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class AcademicCostTrigger extends SimpleCriterionTrigger<AcademicCostTrigger.Instance> {
	public static final ResourceLocation ID = Cmi.loadResource("academic_cost");

	@Override
	protected @NotNull Instance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext context) {
		return new Instance(predicate);
	}

	public void trigger(ServerPlayer player) {
		trigger(player, (instance) -> {
			return true;
		});
	}

	@Override
	public @NotNull ResourceLocation getId() {
		return ID;
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		public Instance(ContextAwarePredicate player) {
			super(ID, player);
		}
	}
}