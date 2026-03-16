package dev.celestiacraft.cmi.common.block.test_multiblock;

import cpw.mods.util.Lazy;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.register.CmiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import dev.celestiacraft.libs.compat.patchouli.multiblock.*;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IMultiblock;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TestMultiblockBlockEntity extends BlockEntity implements IMultiblockProvider {
	public TestMultiblockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	private static final Lazy<IMultiblock> STRUCTURE = Lazy.of(() -> {
		return StructureBuilder.create(new String[][]{
						{
								"AAA",
								"AAA",
								"AAA"
						},
						{
								"A0A",
								"AAA",
								"AAA"
						},
						{
								"AAA",
								"AAA",
								"AAA"
						}
				})
				// 木板
				.define('A', (builder) -> {
					builder.block(Blocks.COBBLESTONE);
				})
				.define('0', (builder) -> {
					builder.block(CmiBlock.TEST_MULTIBLOCK.get());
				})
				.build();
	});

	private final CapabilityHandler capabilityHandler = new CapabilityHandler();

	private final MultiblockHandler MULTIBLOCK = MultiblockHandler.builder(this, (Supplier<IMultiblock>) STRUCTURE)
			.translationKey(String.format("multiblock.building.%s.test_multiblock", Cmi.MODID))
			.renderOffset(0, -1, 0)
			.cacheTicks(20)
			.build();

	@Override
	public MultiblockHandler getMultiblockHandler() {
		return MULTIBLOCK;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
		if (capability == ForgeCapabilities.ITEM_HANDLER) {
			// 结构不全不给输入输出
			if (!isStructureValid()) {
				return LazyOptional.empty();
			}
			return capabilityHandler.itemCapability.cast();
		}

		return super.getCapability(capability, direction);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		capabilityHandler.invalidate();
	}

	private class CapabilityHandler {
		private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
		};

		private final LazyOptional<IItemHandler> itemCapability = LazyOptional.of(() -> {
			return new IItemHandler() {
				@Override
				public int getSlots() {
					return itemHandler.getSlots();
				}

				@Override
				public @NotNull ItemStack getStackInSlot(int slot) {
					return itemHandler.getStackInSlot(slot);
				}

				@Override
				public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
					return itemHandler.insertItem(slot, stack, simulate);
				}

				@Override
				public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
					return itemHandler.extractItem(slot, amount, simulate);
				}

				@Override
				public int getSlotLimit(int slot) {
					return itemHandler.getSlotLimit(slot);
				}

				@Override
				public boolean isItemValid(int slot, @NotNull ItemStack stack) {
					return false;
				}
			};
		});

		private void invalidate() {
			itemCapability.invalidate();
		}
	}
}
