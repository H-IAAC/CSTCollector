package br.org.eldorado.cst.collector.model.mapper;

public interface IBattery {
    public int getLevel();
    public boolean isLevelHigherThan(int value);
}
