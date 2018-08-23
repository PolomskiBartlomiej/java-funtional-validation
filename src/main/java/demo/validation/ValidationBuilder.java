package demo.validation;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ValidationBuilder<T> {

    ValidationBuilder<T> and(Predicate<T> predicate);

    ValidationBuilder<T> or(Predicate<T> predicate);

    <T extends Exception> void throwIfNot(Supplier<? extends T> supplier ) throws T;

    boolean isValid();
}
