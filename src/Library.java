import java.awt.*;
import java.util.*;

public class Library {

    public static void menu(){
        System.out.println("1) Add new book");
        System.out.println("2) Show all books");
        System.out.println("3) Show Books availability");
        System.out.println("4) Change Book availability");
        System.out.println("5) Show Book info");
        System.out.println("6) Exit");
    }

    public static String selectGenres(ArrayList<String> genresList, Scanner scanner) throws InvalidGenre{
        int genreSelected;
        for (int i = 0; i < genresList.size(); i++) {
            System.out.println(i+1+")" + genresList.get(i));
        }
        System.out.println("Select the genre");
        genreSelected = scanner.nextInt()-1;
        scanner.nextLine();
        if (genreSelected>=genresList.size()){
            throw new InvalidGenre("Select a valid genre");
        }else if(genreSelected<0){
            throw new InvalidGenre("Select a valid genre");
        }
            else{
            if (genresList.get(genreSelected) != null){
                return  genresList.get(genreSelected);
            }else{
                throw new InvalidGenre("Select a valid genre");
            }
        }

    }

    public static void showAllBooks(HashMap<String,Book> catalog){
        for(String items : catalog.keySet()){
            System.out.println("ID: "+items+" Title: "+ catalog.get(items).getTitle());
        }
    }

    public static void showBooksAvailability(HashMap<String,Book> catalog){
        for (String items : catalog.keySet()){
            if (catalog.get(items).isAvailability() == true){
                System.out.println(catalog.get(items).getTitle() + " -> Available");
            }else {
                System.out.println(catalog.get(items).getTitle() + " -> Not Available");
            }

        }
    }

    public static String getIdBookToUpdate(HashMap<String,Book> catalog, String title) throws InvalidBook{
        String aux ="";
        for (String item : catalog.keySet()){
            if (Objects.equals(catalog.get(item).getTitle(), title)){
                aux = item;
                break;
            }
        }
        if (aux != null){
            return aux;
        }
        else{
            throw new InvalidBook("The book doesn't exist");
        }


    }
    public static String showBookInfo(HashMap<String,Book> catalog ,String title) throws InvalidBook{
        String aux ="";
        for (String item : catalog.keySet()){
            if (Objects.equals(catalog.get(item).getTitle(), title)){
                aux = catalog.get(item).toString();
                break;
            }
        }
        if (aux != null){
            return aux;
        }
        else{
            throw new InvalidBook("The book doesn't exist");
        }
    }

    public static String menuOptionSelected(Scanner scanner) throws InvalidOption{
        int menuOption;
        menuOption = scanner.nextInt();
        scanner.nextLine();
        if (menuOption <= 0){
            throw new InvalidOption("Invalid option");
        } else if (menuOption > 6) {
            throw new InvalidOption("Invalid option");
        }
        else {
            return String.valueOf(menuOption);
        }
    }




    public static void main(String[] args){
        HashMap<String,Book> catalog = new HashMap<>();
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Terror");
        genres.add("Comedia");
        genres.add("Drama");
        genres.add("Romance");
        genres.add("Scifi");
        genres.add("Other");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;


        while (!exit){

            String bookId;
            int menuOption=0;
            String title;
            String author;
            String genre;
            boolean availability;
            System.out.println("========================WELCOME TO THE LIBRARY========================");
            System.out.println("What do you want to do?");
            menu();
            try{
               menuOption = Integer.parseInt(menuOptionSelected(scanner));
            }catch (InvalidOption e){
                System.out.println(e.getMessage());
            }
            switch (menuOption){
                case 1:
                    try{
                        System.out.println("========================ADD NEW BOOK============================");
                        System.out.println("Write the ID of the book");
                        bookId = scanner.nextLine();
                        System.out.println("Write the title of the book:");
                        title = scanner.nextLine();
                        System.out.println("Write the author of the book");
                        author = scanner.nextLine();
                        System.out.println("What is the genre of the book?");
                        genre = selectGenres(genres,scanner);
                        catalog.put(bookId,new Book(bookId,title,author,genre,true));
                    }catch (InvalidGenre e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("================SHOW ALL BOOKS===================================");
                    showAllBooks(catalog);
                    break;

                case 3:
                    System.out.println("=================SHOW BOOK AVAILABILITY============================");
                    showBooksAvailability(catalog);
                    break;

                case 4:
                    String aux ="";
                    try {
                        System.out.println("====================CHANGE BOOK AVAILABILITY============================");
                        showAllBooks(catalog);
                        System.out.println("Write the title of the book you want to change the availability: ");
                        title = scanner.nextLine();
                        aux = getIdBookToUpdate(catalog, title);
                        catalog.get(aux).setAvailability(!catalog.get(aux).isAvailability());
                    }catch (InvalidBook e){
                        System.out.println(e.getMessage());
                    }
                    break;


                case 5:
                    try {
                        System.out.println("=======================SHOW BOOK INFO===================================");
                        showAllBooks(catalog);
                        System.out.println("Write the title of the book you want to see the info: ");
                        title = scanner.nextLine();
                        System.out.println(showBookInfo(catalog,title));
                    }catch (InvalidBook e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting");
                    exit = true;
                    break;
            }
        }
        scanner.close();
    }
}
