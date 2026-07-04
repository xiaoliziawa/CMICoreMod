package dev.celestiacraft.cmi.api.interaction.context;

import dev.latvian.mods.kubejs.level.BlockContainerJS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IMultiblock;

@AllArgsConstructor
@Getter
public class BlockCraftingOnBuild {
	private final Player player;
	private final BlockContainerJS block;
	private final Level level;
	private final ItemStack entity;
	private final IMultiblock multiblock;
	private final Direction direction;
}