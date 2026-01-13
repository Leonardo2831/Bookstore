//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import Books.Library;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String verifySeeBooks = "Não";

        do {
            System.out.println("Deseja ver os livros disponíveis? (Sim/Não)");
            verifySeeBooks = input.next();

            if(verifySeeBooks.equalsIgnoreCase("Sim")){
                Library library = new Library("src/database/books.txt");

                try {
                    library.loadBooks(input);
                } catch (Exception err){
                    System.err.println(err.getMessage());
                    System.out.println("Ocorreu um erro ao carregar os livros, tente novamente mais tarde ");
                }

            } else {
                System.out.println("Obrigado por visitar nossa livraria!");
            }

        } while(verifySeeBooks.equals("Sim"));
    }
}