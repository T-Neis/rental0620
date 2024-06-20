import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private int discountPercent;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate, List<LocalDate> holidays) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.chargeDays = calculateChargeDays(holidays);
        this.preDiscountCharge = calculatePreDiscountCharge();
        this.discountAmount = calculateDiscountAmount();
        this.finalCharge = calculateFinalCharge();
    }

    private int calculateChargeDays(List<LocalDate> holidays) {
        int count = 0;
        for (int i = 1; i <= rentalDays; i++) {
            LocalDate currentDate = checkoutDate.plusDays(i);
            boolean isHoliday = holidays.contains(currentDate);
            boolean isWeekend = currentDate.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
            if ((tool.isWeekdayCharge() && !isWeekend && !isHoliday) || (tool.isWeekendCharge() && isWeekend) || (tool.isHolidayCharge() && isHoliday)) {
                count++;
            }
        }
        return count;
    }

    private double calculatePreDiscountCharge() {
        return chargeDays * tool.getDailyCharge();
    }

    private double calculateDiscountAmount() {
        return preDiscountCharge * discountPercent / 100.0;
    }

    private double calculateFinalCharge() {
        return preDiscountCharge - discountAmount;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DecimalFormat currencyFormatter = new DecimalFormat("$#,##0.00");
        DecimalFormat percentFormatter = new DecimalFormat("0%");
        return String.format("Tool code: %s%nTool type: %s%nTool brand: %s%nRental days: %d%nCheck out date: %s%nDue date: %s%nDaily rental charge: %s%nCharge days: %d%nPre-discount charge: %s%nDiscount percent: %s%nDiscount amount: %s%nFinal charge: %s",
                tool.getCode(), tool.getType(), tool.getBrand(), rentalDays, dateFormatter.format(checkoutDate), dateFormatter.format(dueDate), currencyFormatter.format(tool.getDailyCharge()), chargeDays, currencyFormatter.format(preDiscountCharge), percentFormatter.format(discountPercent / 100.0), currencyFormatter.format(discountAmount), currencyFormatter.format(finalCharge));
    }
}
