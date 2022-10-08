package com.microlan.rushhandingoffline.OfflineModel;

import android.net.Uri;

public class AllCatogeryModel {

    String CatogerId,Catogeryname,Catogeryimage;
    Uri imagepaths;

    public AllCatogeryModel(String catogerId, String catogeryname, String catogeryimage) {

        CatogerId = catogerId;
        Catogeryname = catogeryname;
        Catogeryimage = catogeryimage;
    }
    public AllCatogeryModel(String catogeryname, String catogeryimage ) {

        Catogeryname = catogeryname;
        Catogeryimage = catogeryimage;
        imagepaths = imagepaths;

    }


    public String getCatogerId() {
        return CatogerId;
    }

    public void setCatogerId(String catogerId) {
        CatogerId = catogerId;
    }

    public String getCatogeryname() {
        return Catogeryname;
    }

    public void setCatogeryname(String catogeryname) {
        Catogeryname = catogeryname;
    }

    public String getCatogeryimage() {
        return Catogeryimage;
    }

    public void setCatogeryimage(String catogeryimage) {
        Catogeryimage = catogeryimage;
    }
}
