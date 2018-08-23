package demo.validation;

import com.sun.istack.internal.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Validation<T> implements ToValidate<T>, ValidationBuilder<T> {

    private static final Validation<?> EMPTY = new Validation<>();

    private T toValid;
    private Predicate<T> valid;

    private Validation(T toValid, Predicate<T> valid) {
        this.toValid = toValid;
        this.valid = valid;
    }

    private Validation() {
        this.toValid = null;
        this.valid = (t) -> true;
    }

    public static <T> ToValidate<T> ifPresent(@Nullable T toValid) {
        return nonNull(toValid) ? of(toValid, (t) -> false) : empty();
    }

    public static <T> ToValidate<T> present(T toValid) {
        if(isNull(toValid)) throw new IllegalArgumentException("argument is null");
        return of(toValid,(t) -> false);
    }

    private static <T> Validation<T> of(T validation, Predicate<T> predicate) {
        return new Validation<>(validation,predicate);
    }

    private static <T> Validation<T> empty() {
        @SuppressWarnings("unchecked")
        Validation<T> validation = (Validation<T>) EMPTY;
        return validation;
    }

    @Override
    public ValidationBuilder<T> valid(Predicate<T> predicate) {
        requireNonNull(predicate);
        return isPresent() ? of(toValid, predicate) : empty();
    }

    @Override
    public ValidationBuilder<T> and(Predicate<T> predicate) {
        requireNonNull(predicate);
        return isPresent() ? of(toValid, valid.and(predicate)) : empty();
    }

    @Override
    public ValidationBuilder<T> or(Predicate<T> predicate) {
        requireNonNull(predicate);
        return isPresent() ? of(toValid, valid.or(predicate)) : empty();
    }

    @Override
    public <T extends Exception> void throwIfNot(Supplier<? extends T> supplier) throws T {
        if (!isValid()) throw supplier.get();
    }

    @Override
    public boolean isValid() {
        return valid.test(toValid);
    }

    private void requireNonNull(Predicate<T> predicate) {
        if(isNull(predicate)) throw new IllegalArgumentException("predicate is null");
    }

    private boolean isPresent() {
        return nonNull(toValid);
    }
}
