package dev.celestiacraft.cmi.api.server;

import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ReplaceRule {
	private final List<Supplier<Block>> match;
	private final Supplier<Block> result;

	public ReplaceRule(Supplier<Block> match, Supplier<Block> result) {
		this.match = List.of(match);
		this.result = result;
	}

	public ReplaceRule(List<Supplier<Block>> match, Supplier<Block> result) {
		this.match = match;
		this.result = result;
	}

	public boolean matches(Block block) {
		for (Supplier<Block> supplier : match) {
			if (supplier.get() == block) {
				return true;
			}
		}
		return false;
	}

	public Block getResult() {
		return result.get();
	}

	public List<Block> getMatchBlocks() {
		return match.stream().map(Supplier::get).collect(Collectors.toList());
	}
}