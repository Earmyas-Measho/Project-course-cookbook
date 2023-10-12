package secfolder;

import java.util.Arrays;
import java.util.List;

public class tag {
    private String name;
    private static final List<String> predefinedTags = Arrays.asList("vegetarian", "vegan", "lactose free",
            "gluten free", "starter", "main course", "dessert", "sweets");

    public tag(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<String> getPredefinedTags() {
        return predefinedTags;
    }
}
