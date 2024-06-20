import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ToolRentalService {
    private List<Tool> tools;
    private List<LocalDate> holidays;

    public ToolRentalService() {
        tools = new ArrayList<>();
        holidays = new ArrayList<>();
        initializeTools();
        initializeHolidays();
    }

    private void initializeTools() {
        tools.add(new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        tools.add(new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        tools.add(new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        tools.add(new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    private void initializeHolidays() {
        holidays.add(LocalDate.of(2021, 7, 4));
        holidays.add(LocalDate.of(2021, 9, 6)); // Labor Day 2021
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, String checkoutDateString) throws IllegalArgumentException {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range 0-100");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate checkoutDate = LocalDate.parse(checkoutDateString, dateFormatter);

        Tool tool = tools.stream()
                .filter(t -> t.getCode().equals(toolCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid tool code"));

        return new RentalAgreement(tool, rentalDays, discountPercent, checkoutDate, holidays);
    }

    public static void main(String[] args) {
        ToolRentalService service = new ToolRentalService();
        try {
            RentalAgreement agreement = service.checkout("LADW", 3, 10, "07/02/20");
            System.out.println(agreement);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
