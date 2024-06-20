import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToolRentalServiceTest {
    ToolRentalService service = new ToolRentalService();

    @Test
    public void testCheckoutJAKR() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.checkout("JAKR", 5, 101, "09/03/15");
        });
        assertEquals("Discount percent must be in the range 0-100", exception.getMessage());
    }

    @Test
    public void testCheckoutLADW() {
        RentalAgreement agreement = service.checkout("LADW", 3, 10, "07/02/20");
        assertNotNull(agreement);
        assertEquals("LADW", agreement.getToolCode());
        assertEquals(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("07/05/20", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getDueDate());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(5.97, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.60, agreement.getDiscountAmount(), 0.01);
        assertEquals(5.37, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCheckoutCHNS() {
        RentalAgreement agreement = service.checkout("CHNS", 5, 25, "07/02/15");
        assertNotNull(agreement);
        assertEquals("CHNS", agreement.getToolCode());
        assertEquals(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("07/07/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getDueDate());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(4.47, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(1.12, agreement.getDiscountAmount(), 0.01);
        assertEquals(3.35, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCheckoutJAKD() {
        RentalAgreement agreement = service.checkout("JAKD", 6, 0, "09/03/15");
        assertNotNull(agreement);
        assertEquals("JAKD", agreement.getToolCode());
        assertEquals(LocalDate.parse("09/03/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("09/09/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getDueDate());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(8.97, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        assertEquals(8.97, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCheckoutJAKRNoDiscount() {
        RentalAgreement agreement = service.checkout("JAKR", 9, 0, "07/02/15");
        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals(LocalDate.parse("07/02/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("07/11/15", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getDueDate());
        assertEquals(5, agreement.getChargeDays());
        assertEquals(14.95, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(0.00, agreement.getDiscountAmount(), 0.01);
        assertEquals(14.95, agreement.getFinalCharge(), 0.01);
    }

    @Test
    public void testCheckoutJAKRWithDiscount() {
        RentalAgreement agreement = service.checkout("JAKR", 4, 50, "07/02/20");
        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals(LocalDate.parse("07/02/20", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("07/06/20", DateTimeFormatter.ofPattern("MM/dd/yy")), agreement.getDueDate());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(5.98, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(2.99, agreement.getDiscountAmount(), 0.01);
        assertEquals(2.99, agreement.getFinalCharge(), 0.01);
    }
}
