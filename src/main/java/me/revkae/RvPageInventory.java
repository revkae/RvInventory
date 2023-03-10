package me.revkae;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RvPageInventory {

    private List<RvInventory> inventories;

    public RvPageInventory() {
        inventories = new ArrayList<>();
    }

    public RvPageInventory(RvInventory... rvInventories) {
        inventories = Arrays.asList(rvInventories);
    }

    public RvPageInventory(Inventory... inventories) {
        this.inventories = Arrays.stream(inventories)
                .map(RvInventory::new)
                .collect(Collectors.toList());
    }

    public RvPageInventory(RvInventory rvInventory, int amount) {
        this.inventories = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            inventories.add(rvInventory);
        }
    }

    public RvPageInventory(Inventory inventory, int amount) {
        this.inventories = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            inventories.add(new RvInventory(inventory));
        }
    }

    // GET FEATURES

    public int getPageIndex(RvInventory inventory) {
        int index = 0;
        for (int i = 0; i < inventories.size(); i++) {
            if (inventories.get(i).equals(inventory)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public RvInventory getPage(int index) {
        return inventories.get(index);
    }

    public List<RvInventory> getPages() {
        return inventories;
    }

    public RvInventory getFirstPage() {
        return inventories.get(0);
    }

    public List<RvInventory> getMiddlePages() {
        return inventories.stream()
                .filter(inventory -> !inventory.equals(getFirstPage()))
                .filter(inventory -> !inventory.equals(getLastPage()))
                .collect(Collectors.toList());
    }

    public RvInventory getLastPage() {
        return inventories.get(inventories.size() == 0 ? 0 : inventories.size() - 1);
    }

    public RvInventory getPage(Inventory inventory) {
        return getPages().stream()
                .filter(page -> page.build().equals(inventory))
                .findAny()
                .orElse(null);
    }

    public int getPageAmount() {
        return inventories.size();
    }

    // ADD FEATURES

    public RvPageInventory addPages(RvInventory... rvInventories) {
        for (RvInventory rvInventory : rvInventories) {
            addPage(rvInventory);
        }
        return this;
    }

    public RvPageInventory addPage(RvInventory rvInventory) {
        inventories.add(rvInventory);
        return this;
    }

    public RvPageInventory addPages(RvInventory rvInventory, int amount) {
        for (int i = 0; i < amount; i++) {
            inventories.add(rvInventory);
        }
        return this;
    }

    // REMOVE FEATURES

    public RvPageInventory removePage(int index) {
        inventories.remove(index);
        inventories = inventories.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return this;
    }

    public RvPageInventory removePages(int... indexes) {
        for (int index : indexes) {
            inventories.remove(index);
        }
        inventories = inventories.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return this;
    }

    // QUESTION FEATURES

    public boolean isPageEmpty(int index) {
        return inventories.get(index).isEmpty();
    }

    public boolean isPageFull(int index) {
        return inventories.get(index).isFull();
    }

    public boolean hasPage(Inventory inventory) {
        return getPages().stream().anyMatch(rvInventory -> rvInventory.build().equals(inventory));
    }

    public boolean hasPage(RvInventory rvInventory) {
        return getPages().stream().anyMatch(inv -> inv.equals(rvInventory));
    }

    // SPECIAL FEATURES

    public RvInventory nextPage(RvInventory rvInventory) {
        int page = -1;
        for (int i = 0; i < inventories.size(); i++) {
            if (!inventories.get(i).equals(rvInventory)) continue;

            page = i;
        }

        if (page == -1) {
            System.out.println("This page you have entered as parameter doesn't exist");
            return null;
        }

        if (page + 1 == inventories.size()) {
            System.out.println("Next page doesn't exist");
            return null;
        }

        return inventories.get(page + 1);
    }

    public RvInventory previousPage(RvInventory rvInventory) {
        int page = -1;
        for (int i = 0; i < inventories.size(); i++) {
            if (!inventories.get(i).equals(rvInventory)) continue;

            page = i;
        }

        if (page == -1) {
            System.out.println("This page you have entered as parameter doesn't exist");
            return null;
        }

        if (page == 0) {
            System.out.println("Previous page doesn't exist");
            return null;
        }

        return inventories.get(page - 1);
    }
}
