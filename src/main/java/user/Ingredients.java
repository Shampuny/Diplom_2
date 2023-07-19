package user;

public class Data {
    private static String[] ingredients = new String[]{"61c0c5a71d1f82001bdaaa77", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"};
    private static String[] invalidIngredients = new String[]{"61c0c6671d1f82001bdaaa6d", "61c0c6644d1f82001bdaaa6d", "61c0c6671d1f33001bdaaa6d"};
    public static String[] getIngredients() {
        return ingredients;
    }

    public static String[] getInvalidIngredients() {
        return invalidIngredients;
    }
}
