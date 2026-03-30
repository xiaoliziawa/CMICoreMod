package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.cmi.common.advancement.academic_cost.AcademicCostTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class CmiAdvanmentTrigger {
	public static final AcademicCostTrigger ACADEMIC_COST = new AcademicCostTrigger();

	public static void register() {
		CriteriaTriggers.register(ACADEMIC_COST);
	}
}