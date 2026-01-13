package Books;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Library {
    private String pathBooks;

    public Library(String pathBook) {
        this.pathBooks = pathBook;
    }

    public void loadBooks(Scanner input) throws Exception {
        Path path = Paths.get(this.pathBooks);

        if(!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            String booksString = "1;O Senhor dos Anéis;J.R.R. Tolkien;1954\n2;1984;George Orwell;1949\n3;Dom Quixote;Miguel de Cervantes;1605";

            Files.writeString(path, booksString);
        }

        String contentFile = Files.readString(path);
        String[] linesBooks = contentFile.split("\n");

        for(String line : linesBooks){
            String[] bookInfo = line.split(";");

            for(int i = 0; i < bookInfo.length; i++){
                if(i == bookInfo.length - 1){
                    System.out.println(bookInfo[i]);
                } else {
                    System.out.print(bookInfo[i] + " ");
                }
            }
        }
    }
}
