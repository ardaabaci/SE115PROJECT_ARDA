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


    public static void loadData() {
        for(int i=0; i<months.length; i++) {
            String filename = "Data_Files/" + months[i] + ".txt";
            Scanner sc = null;
            try {
                sc = new Scanner(Paths.get(filename));
                while(sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if(line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if(parts.length != 3) continue;
                    int day, profit;

                    try {
                        day = Integer.parseInt(parts[0].trim());
                        profit = Integer.parseInt(parts[2].trim());

                    } catch (Exception e) {
                        continue;
                    }
                    String comm = parts[1].trim();
                    if(day<1 || day>DAYS) continue;
                    int commIndex = -1;
                    for(int m=0; m<commodities.length; m++) {

                        if(commodities[m].equals(comm)) {
                            commIndex=m;
                            break;
                        }
                    }
                    if(commIndex != -1) {
                        data[i][day-1][commIndex] = profit;
                    }
                }
            } catch (Exception e) {
            } finally {
                if(sc != null) {
                    sc.close();
                }
            }
        }
    }


    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month>=MONTHS) {
            return "INVALID_MONTH";
        }
        int maxProfit = Integer.MIN_VALUE;
        String bestComm = "";

        for(int i=0; i<COMMS; i++) {
            int monthlyTotal = 0;

            for(int j=0; j<DAYS; j++) {
                monthlyTotal += data[month][j][i];
            }

            if(monthlyTotal > maxProfit) {
                maxProfit = monthlyTotal;
                bestComm = commodities[i];
            }}
        return bestComm + " " + maxProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month < 0 || month >= MONTHS || day<1 || day>DAYS) {
            return -99999;
        }
            int sum=0;
            for(int i=0; i<COMMS; i++) {
                sum+=data[month][day - 1][i];
            }
            return sum;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay){
        if(commodity == null) return -99999;
        if(fromDay<1 || fromDay>DAYS || toDay<1 || toDay>DAYS || fromDay>toDay) {
            return -99999;
        }
        int commIndex = -1;
        for(int i = 0; i < commodities.length; i++) {
            if(commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if(commIndex == -1) {
            return -99999;
        }
        int totalProfit = 0;

        for(int i=0; i< MONTHS; i++) {
            for(int j=fromDay; j<=toDay; j++) {
                totalProfit += data[i][j-1][commIndex];

        }

            }return totalProfit;}


    public static int bestDayOfMonth(int month) {
        if(month < 0 || month >= MONTHS) {
            return -1;
        }
        int maxProfit = Integer.MIN_VALUE;
        int bestDay = 1;

        for(int i=1; i <= DAYS; i++) {
            int dailyTotal = 0;

        for(int j=0; j < COMMS; j++) {
            dailyTotal += data[month][i - 1][j];
        }
            if(dailyTotal > maxProfit) {
                maxProfit = dailyTotal;
                bestDay = i;}}
        return bestDay;}

    public static String bestMonthForCommodity(String comm) {
        if(comm==null){
            return "INVALID_COMMODITY";
        }
        int cIndex=-1;
        for(int i=0; i<commodities.length; i++) {
            if (commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if(cIndex==-1) {
            return "INVALID_COMMODITY";
        }
        int maxProfit = Integer.MIN_VALUE;
        int bestMonthIndex = 0;

        for(int i=0; i<MONTHS; i++) {
            int monthlyTotal = 0;

            for(int j=0; j<DAYS; j++) {
                monthlyTotal += data[i][j][cIndex];
            }
            if(monthlyTotal>maxProfit) {
                maxProfit = monthlyTotal;
                bestMonthIndex = i;
            }
        }
        return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        if(comm==null) {
            return -1;
        }
        int cIndex=-1;
        for(int i=0; i<COMMS; i++) {
            if(commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if(cIndex == -1) {
            return -1;
        }
        int maxStreak = 0;
        int currentStreak = 0;

        for(int i=0; i<MONTHS; i++) {
            for(int j=0; j<DAYS; j++) {
                if(data[i][j][cIndex]<0) {
                    currentStreak++;
                    if(currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                }else{
                        currentStreak = 0;
                    }
                    }
                }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        if(comm == null) {
            return -1;
        }
        int cIndex = -1;
        for(int i=0; i<commodities.length; i++) {
            if(commodities[i].equals(comm)) {
                cIndex = i;
                break;
            }
        }
        if(cIndex == -1) {
            return -1;
        }
        int count = 0;
        for(int i=0; i<MONTHS; i++) {
            for(int j=0; j<DAYS; j++) {
                if(data[i][j][cIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }
    public static int biggestDailySwing(int month) {
        if(month < 0 || month >= MONTHS) {
            return -99999;
        }
        int maxSwing = 0;

        for(int i=0; i<DAYS-1; i++) {
            int currentDayTotal = 0;
            int nextDayTotal = 0;

            for(int j=0; j<COMMS; j++) {
                currentDayTotal += data[month][i][j];
                nextDayTotal += data[month][i + 1][j];
            }
            int difference = currentDayTotal - nextDayTotal;

            if(difference < 0) {
                difference = -difference;
            }
            if(difference > maxSwing) {
                maxSwing = difference;
            }
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        if(month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int maxWeekProfit = Integer.MIN_VALUE;
        int bestWeekNumber = 1;
        for(int w=0; w<4; w++) {
            int weekTotal = 0;

            for(int d=w * 7; d<(w + 1)*7; d++) {
                for(int c=0; c < COMMS; c++) {
                    weekTotal += data[month][d][c];
                }
            }

            if(weekTotal > maxWeekProfit) {
                maxWeekProfit = weekTotal;
                bestWeekNumber = w + 1;
    }}
        return "Week " + bestWeekNumber;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}