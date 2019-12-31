package org.stormroboticsnj.ui.display;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.stormroboticsnj.models.Whoosh;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Whoosh>> selected = new MutableLiveData<List<Whoosh>>();

    public void select(List<Whoosh> w) {
        selected.setValue(w);
    }

    public LiveData<List<Whoosh>> getSelected() {
        return selected;
    }
}
