package user;

public class IngredientsData {
    private static String[] idsIngredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6f"};
    private static String[] idsIncorrectIngredients = {"61c0c5a44d1f82001bdaaa6d", "61c0c5a44d1f82001bdaaa6f"};

    public static String[] getIdsIncorrectIngredients() {
        return idsIncorrectIngredients;
    }

    public static void setIdsIncorrectIngredients(String[] idsIncorrectIngredients) {
        IngredientsData.idsIncorrectIngredients = idsIncorrectIngredients;
    }

    public static String[] getIdsIngredients() {
        return idsIngredients;
    }

    public void setIdsIngredients(String[] idsIngredients) {
        IngredientsData.idsIngredients = idsIngredients;
    }
}
