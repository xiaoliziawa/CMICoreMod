package dev.celestiacraft.cmi.compat.adastra;

import com.teamresourceful.resourcefullib.common.utils.SaveHandler;
import dev.celestiacraft.cmi.common.recipe.space_elevator_construction.SpaceElevatorConstructionRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceElevatorMaterialStorage extends SaveHandler {
	private static final String ENTRIES_KEY = "Entries";
	private static final String ANCHOR_POS_KEY = "AnchorPos";
	private static final String COUNTS_KEY = "Counts";

	private final Map<Long, List<Integer>> storedMaterials = new HashMap<>();

	@Override
	public void loadData(CompoundTag tag) {
		storedMaterials.clear();
		ListTag entryTags = tag.getList(ENTRIES_KEY, Tag.TAG_COMPOUND);
		for (Tag value : entryTags) {
			CompoundTag entryTag = (CompoundTag) value;
			long anchorKey = NbtUtils.readBlockPos(entryTag.getCompound(ANCHOR_POS_KEY)).asLong();
			ListTag countTags = entryTag.getList(COUNTS_KEY, Tag.TAG_INT);
			List<Integer> counts = new ArrayList<>(countTags.size());
			for (Tag countTag : countTags) {
				counts.add(((IntTag) countTag).getAsInt());
			}
			storedMaterials.put(anchorKey, counts);
		}
	}

	@Override
	public void saveData(CompoundTag tag) {
		ListTag entryTags = new ListTag();
		for (Map.Entry<Long, List<Integer>> entry : storedMaterials.entrySet()) {
			CompoundTag entryTag = new CompoundTag();
			entryTag.put(ANCHOR_POS_KEY, NbtUtils.writeBlockPos(net.minecraft.core.BlockPos.of(entry.getKey())));
			ListTag countTags = new ListTag();
			for (Integer count : entry.getValue()) {
				countTags.add(IntTag.valueOf(count));
			}
			entryTag.put(COUNTS_KEY, countTags);
			entryTags.add(entryTag);
		}
		tag.put(ENTRIES_KEY, entryTags);
	}

	@Override
	public boolean isDirty() {
		return true;
	}

	public static SpaceElevatorMaterialStorage read(ServerLevel level) {
		return read(level.getDataStorage(), SpaceElevatorMaterialStorage::new, "cmi_space_elevator_materials");
	}

	public static int[] getStoredCounts(ServerLevel level, net.minecraft.core.BlockPos anchorPos, int size) {
		List<Integer> counts = read(level).getOrCreate(anchorPos, size);
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			result[i] = i < counts.size() ? counts.get(i) : 0;
		}
		return result;
	}

	public static boolean hasAllMaterials(ServerLevel level, net.minecraft.core.BlockPos anchorPos, SpaceElevatorConstructionRecipe recipe) {
		int[] counts = getStoredCounts(level, anchorPos, recipe.ingredients().size());
		for (int i = 0; i < recipe.ingredients().size(); i++) {
			if (counts[i] < recipe.ingredients().get(i).count()) {
				return false;
			}
		}
		return true;
	}

	public static void clear(ServerLevel level, net.minecraft.core.BlockPos anchorPos) {
		read(level).storedMaterials.remove(anchorPos.asLong());
	}

	public static StoreResult storeFromInventory(ServerPlayer player, net.minecraft.core.BlockPos anchorPos, SpaceElevatorConstructionRecipe recipe) {
		if (player.isCreative() || player.isSpectator()) {
			return StoreResult.NOTHING_TO_STORE;
		}

		SpaceElevatorMaterialStorage storage = read(player.serverLevel());
		List<Integer> counts = storage.getOrCreate(anchorPos, recipe.ingredients().size());
		Inventory inventory = player.getInventory();
		boolean movedAny = false;

		for (int ingredientIndex = 0; ingredientIndex < recipe.ingredients().size(); ingredientIndex++) {
			SpaceElevatorConstructionRecipe.IngredientEntry entry = recipe.ingredients().get(ingredientIndex);
			int stored = ingredientIndex < counts.size() ? counts.get(ingredientIndex) : 0;
			int remaining = Math.max(0, entry.count() - stored);
			if (remaining <= 0) {
				continue;
			}

			for (int slot = 0; slot < inventory.getContainerSize() && remaining > 0; slot++) {
				ItemStack stack = inventory.getItem(slot);
				if (!entry.ingredient().test(stack)) {
					continue;
				}

				int moved = Math.min(remaining, stack.getCount());
				if (moved <= 0) {
					continue;
				}

				stack.shrink(moved);
				remaining -= moved;
				stored += moved;
				movedAny = true;
			}

			counts.set(ingredientIndex, stored);
		}

		if (!movedAny) {
			return StoreResult.NOTHING_TO_STORE;
		}
		return hasAllMaterials(player.serverLevel(), anchorPos, recipe) ? StoreResult.COMPLETE : StoreResult.STORED;
	}

	private List<Integer> getOrCreate(net.minecraft.core.BlockPos anchorPos, int size) {
		List<Integer> counts = storedMaterials.computeIfAbsent(anchorPos.asLong(), key -> new ArrayList<>());
		while (counts.size() < size) {
			counts.add(0);
		}
		if (counts.size() > size) {
			counts.subList(size, counts.size()).clear();
		}
		return counts;
	}

	public enum StoreResult {
		STORED("text.cmi.space_elevator.store_success"),
		COMPLETE("text.cmi.space_elevator.store_complete"),
		NOTHING_TO_STORE("text.cmi.space_elevator.store_nothing");

		private final String translationKey;

		StoreResult(String translationKey) {
			this.translationKey = translationKey;
		}

		public String translationKey() {
			return translationKey;
		}
	}
}
