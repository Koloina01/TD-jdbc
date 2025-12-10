import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        List<Category> allCategories = dataRetriever.getAllCategories();
        System.out.println(allCategories);

        int[] pages = {1, 1, 1, 2};
        int[] sizes = {10, 5, 3, 2};

        for (int i = 0; i < pages.length; i++) {
            List<Product> list = dataRetriever.getProductList(pages[i], sizes[i]);
            System.out.println(list);
        }

        String[] namesFilter1 = {null, "HP", "Sony", null};
        String[] categoriesFilter1 = {null, null, "Informatique", null};
        Instant[] minFilter1 = {null, null, null, Instant.parse("2023-01-01T00:00:00Z")};
        Instant[] maxFilter1 = {null, null, null, Instant.parse("2023-12-31T23:59:59Z")};

        for (int i = 0; i < namesFilter1.length; i++) {
            List<Product> list = dataRetriever.getProductsByCriteria(
                namesFilter1[i], 
                categoriesFilter1[i], 
                minFilter1[i], 
                maxFilter1[i]
            );
            System.out.println(list);
        }

        String[] namesFilter2 = {null, "Dell", null};
        String[] categoriesFilter2 = {null, null, "Informatique"};
        Instant[] minFilter2 = {null, null, null};
        Instant[] maxFilter2 = {null, null, null};
        int[] pagesFilter2 = {1, 1, 1};
        int[] sizesFilter2 = {10, 1, 10};

        for (int i = 0; i < namesFilter2.length; i++) {
            List<Product> list = dataRetriever.getProductsByCriteria(
                namesFilter2[i],
                categoriesFilter2[i],
                minFilter2[i],
                maxFilter2[i],
                pagesFilter2[i],
                sizesFilter2[i]
            );
            System.out.println(list);
        }
    }
}

