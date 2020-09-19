package com.example.revistasuteq.objetos;

import java.io.Serializable;

public class galeys implements Serializable {
    String galley_id;
    String label;
    String file_id;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    String UrlViewGalley;

    public String getGalley_id() {
        return galley_id;
    }

    public void setGalley_id(String galley_id) {
        this.galley_id = galley_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrlViewGalley() {
        return UrlViewGalley;
    }

    public void setUrlViewGalley(String urlViewGalley) {
        UrlViewGalley = urlViewGalley;
    }
}
