package com.gmail.rabrg96.squad.dataset;

import java.util.List;

public final class Dataset {

    private List<Article> data;
    private String version;

    public List<Article> getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "data=" + data +
                ", version='" + version + '\'' +
                '}';
    }
}
