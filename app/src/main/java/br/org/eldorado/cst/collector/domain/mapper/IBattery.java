package br.org.eldorado.cst.collector.domain.mapper;

public interface IBattery {
    public int getLevel();
    public boolean isLevelHigherThan(int value);
}
