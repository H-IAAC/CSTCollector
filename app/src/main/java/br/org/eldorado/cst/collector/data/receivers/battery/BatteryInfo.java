package br.org.eldorado.cst.collector.data.receivers.battery;

import br.org.eldorado.cst.collector.data.receivers.location.LocationInfo;
import br.org.eldorado.cst.collector.model.mapper.IBattery;

public class BatteryInfo implements IBattery {
    private int level = 0;

    public BatteryInfo() {
    }

    public BatteryInfo(int level) {
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean isLevelHigherThan(int value) {
        return (level > value);
    }

    @Override
    public IBattery clone() {
        try {
            return (IBattery) super.clone();
        } catch (CloneNotSupportedException e) {
            return new BatteryInfo(this.level);
        }
    }
}
