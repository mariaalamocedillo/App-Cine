import java.time.LocalDate;
import java.time.LocalTime;

public final class Data {

    public static final Long GENERAL_ID = 1L;

    public static final String CLIENT_NAME = "Ana";
    public static final Long CLIENT_TELEPHONE = 644877774L;
    public static final String CLIENT_EMAIL = "ana@gmail.com";
    public static final String CLIENT_PSSWD = "anasoyyo";

    public static final String FILM_TITLE = "Drive my car";
    public static final String FILM_SIPNOSIS = "Pese a no ser capaz de recuperarse de un drama personal, Yusuke Kafuku, actor y director de teatro, acepta montar la obra ";
    public static final String FILM_DIRECTOR = "Ryusuke Hamaguchi";
    public static final int FILM_DURATION = 96;
    public static final String FILM_STUDIO = "Bitters End";
    public static final String FILM_POSTER = "https://www.ecartelera.com/carteles/17000/17056/006.jpg";
    public static final boolean FILM_SHOWING = true;

    public static final String ROOM_NAME = "Hercules";

    public static final LocalDate PROJ_DATE = LocalDate.of(2022, 10, 28);
    public static final LocalTime PROJ_HOUR = LocalTime.of(16, 0);

    public static final String PRICE_NAME = "Normal";
    public static final String PRICE_CONDITION = "Entrada general";
    public static final Double PRICE_PRICE = 9.5;

    public static final String SEAT_LETTER_1 = "A";
    public static final String SEAT_NUMBER_1 = "1";
    public static final String SEAT_LETTER_2 = "A";
    public static final String SEAT_NUMBER_2 = "2";
    public static final String SEAT_LETTER_3 = "B";
    public static final String SEAT_NUMBER_3 = "1";

    public static final boolean BOOKING_ACTIVE = true;
    public static final boolean BOOKING_NOT_ACTIVE = false;
    public static final boolean BOOKING_BOOKED = true;
    public static final boolean BOOKING_NOT_BOOKED = false;
    public static final boolean BOOKING_PAID = true;
    public static final boolean BOOKING_NOT_PAID = false;


    private Data() {
    }
}
