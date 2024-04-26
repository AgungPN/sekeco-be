package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.enums.ProfitShared;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class InvoiceTourOrderResponse {

    public static Map<ProfitShared, Object> groupByProfitSharingType(List<OrderDetailsResponse> data) {
        Map<Long, List<OrderDetailsResponse>> groupedDataPurchase = new HashMap<>();
        Map<String ,List<OrderDetailsResponse>> groupedDataSharingAmount = new HashMap<>();
        List<OrderDetailsResponse> groupedDataNone = new ArrayList<>();
        for (OrderDetailsResponse item : data) {
            if (item.getProfitSharedType().equals(ProfitShared.PERCENTAGE)) {
                groupedDataPurchase.computeIfAbsent(item.getProfitSharing(), k -> new ArrayList<>()).add(item);
            } else if (item.getProfitSharedType().equals(ProfitShared.SHARING_AMOUNT)) {
                groupedDataSharingAmount.computeIfAbsent(item.getProductId().getBarcode(), k -> new ArrayList<>()).add(item);
            } else {
                groupedDataNone.add(item);
            }
        }
        Map<ProfitShared, Object> groupedData = new HashMap<>();
        groupedData.put(ProfitShared.PERCENTAGE, groupedDataPurchase);
        groupedData.put(ProfitShared.SHARING_AMOUNT, groupedDataSharingAmount);
        groupedData.put(ProfitShared.NONE, groupedDataNone);

        return groupedData;
    }

}
