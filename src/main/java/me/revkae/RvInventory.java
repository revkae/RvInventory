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
import java.util.stream.Collectors;

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

    public RvInventory removeRow(int index) {
        if (index > rowAmount || index < 0) return this;

        int startSlot = index * columnAmount;
        int endSlot = startSlot + columnAmount;

        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            inventory.setItem(j, new ItemStack(Material.AIR));
            num++;
        }
        return this;
    }

    public RvInventory removeRows(int... indexes) {
        for (int index : indexes) {
            removeRow(index);
        }
        return this;
    }

    public RvInventory setRow(int index, RvRow row) {
        if (index > rowAmount || index < 0) return this;

        int startSlot = index * columnAmount;
        int endSlot = startSlot + columnAmount;

        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            inventory.setItem(j, row.getItem(num));
            num++;
        }
        return this;
    }

    public RvInventory setRows(RvRow row, int... indexes) {
        for (int index : indexes) {
            setRow(index, row);
        }
        return this;
    }

    public RvInventory removeColumn(int index) {
        if (columnAmount < index || index < 0) return this;

        int startSlot = index;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            inventory.setItem(j, new ItemStack(Material.AIR));
            num++;
        }
        return this;
    }

    public RvInventory removeColumns(int... indexes) {
        for (int index : indexes) {
            removeColumn(index);
        }
        return this;
    }

    public RvInventory setColumn(int index, RvColumn column) {
        if (columnAmount < index || index < 0) return this;

        int startSlot = index;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            inventory.setItem(j, column.getItem(num));
            num++;
        }
        return this;
    }

    public RvInventory setColumns(RvColumn column, int... indexes) {
        for (int index : indexes) {
            setColumn(index, column);
        }
        return this;
    }

    public RvRow getRow(int index) {
        if (rowAmount < index || index < 0) return null;
        RvRow rvRow = new RvRow();
        int startSlot = index * columnAmount;
        int endSlot = startSlot + columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j++) {
            rvRow.setItem(num, inventory.getItem(j));
            num++;
        }
        return rvRow;
    }

    public List<RvRow> getRows(int... indexes) {
        return Arrays.stream(indexes).mapToObj(this::getRow).collect(Collectors.toList());
    }

    public RvColumn getColumn(int index) {
        if (columnAmount < index || index < 0) return null;
        RvColumn rvColumn = new RvColumn();
        int startSlot = index;
        int endSlot = rowAmount * columnAmount;
        int num = 0;
        for (int j = startSlot; j < endSlot; j+=9) {
            rvColumn.setItem(num, inventory.getItem(j));
            num++;
        }
        return rvColumn;
    }

    public List<RvColumn> getColumns(int... indexes) {
        return Arrays.stream(indexes).mapToObj(this::getColumn).collect(Collectors.toList());
    }

    public boolean isRowEmpty(int index) {
        if (rowAmount < index || index < 0) return false;

        int startSlot = index * columnAmount;
        int endSlot = startSlot + columnAmount;

        boolean found = false;
        for (int j = startSlot; j < endSlot; j++) {
            if (inventory.getItem(j).getType() == Material.AIR) continue;

            found = true;
        }

        return !found;
    }

    public boolean isRowsEmpty(int... indexes) {
        for (int index : indexes) {
            if (!isRowEmpty(index)) return false;
        }
        return true;
    }

    public boolean isColumnEmpty(int index) {
        if (rowAmount < index || index < 0) return false;

        int startSlot = index;
        int endSlot = rowAmount * columnAmount;

        boolean found = false;
        for (int j = startSlot; j < endSlot; j++) {
            if (inventory.getItem(j).getType() == Material.AIR) continue;

            found = true;
        }

        return !found;
    }

    public boolean isColumnsEmpty(int... indexes) {
        for (int index : indexes) {
            if (!isColumnEmpty(index)) return false;
        }
        return true;
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

    public RvInventory fillOut(ItemStack itemStack, int... indexes) {
        List<ItemStack> content = new ArrayList<>(Arrays.asList(getContents()));
        Collections.fill(content, itemStack);
        setContents((ItemStack[]) content.toArray());
        for (int index : indexes) {
            setItem(index, new ItemStack(Material.AIR));
        }
        return this;
    }

    public RvInventory setItem(ItemStack itemStack, int... indexes) {
        for (int index : indexes) {
            setItem(index, itemStack);
        }
        return this;
    }

    public void setLayout(RvLayout layout) {
        inventory.clear();
        layout.init(this);
    }

    public boolean isEmpty(int slot) {
        return inventory.getItem(slot).getType() == Material.AIR;
    }

    public boolean isFull(int slot) {
        return inventory.getItem(slot).getType() != Material.AIR;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
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
