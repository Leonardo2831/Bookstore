package Books;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Book {
    int id;
    String title;
    String author;
    LocalDate publishDate;

    boolean loan;
    String userLoan;
    LocalDateTime dateLoan;

    static String formatDateTime(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    static String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public Book(int id, String title, String author, LocalDate publishDate, boolean loan, String userLoan, LocalDateTime dateLoan) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.loan = loan;
        this.userLoan = userLoan;
        this.dateLoan = dateLoan;
    }
}