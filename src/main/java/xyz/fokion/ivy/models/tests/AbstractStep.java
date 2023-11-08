package xyz.fokion.ivy.models.tests;

public non-sealed abstract class AbstractStep implements Step {
    protected final String name;

    public AbstractStep(String name) {
        this.name = name;
    }


}
