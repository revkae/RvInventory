package me.revkae;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RvInventory {

    private Inventory inventory;
    private final int rowAmount;
    private final int columnAmount = 9;

    public RvInventory(InventoryHolder holder, InventoryType type) {
        this.inventory = Bukkit.createInventory(holder, type);
        this.rowAmount = inventory.getSize() / columnAmount;
    }

    public RvInventory(InventoryHolder holder, InventoryType type, String title) {
        this.inventory = Bukkit.createInventory(holder, type, title);
        this.rowAmount = inventory.getSize() / columnAmount;
    }

    public RvInventory(InventoryHolder holder, int size) {
        this.inventory = Bukkit.createInventory(holder, size);
        this.rowAmount = inventory.getSize() / columnAmount;
    }

    public RvInventory(InventoryHolder holder, int size, String title) {
        this.inventory = Bukkit.createInventory(holder, size, title);
        this.rowAmount = inventory.getSize() / columnAmount;
    }

    public RvInventory removeRow(int i) {
        if (i > rowAmount || i < 0) return this;

        int startSlot = i * columnAmount;
        int endSlot = startSlot + columnAmount;

        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            inventory.setItem(j, new ItemStack(Material.AIR));
            num++;
        }
        return this;
    }

    public RvInventory setRow(int i, RvRow row) {
        if (i > rowAmount || i < 0) return this;

        int startSlot = i * columnAmount;
        int endSlot = startSlot + columnAmount;

        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            inventory.setItem(j, row.getItem(num));
            num++;
        }
        return this;
    }

    public RvInventory removeColumn(int i) {
        if (columnAmount < i || i < 0) return this;

        int startSlot = i;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            inventory.setItem(j, new ItemStack(Material.AIR));
            num++;
        }
        return this;
    }

    public RvInventory setColumn(int i, RvColumn column) {
        if (columnAmount < i || i < 0) return this;

        int startSlot = i;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            inventory.setItem(j, column.getItem(num));
            num++;
        }
        return this;
    }

    public RvRow getRow(int i) {
        if (rowAmount < i || i < 0) return null;
        RvRow rvRow = new RvRow();
        int startSlot = i * columnAmount;
        int endSlot = startSlot + columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            rvRow.setItem(num, inventory.getItem(j));
            num++;
        }
        return rvRow;
    }

    public RvColumn getColumn(int i) {
        if (columnAmount < i || i < 0) return null;
        RvColumn rvColumn = new RvColumn();
        int startSlot = i;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            rvColumn.setItem(num, inventory.getItem(j));
            num++;
        }
        return rvColumn;
    }

    public boolean isRowEmpty(int i) {
        if (rowAmount < i || i < 0) return false;

        int startSlot = i * columnAmount;
        int endSlot = startSlot + columnAmount;

        boolean found = false;
        for (int j = startSlot; j < endSlot; j++) {
            if (inventory.getItem(j).getType() == Material.AIR) continue;

            found = true;
        }

        return !found;
    }

    public boolean isColumnEmpty(int i) {
        if (rowAmount < i || i < 0) return false;

        int startSlot = i;
        int endSlot = rowAmount * columnAmount;

        boolean found = false;
        for (int j = startSlot; j < endSlot; j++) {
            if (inventory.getItem(j).getType() == Material.AIR) continue;

            found = true;
        }

        return !found;
    }

    public RvInventory setTitle(String title) {
        Inventory newInventory = Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), title);
        newInventory.setContents(newInventory.getContents());
        inventory = newInventory;
        return this;
    }

    public RvInventory fillOut(ItemStack itemStack) {
        List<ItemStack> content = new ArrayList<>(Arrays.asList(getContents()));
        Collections.fill(content, itemStack);
        setContents((ItemStack[]) content.toArray());
        return this;
    }

    public RvInventory setItem(ItemStack itemStack, int... indexes) {
        for (int index : indexes) {
            setItem(index, itemStack);
        }
        return this;
    }

    public boolean isEmpty(int slot) {
        return inventory.getItem(slot).getType() == Material.AIR;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void update(Player player) {
        player.updateInventory();
    }

    public Inventory build() {
        return inventory;
    }

    public int getSize() {
        return inventory.getSize();
    }

    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    public void setMaxStackSize(int size) {
        inventory.setMaxStackSize(size);
    }

    public String getName() {
        return inventory.getName();
    }

    public ItemStack getItem(int index) {
        return inventory.getItem(index);
    }

    public void setItem(int index, ItemStack item) {
        inventory.setItem(index, item);
    }

    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
        return inventory.addItem(items);
    }

    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
        return inventory.removeItem(items);
    }

    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    public void setContents(ItemStack[] items) throws IllegalArgumentException {
        inventory.setContents(items);
    }

    public boolean contains(int materialId) {
        return inventory.contains(materialId);
    }

    public boolean contains(Material material) throws IllegalArgumentException {
        return inventory.contains(material);
    }

    public boolean contains(ItemStack item) {
        return false;
    }

    public boolean contains(int materialId, int amount) {
        return inventory.contains(materialId, amount);
    }

    public boolean contains(Material material, int amount) throws IllegalArgumentException {
        return inventory.contains(material, amount);
    }

    public boolean contains(ItemStack item, int amount) {
        return inventory.contains(item, amount);
    }

    public boolean containsAtLeast(ItemStack item, int amount) {
        return inventory.containsAtLeast(item, amount);
    }

    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        return inventory.all(materialId);
    }

    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        return inventory.all(material);
    }

    public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
        return inventory.all(item);
    }

    public int first(int materialId) {
        return inventory.first(materialId);
    }

    public int first(Material material) throws IllegalArgumentException {
        return inventory.first(material);
    }

    public int first(ItemStack item) {
        return inventory.first(item);
    }

    public int firstEmpty() {
        return inventory.firstEmpty();
    }

    public void remove(int materialId) {
        inventory.remove(materialId);
    }

    public void remove(Material material) throws IllegalArgumentException {
        inventory.remove(material);
    }

    public void remove(ItemStack item) {
        inventory.remove(item);
    }

    public void clear(int index) {
        inventory.clear(index);
    }

    public void clear() {
        inventory.clear();
    }

    public List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    public String getTitle() {
        return inventory.getTitle();
    }

    public InventoryType getType() {
        return inventory.getType();
    }

    public InventoryHolder getHolder() {
        return inventory.getHolder();
    }

    public ListIterator<ItemStack> iterator() {
        return inventory.iterator();
    }

    public ListIterator<ItemStack> iterator(int index) {
        return inventory.iterator(index);
    }
}
