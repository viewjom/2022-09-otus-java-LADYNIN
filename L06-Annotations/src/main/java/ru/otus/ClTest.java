package ru.otus;

public class ClTest {

    private boolean before;
    private boolean test;
    private boolean after;

    public ClTest(boolean before, boolean test, boolean after) {
        this.before = before;
        this.test = test;
        this.after = after;
    }

    @Before
    public boolean methodBefore() {
        this.before = true;
        return this.before;
    }

    @Test
    public boolean methodTest() {
        if (this.before) {
            this.test = true;
        }
        return this.test;
    }

    @After
    public boolean methodAfter() {
        if (this.test) {
            this.after = true;
        }
        return this.after;
    }
}
