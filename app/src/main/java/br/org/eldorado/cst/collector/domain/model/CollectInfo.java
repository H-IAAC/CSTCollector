package br.org.eldorado.cst.collector.domain.model;

public class CollectInfo {
    private final long startDate, endDate;
    private final int numberOfItems;
    private final boolean synced;

    public CollectInfo(long startDate, long endDate, int numberOfItems, boolean synced) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfItems = numberOfItems;
        this.synced = synced;
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

    public boolean idSynced() {
        return synced;
    }
}
