package Books;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Error;

public class Library {
    private final String PATH_BOOKS;
    Path path;
    Scanner input;
    public List<Book> books;

    public Library(String pathBook) {
        this.PATH_BOOKS = pathBook;
        this.books = new ArrayList<>();
    }

    void loanBook(int bookId){
        for(int i = 0; i < books.size(); i++){
            if(this.books.get(i).id != bookId) {
                if(i == books.size() - 1) System.out.println("Livro não encontrado.");
                continue;
            } else if (this.books.get(i).id == bookId && !this.books.get(i).loan) {
                System.out.println("Você escolheu o livro: " + books.get(i).title + " do autor " + books.get(i).author + ", publicado em " + Book.formatDate(books.get(i).publishDate));

                System.out.println("Digite seu nome para registrar o empréstimo: ");
                books.get(i).userLoan = this.input.next();

                if (!books.get(i).userLoan.isEmpty()) {
                    books.get(i).loan = true;
                    books.get(i).dateLoan = LocalDateTime.now();

                    System.out.println("Livro emprestado com sucesso!");
                } else {
                    System.out.println("É necessário informar um nome para fazer o empréstimo.");
                }
                break;
            } else {
                System.err.println("Livro indisponível para empréstimo.");
            }
        }
    }

    void showBooks(){
        String TACHADO = "\u001b[9m";
        String RESET = "\u001b[0m";

        for(Book book : books){
            if(book.loan) {
                String showBooks = TACHADO + String.valueOf(book.id) + "- " + book.title + " de " + book.author + ", Publicado em: " + Book.formatDate(book.publishDate) + " Emprestado para: " + book.userLoan + "-" + Book.formatDateTime(book.dateLoan) + RESET;
                System.out.println(showBooks);
                continue;
            };

            String showBooks = String.valueOf(book.id) + "- " + book.title + " de " + book.author + ", Publicado em: " + Book.formatDate(book.publishDate);
            System.out.println(showBooks);
        }
    }

    void createBooks(String[] linesBooks){
        this.books = new ArrayList<>();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");

        for(String line : linesBooks){
            try {
                String[] bookInfo = line.split(";");

                boolean hasLoan = bookInfo.length >= 5;
                boolean hasDateHourLoan = bookInfo.length >= 6;

                LocalDateTime dateHourLoan = null;

                if (hasDateHourLoan) {
                    dateHourLoan = LocalDateTime.parse(
                            bookInfo[5].trim(),
                            formatterTime
                    );
                }

                // Sempre passar formato em parse para não haver erros de transformação de string em objetos LocalDate
                Book book = new Book(
                        Integer.parseInt(bookInfo[0]),
                        bookInfo[1],
                        bookInfo[2],
                        LocalDate.parse(bookInfo[3], formatterDate),
                        hasLoan,
                        hasLoan ? bookInfo[4] : "",
                        hasDateHourLoan ? dateHourLoan : null
                );

                books.add(book);
            } catch (Exception err){
                System.out.println("Ocorreu um erro ao criar o livro a partir da linha: " + line);
                throw new Error(err);
            }
        }
    }

    private void writeDocument(){
        try {
            // StringBuilder usado para a concatenação de strings em loops
            StringBuilder contentBook = new StringBuilder();

            for(Book book : books){
                contentBook.append(book.id).append(";").append(book.title).append(";").append(book.author).append(";").append(book.publishDate);

                if(!book.userLoan.isEmpty() && book.dateLoan != null){
                    contentBook.append(";").append(book.userLoan).append(";").append(book.dateLoan);
                }

                contentBook.append("\n");
            }

            Files.writeString(this.path, contentBook.toString());
        } catch (Exception err){
            System.out.println("Ocorreu um erro ao atualizar os livros.");
            throw new Error(err);
        }
    }

    private String[] verifyExistsFile() throws Exception{
        try {
            this.path = Paths.get(this.PATH_BOOKS);

            if(!Files.exists(this.path)) {
                Files.createDirectories(this.path.getParent());
                String booksString = "1;O Senhor dos Anéis;J.R.R. Tolkien;1954\n2;1984;George Orwell;1949\n3;Dom Quixote;Miguel de Cervantes;1605";

                Files.writeString(this.path, booksString);
            }

            String contentFile = Files.readString(this.path);
            return contentFile.split("\n");
        } catch (Exception err){
            throw new Exception("Ocorreu um erro ao acessar o arquivo de livros.");
        }
    }

    public String loadBooks(Scanner input) throws Exception {
        this.input = input;

        String[] linesBooks = this.verifyExistsFile();
        this.createBooks(linesBooks);

        this.showBooks();

        System.out.println("Deseja pegar algum livro emprestado? (Sim/Não)");
        String choiceLoan = this.input.next();

        while(choiceLoan.equalsIgnoreCase("Sim")){
            this.showBooks();

            System.out.println("Digite o ID do livro que deseja pegar emprestado: ");
            int choiceBookId = Integer.parseInt(this.input.next());

            this.loanBook(choiceBookId);

            System.out.println("Deseja pegar mais algum livro emprestado? (Sim/Não)");
            choiceLoan = this.input.next();
        };

        writeDocument();
        return choiceLoan;
    }
}