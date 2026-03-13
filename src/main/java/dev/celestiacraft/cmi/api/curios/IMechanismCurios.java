package dev.celestiacraft.cmi.api.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 构件 Curios Tick 接口
 *
 * <p>
 * 实现该接口的物品在被玩家作为 Curios 饰品佩戴时,
 * 会按照指定的 Tick 间隔周期性调用对应的方法
 * </p>
 *
 * <p>
 * 一般情况下只需要实现
 * {@link #curiosTick(Player, Level)} 即可
 * </p>
 *
 * <p>
 * 如果需要访问当前饰品的 {@link ItemStack},
 * 可以实现 {@link #curiosTick(Player, Level, ItemStack)}
 * </p>
 *
 * <p>
 * Tick 调用仅在服务端执行,
 * 因此通常不需要额外判断
 * {@code level.isClientSide()}
 * </p>
 *
 * <pre>{@code
 * public class CobaltItem extends MechanismItem implements IMechanismCurios {
 *     @Override
 *     public void curiosTick(Player player, Level level) {
 *         player.addEffect(new MobEffectInstance(
 *             MobEffects.MOVEMENT_SPEED,
 *             40,
 *             1,
 *             false,
 *             false
 *         ));
 *     }
 * }
 * }</pre>
 */
public interface IMechanismCurios {
	/**
	 * Curios Tick 回调
	 *
	 * <p>
	 * 当玩家佩戴该物品作为 Curios 饰品时, 
	 * 按照 {@link #tickCheck()} 指定的 Tick 间隔调用
	 * </p>
	 *
	 * <p>
	 * 默认实现为空方法, 
	 * 子类可以重写该方法实现饰品效果
	 * </p>
	 *
	 * @param player 玩家
	 * @param level  世界
	 */
	void curiosTick(Player player, Level level);

	/**
	 * Curios Tick 回调(带ItemStack)
	 *
	 * <p>
	 * 与 {@link #curiosTick(Player, Level)} 类似, 
	 * 但额外提供当前饰品的 {@link ItemStack}
	 * </p>
	 *
	 * <p>
	 * 适用于需要读取 NBT、耐久或其它物品状态的情况
	 * </p>
	 *
	 * <p>
	 * 默认实现会调用
	 * {@link #curiosTick(Player, Level)}
	 * </p>
	 *
	 * @param player 玩家
	 * @param level  世界
	 * @param item   当前饰品 ItemStack
	 */
	default void curiosTick(Player player, Level level, ItemStack item) {
		curiosTick(player, level);
	}

	/**
	 * 获取 Curios Tick 间隔
	 *
	 * <p>
	 * 返回值为 Tick 数, 
	 * 表示两次 {@code curiosTick} 调用之间的间隔
	 * </p>
	 *
	 * <p>
	 * 默认值为 {@code 20}, 
	 * 即每秒执行一次
	 * </p>
	 *
	 * <pre>{@code
	 * @Override
	 * public int tickCheck() {
	 *     // 每两秒触发一次
	 *     return 40;
	 * }
	 * }</pre>
	 *
	 * @return Tick 间隔
	 */
	default int tickCheck() {
		return 20;
	}
}