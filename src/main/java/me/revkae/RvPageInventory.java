package me.revkae;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RvPageInventory {

    private List<RvInventory> inventories;

    public RvPageInventory() {
        inventories = new ArrayList<>();
    }

    public RvInventory getPage(int index) {
        return inventories.get(index);
    }

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

    public boolean isPageEmpty(int index) {
        return inventories.get(index).isEmpty();
    }

    public boolean isPageFull(int index) {
        return inventories.get(index).isFull();
    }

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

    public List<RvInventory> getPages() {
        return inventories;
    }

    public int getPageAmount() {
        return inventories.size();
    }
}
