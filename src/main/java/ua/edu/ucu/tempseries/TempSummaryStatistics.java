package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics {
    static final double DELTA = 1e-6;
    private final double avgTemp;
    private final double devTemp;
    private final double minTemp;
    private final double maxTemp;

    TempSummaryStatistics(double avgTemp, double devTemp,
                          double minTemp, double maxTemp) {
        this.avgTemp = avgTemp;
        this.devTemp = devTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TempSummaryStatistics)) {
            return false;
        }
        return equalsTwoParam(avgTemp,
                ((TempSummaryStatistics) other).getAvgTemp())
                && equalsTwoParam(devTemp,
                ((TempSummaryStatistics) other).getDevTemp())
                && equalsTwoParam(minTemp,
                ((TempSummaryStatistics) other).getMinTemp())
                && equalsTwoParam(maxTemp,
                ((TempSummaryStatistics) other).getMaxTemp());
    }

    private boolean equalsTwoParam(double paramOne, double paramTwo) {
        return Math.abs(paramOne - paramTwo) < DELTA;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDevTemp() {
        return devTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
