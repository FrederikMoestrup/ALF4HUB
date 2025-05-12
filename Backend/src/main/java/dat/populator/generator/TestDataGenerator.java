package dat.populator.generator;

import java.util.List;

public interface TestDataGenerator<T> {
    List<T> generate();
}
