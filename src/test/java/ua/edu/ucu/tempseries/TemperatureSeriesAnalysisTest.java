package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {
    private static TemperatureSeriesAnalysis empty;
    private static TemperatureSeriesAnalysis withoutElements;
    private static TemperatureSeriesAnalysis oneElement;
    private static TemperatureSeriesAnalysis standart;
    private static TemperatureSeriesAnalysis oppositeData;
    private static TemperatureSeriesAnalysis tooSmallData;

    @BeforeClass
    public static void setUp() {
        empty = new TemperatureSeriesAnalysis(new double[]{});
        withoutElements = new TemperatureSeriesAnalysis();
        oneElement = new TemperatureSeriesAnalysis(new double[]{-1.0});
        standart = new TemperatureSeriesAnalysis(new double[]{3.0, -5.0, 1.0, -8.0});
        oppositeData = new TemperatureSeriesAnalysis(new double[]{-2.0, 2.0});
    }

    @Test(expected = InputMismatchException.class)
    public void testSetUpError() {
        tooSmallData = new TemperatureSeriesAnalysis(new double[]{-3.0, 2.0, -284.0, 34.2, 27.1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTemperatureSummaryException() {
        TempSummaryStatistics result = empty.summaryStatistics();
        TempSummaryStatistics actualResult = withoutElements.summaryStatistics();
//        double expResult = empty.maxMinComparisons("min");
//        double expResultClosest = empty.findTempClosestToValue(12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinMaxComparisons() {
        double expResult = empty.maxMinComparisons("min");
    }

    @Test
    public void testTemperatureSummaryStatistics() {
        TempSummaryStatistics actualResultStandart = standart.summaryStatistics();
        TempSummaryStatistics expResultStandart = new TempSummaryStatistics(-2.25, 4.4370598373, -8.0, 3.0);
        assertEquals(actualResultStandart, expResultStandart);
        TempSummaryStatistics actualResultOneElement = oneElement.summaryStatistics();
        TempSummaryStatistics expResultOneElement = new TempSummaryStatistics(-1.0, 0.0, -1.0, -1.0);
        assertEquals(actualResultOneElement, expResultOneElement);
        TempSummaryStatistics actualResultOpposite = oppositeData.summaryStatistics();
        TempSummaryStatistics expResultOpposite = new TempSummaryStatistics(0.0, 2.0, -2.0, 2.0);
        assertEquals(actualResultOpposite, expResultOpposite);
    }

    @Test
    public void testClosestToValue() {
        double actualResultStandart = standart.findTempClosestToValue(12);
        double expResultStandart = 3.0;
        assertEquals(expResultStandart, actualResultStandart, 0.00001);
        double actualResultOneElement = oneElement.findTempClosestToValue(12);
        double expResultOneElement = -1.0;
        assertEquals(expResultOneElement, actualResultOneElement, 0.00001);
        double actualResultOpposite = oppositeData.findTempClosestToZero();
        double expResultOpposite = 2.0;
        assertEquals(expResultOpposite, actualResultOpposite, 0.00001);
    }

    @Test
    public void testFindTempLessThan() {
        double[] actualResultStandart = standart.findTempsLessThen(12);
        double[] expResultStandart = new double[]{3.0, -5.0, 1.0, -8.0};
        assertArrayEquals(expResultStandart, actualResultStandart, 0.00001);
        double[] actualResultOneElement = oneElement.findTempsLessThen(1);
        double[] expResultOneElement = new double[]{-1.0};
        assertArrayEquals(expResultOneElement, actualResultOneElement, 0.00001);
        double[] actualResultOpposite = oppositeData.findTempsLessThen(0);
        double[] expResultOpposite = new double[]{-2.0};
        assertArrayEquals(expResultOpposite, actualResultOpposite, 0.00001);
    }



    @Test
    public void testFindTempGreaterThan() {
        double[] actualResultStandart = standart.findTempsGreaterThen(12);
        double[] expResultStandart = new double[]{};
        assertArrayEquals(expResultStandart, actualResultStandart, 0.00001);
        double[] actualResultOneElement = oneElement.findTempsGreaterThen(1);
        double[] expResultOneElement = new double[]{};
        assertArrayEquals(expResultOneElement, actualResultOneElement, 0.00001);
        double[] actualResultOpposite = oppositeData.findTempsGreaterThen(0);
        double[] expResultOpposite = new double[]{2.0};
        assertArrayEquals(expResultOpposite, actualResultOpposite, 0.00001);
    }

    @Test
    public void testEqualsSummaryStatistics() {
        TemperatureSeriesAnalysis actualResult = standart;
        TempSummaryStatistics expResult = standart.summaryStatistics();
        assertNotEquals(expResult, actualResult);
    }

    @Test
    public void testEqualsHashCode() {
        TempSummaryStatistics expResult = new TempSummaryStatistics(-1.0, 2.0, 3.0, 5.0);
        TempSummaryStatistics actualResult = new TempSummaryStatistics(-2.0, -1.0, 3.0, 2.0);
        assertNotEquals(expResult.hashCode(), actualResult.hashCode());
    }
}
