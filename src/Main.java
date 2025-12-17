// Main.java — Students version
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    static int[][][] data = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int i = 0; i < months.length; i++) {
            String filename = "Data_Files/" + months[i] + ".txt";
            Scanner sc = null;
            try {
                sc = new Scanner(Paths.get(filename));
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length != 3) continue;
                    int day, profit;

                    try {
                        day = Integer.parseInt(parts[0].trim());
                        profit = Integer.parseInt(parts[2].trim());

                    } catch (Exception e) {
                        continue;
                    }
                    String comm = parts[1].trim();
                    if (day < 1 || day > DAYS) continue;
                    int commIndex = -1;
                    for (int m = 0; m < commodities.length; m++) {

                        if (commodities[m].equals(comm)) {
                            commIndex = m;
                            break;
                        }
                    }
                    if (commIndex != -1) {
                        data[i][day - 1][commIndex] = profit;
                    }
                }
            } catch (Exception e) {
            } finally {
                if (sc != null) {
                    sc.close();
                }
            }}}

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        return "DUMMY";
    }

    public static int totalProfitOnDay(int month, int day) {
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {
        return 1234;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}