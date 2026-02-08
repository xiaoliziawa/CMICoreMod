package top.nebula.cmi.datagen;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.register.CmiItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Cmi.MODID, helper);
	}

	@Override
	protected void registerModels() {
		simpleItem(CmiItems.NUCLEAR_MECHANISM);
	}

	private ItemModelBuilder simpleItem(ItemEntry<? extends Item> item) {
		String getItemKey = BuiltInRegistries.ITEM.getKey(item.get()).toString();
		String getItemPath = BuiltInRegistries.ITEM.getKey(item.get()).getPath();
		ResourceLocation texture = Cmi.loadResource("item/" + getItemPath);
		ResourceLocation parent = ResourceLocation.withDefaultNamespace("item/generated");

		return withExistingParent(getItemKey, parent)
				.texture("layer0", texture);
	}
}