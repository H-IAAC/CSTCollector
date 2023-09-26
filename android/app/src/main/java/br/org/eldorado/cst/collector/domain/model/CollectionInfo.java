package br.org.eldorado.cst.collector.domain.model;

public class CollectionInfo {
    private final long uuid, startDate, endDate;
    private final int numberOfItems;
    private final int synced;

    public CollectionInfo(long uuid, long startDate, long endDate, int numberOfItems, int synced) {
        this.uuid = uuid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfItems = numberOfItems;
        this.synced = synced;
    }

    public long getUuid() {
        return uuid;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getSyncedState() {
        return synced;
    }
}
