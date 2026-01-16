package com.Nuaah.NRogueLikeSurvivalBox.gui.container;

import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoul;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulProvider;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.PlayerStatusPuzzleData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.PlayerStatusPuzzleProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class StatusPuzzleManu extends AbstractContainerMenu {
    private final PlayerStatusPuzzleData augmentInv;
    private final Player player;

    public StatusPuzzleManu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, new PlayerStatusPuzzleData(playerInv.player));
        // 実際はここでCapabilityを取得する処理が入ります
    }

    public StatusPuzzleManu(int containerId, Inventory playerInventory, PlayerStatusPuzzleData augmentInv) {
        super(NRogueLikeSurvivalBoxContainerTypes.STATUS_PUZZLE_MENU.get(), containerId);
        this.augmentInv = augmentInv;
        this.player = playerInventory.player;
        ItemStackHandler handler = augmentInv.getInventory();

        // 1. 強化スロット (5x5) の配置
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                this.addSlot(new SlotItemHandler(handler, col + row * 5, 44 + col * 18, 8 + row * 18));
            }
        }

        // 2. プレイヤーの通常インベントリ (3x9)
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public int getLegacySoul(){
        int legacySoul = player.getCapability(LegacySoulProvider.LEGACY_SOUL)
                .map(LegacySoul::getSoul) // ここで中身をintに変換
                .orElse(0);

        return legacySoul;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index >= 20) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            int containerSlots = augmentInv.getInventory().getSlots();
            if (index < containerSlots) {
                if (!this.moveItemStackTo(original, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(original, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (original.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
                augmentInv.getInventory().setStackInSlot(index,ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
                augmentInv.getInventory().setStackInSlot(index,original);
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
