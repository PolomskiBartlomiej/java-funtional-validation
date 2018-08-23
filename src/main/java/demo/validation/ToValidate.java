package demo.validation;

import java.util.function.Predicate;

public interface ToValidate<T> {

    ValidationBuilder<T> valid(Predicate<T> predicate);
}
