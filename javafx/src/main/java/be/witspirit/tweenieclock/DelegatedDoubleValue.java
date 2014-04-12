package be.witspirit.tweenieclock;


import javafx.beans.value.WritableValue;

public class DelegatedDoubleValue implements WritableValue<Number> {

    private WritableValue<Number> delegate;

    public DelegatedDoubleValue(WritableValue<Number> delegate) {
        this.delegate = delegate;
    }

    public WritableValue<Number> getDelegate() {
        return delegate;
    }

    public void setDelegate(WritableValue<Number> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Number getValue() {
        return delegate.getValue();
    }

    @Override
    public void setValue(Number aDouble) {
        delegate.setValue(aDouble);
    }
}
