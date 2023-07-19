package project.main.webstore.converter;

import org.springframework.core.convert.converter.Converter;
import project.main.webstore.domain.item.enums.Category;

public class EnumConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        return Category.of(source);
    }
}
