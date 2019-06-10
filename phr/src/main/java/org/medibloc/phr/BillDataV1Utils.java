package org.medibloc.phr;

import org.medibloc.panacea.crypto.Hash;
import org.medibloc.phr.BillDataV1.*;

import java.util.ArrayList;
import java.util.List;

public class BillDataV1Utils {
    public static byte[] hash(BillDataV1.Bill bill) {
        // TODO : check type, validate format, merklize data, ...
        return Hash.sha3256(bill.toByteArray());
    }

    public static Bill fillBill(Bill.Builder billBuilder) {
        billBuilder.setVersion(1);

        List<BillDataV1.Receipt> filledReceiptList = fillReceiptList(billBuilder.getReceiptsBuilderList());
        billBuilder.clearReceipts();
        billBuilder.addAllReceipts(filledReceiptList);

        List<BillDataV1.Diagnosis> filledDiagnosisList = fillDiagnosisList(billBuilder.getDiagnosesBuilderList());
        billBuilder.clearDiagnoses();
        billBuilder.addAllDiagnoses(filledDiagnosisList);

        return billBuilder.build();
    }

    public static List<BillDataV1.Receipt> fillReceiptList(List<BillDataV1.Receipt.Builder> receiptBuilderList) {
        List<BillDataV1.Receipt> resultList = new ArrayList<Receipt>();
        for (BillDataV1.Receipt.Builder receiptBuilder: receiptBuilderList) {
            resultList.add(fillReceipt(receiptBuilder));
        }
        return resultList;
    }

    public static BillDataV1.Receipt fillReceipt(BillDataV1.Receipt.Builder receiptBuilder) {
        List<BillDataV1.FeeItem> filledFeeItemList = fillFeeItemList(receiptBuilder.getFeeItemsBuilderList());
        receiptBuilder.clearFeeItems();
        receiptBuilder.addAllFeeItems(filledFeeItemList);

        return receiptBuilder.build();
    }

    public static List<BillDataV1.FeeItem> fillFeeItemList(List<BillDataV1.FeeItem.Builder> feeItemBuilderList) {
        List<BillDataV1.FeeItem> resultList = new ArrayList<BillDataV1.FeeItem>();
        for (BillDataV1.FeeItem.Builder feeItemBuilder: feeItemBuilderList) {
            resultList.add(fillFeeItem(feeItemBuilder));
        }
        return resultList;
    }

    public static BillDataV1.FeeItem fillFeeItem(BillDataV1.FeeItem.Builder feeItemBuilder) {
        return feeItemBuilder.build();
    }

    public static List<BillDataV1.Diagnosis> fillDiagnosisList(List<BillDataV1.Diagnosis.Builder> diagnosisBuilderList) {
        List<BillDataV1.Diagnosis> resultList = new ArrayList<BillDataV1.Diagnosis>();
        for (BillDataV1.Diagnosis.Builder diagnosisBuilder: diagnosisBuilderList) {
            resultList.add(fillDiagnosis(diagnosisBuilder));
        }
        return resultList;
    }

    public static BillDataV1.Diagnosis fillDiagnosis(BillDataV1.Diagnosis.Builder diagnosisBuilder) {
        return diagnosisBuilder.build();
    }
}
