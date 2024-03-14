package commppetterm.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Triple class
 * @param <A> Type of the first part of the triple
 * @param <B> Type of the second part of the triple
 * @param <C> Type of the third part of the triple
 */
public final class Triple<A, B, C> {
    /**
     * First part of the triple
     */
    @NotNull A a;

    /**
     * Second part of the triple
     */
    @NotNull B b;

    /**
     * Third part of the triple
     */
    @NotNull C c;

    /**
     * Initializes a new triple
     * @param a The first part of the triple
     * @param b The second part of the triple
     * @param c The third part of the triple
     */
    public Triple(@NotNull A a, @NotNull B b, @NotNull C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * @return the first part of the triple
     */
    public @NotNull A a() {
        return this.a;
    }

    /**
     * @return the second part of the triple
     */
    public @NotNull B b() {
        return b;
    }

    /**
     * @return the third part of the triple
     */
    public @NotNull C c() {
        return c;
    }

    /**
     * @param a the value to set for the first part of the triple
     */
    public void a(@NotNull A a) {
        this.a = a;
    }

    /**
     * @param b the value to set for the second part of the triple
     */
    public void b(@NotNull B b) {
        this.b = b;
    }

    /**
     * @param c the value to set for the third part of the triple
     */
    public void c(@NotNull C c) {
        this.c = c;
    }

    /**
     * Tests if a list contains a triple
     * @param list The list
     * @param s The triple to search for
     * @return {@code true} if the triple was found
     */
    public boolean hasA(@NotNull List<Triple<A, B, C>> list) {
        for (Triple<A, B, C> triple : list) {
            if (triple.a().equals(this.a)) {
                return true;
            }
        }

        return false;
    }
}
