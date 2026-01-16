package com.Nuaah.NRogueLikeSurvivalBox.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("removal")
public class ItemBrownBag extends Item {
    public ItemBrownBag() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack boxStack = player.getItemInHand(hand);

        if (!world.isClientSide && boxStack.hasTag()) {
            CompoundTag tag = boxStack.getTag();

            // "Contents" という名前のリストタグを取得 (10はCompoundTagを示す)
            if (tag.contains("LootTable", 9)) {
                ListTag itemList = tag.getList("LootTable", 10);

                for (int i = 0; i < itemList.size(); i++) {
                    CompoundTag itemEntry = itemList.getCompound(i);
                    String id = itemEntry.getString("Name");
                    int count = itemEntry.getByte("Count"); // または getInt

                    // 文字列からアイテムを探してプレイヤーに渡す
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));

                    if (item != Items.AIR) {
                        ItemStack stack = new ItemStack(item,count);
                        player.drop(stack, true);
//                        player.addItem(new ItemStack(item, count));
                    }
                }
                // 使用後に箱を減らす場合
                boxStack.shrink(1);
                return InteractionResultHolder.consume(boxStack);
            }
        }

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return;
        String id = tag.getString("EntityNameID");
        if (!id.isEmpty()){
            Component line;
            line = Component.empty().append(Component.literal("ENTITY : ")).append(Component.translatable("entity." + id.replace(":",".")));
            tooltip.add(line);
            tooltip.add(Component.translatable("tooltip.loot_bag.click.open").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        }
    }
}
