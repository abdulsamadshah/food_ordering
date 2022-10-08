package com.microlan.rushhandingoffline.DB;


import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCatogeryModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;
import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.CompanySettingModel;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.OfflineModel.StateModel;
import com.microlan.rushhandingoffline.OfflineModel.USerListLoginModel;

public interface OnDatabaseChangedListener
{
    void onNewDatabaseEntryAdded(AllProductModel recordingItem);

    void onNewDatabaseEntryAddedCart(ItemInCardModel recordingItem);

    void onNewDatabaseEntryAddedCustomer(ALLCustomerModel recordingItem);

    void onNewDatabaseEntryOrderDeta(OrderDataModel recordingItem);

    void onNewDatabaseEntryOrderDetais(OrderDataDetailsModel recordingItem);

    void onNewDatabaseEntryAddress(AllCustomerAddressModel recordingItem);

    void onNewDatabaseEntryallgst(AllGstModel recordingItem);

    void onNewDatabaseEntryallstate(StateModel recordingItem);

    void onNewDatabaseEntryallUserlist(USerListLoginModel recordingItem);

    void onNewDatabaseEntryallsetting(CompanySettingModel recordingItem);

}
