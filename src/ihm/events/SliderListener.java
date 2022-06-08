package ihm.events;

import ihm.Interface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import oop.Coefficient;

public class SliderListener implements ChangeListener<Number> {
    Interface iface;
    Slider slider;
    Coefficient coef;

    public SliderListener(Interface iface, Slider slider, Coefficient coef) {
        this.iface = iface;
        this.slider = slider;
        this.coef = coef;
    }

    @Override
    public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        slider.setValue(Math.ceil((double) newVal * 2) / 2.0d);
        iface.dpt.teacher.setWeighting(coef, (double) newVal);
    }

}
