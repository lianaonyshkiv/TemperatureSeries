package ua.edu.ucu.tempseries;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final int minTemp = -273;

    private double[] temperatures = {};

    private int occupancy;
    private int capacity;


    public TemperatureSeriesAnalysis() {
        capacity = 0;
        occupancy = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double temperature : temperatureSeries) {
            if (temperature < minTemp) {
                throw new InputMismatchException();
            }
        }
        temperatures = temperatureSeries;
        capacity = temperatureSeries.length;
        occupancy = capacity;
    }

    public double average() {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (double temperature : temperatures) {
            sum += temperature;
        }
        ;
        return sum / occupancy;
    }

    public double deviation() {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        double deviation = 0;
        double avr = average();
        for (double temperature : temperatures) {
            deviation += Math.pow(temperature - avr, 2);
        }
        return Math.sqrt(deviation / occupancy);
    }

    private double MaxMinComparisons(String param) {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        double min = temperatures[0];
        double max = temperatures[0];
        for (double temperature : temperatures) {
            min = Math.min(min, temperature);
            max = Math.max(max, temperature);
        }
        if (param.equals("min")) {
            return min;
        } else {
            return max;
        }
    }

    public double min() {
        return MaxMinComparisons("min");
    }

    public double max() {
        return MaxMinComparisons("max");
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        double difference = Math.abs(temperatures[0] - tempValue);
        double closestValue = 0;
        for (double temperature : temperatures) {
            double currentDifferent = Math.abs(temperature - tempValue);
            difference = Math.min(difference, currentDifferent);
            if (difference > currentDifferent || (difference == currentDifferent && currentDifferent > 0)) {
                closestValue = temperature;
            }
        }
        return closestValue;
    }

    public double[] Comparisons(double tempValue, String param) {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        TemperatureSeriesAnalysis mins = new TemperatureSeriesAnalysis();
        TemperatureSeriesAnalysis maxs = new TemperatureSeriesAnalysis();
        for (double temperature : temperatures) {
            if (temperature < tempValue) {
                mins.addTemps(temperature);
            } else {
                maxs.addTemps(temperature);
            }
        }
        if (param.equals("less")) {
            return mins.temperatures;
        } else {
            return maxs.temperatures;
        }
    }

    public double[] findTempsLessThen(double tempValue) {
        return Comparisons(tempValue, "less");
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return Comparisons(tempValue, "greater");
    }

    public TempSummaryStatistics summaryStatistics() {
        if (occupancy == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < minTemp) {
                throw new InputMismatchException();
            }
            if (capacity == 0) {
                capacity = 1;
                temperatures = Arrays.copyOf(temperatures, capacity);
            }
            if (occupancy < capacity) {
                temperatures[occupancy] = temp;
            } else {
                int index = capacity;
                capacity *= 2;
                temperatures = Arrays.copyOf(temperatures, capacity);
                temperatures[index] = temp;
            }
            occupancy += 1;
        }
        return occupancy;
    }

}
