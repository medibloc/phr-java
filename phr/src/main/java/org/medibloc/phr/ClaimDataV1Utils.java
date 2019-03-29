package org.medibloc.phr;

import org.medibloc.panacea.crypto.Hash;
import org.medibloc.phr.ClaimDataV1.*;

import java.util.ArrayList;
import java.util.List;

public class ClaimDataV1Utils {
    public static byte[] hash(ClaimDataV1.Claim claim) {
        // TODO : check type, validate format, merklize data, ...
        return Hash.sha3256(claim.toByteArray());
    }

    public static Claim fillClaim(Claim claim) {
        Claim.Builder builder = Claim.newBuilder(claim);

        builder.setVersion(1);

        builder.clearReceipts();
        builder.addAllReceipts(fillReceiptList(claim.getReceiptsList()));
        builder.clearDiagnoses();
        builder.addAllDiagnoses(fillDiagnosisList(claim.getDiagnosesList()));

        return builder.build();
    }

    public static List<ClaimDataV1.Receipt> fillReceiptList(List<ClaimDataV1.Receipt> receiptList) {
        List<ClaimDataV1.Receipt> resultList = new ArrayList<Receipt>();
        for (ClaimDataV1.Receipt receipt: receiptList) {
            resultList.add(fillReceipt(receipt));
        }
        return resultList;
    }

    public static ClaimDataV1.Receipt fillReceipt(ClaimDataV1.Receipt receipt) {
        ClaimDataV1.Receipt.Builder builder = ClaimDataV1.Receipt.newBuilder(receipt);

        builder.clearFeeItems();
        builder.addAllFeeItems(fillFeeItemList(receipt.getFeeItemsList()));

        return builder.build();
    }

    public static List<ClaimDataV1.FeeItem> fillFeeItemList(List<ClaimDataV1.FeeItem> feeItemList) {
        List<ClaimDataV1.FeeItem> resultList = new ArrayList<ClaimDataV1.FeeItem>();
        for (ClaimDataV1.FeeItem feeItem: feeItemList) {
            resultList.add(fillFeeItem(feeItem));
        }
        return resultList;
    }

    public static ClaimDataV1.FeeItem fillFeeItem(ClaimDataV1.FeeItem feeItem) {
        ClaimDataV1.FeeItem.Builder builder = ClaimDataV1.FeeItem.newBuilder(feeItem);
        return builder.build();
    }

    public static List<ClaimDataV1.Diagnosis> fillDiagnosisList(List<ClaimDataV1.Diagnosis> diagnosisList) {
        List<ClaimDataV1.Diagnosis> resultList = new ArrayList<ClaimDataV1.Diagnosis>();
        for (ClaimDataV1.Diagnosis diagnosis: diagnosisList) {
            resultList.add(fillDiagnosis(diagnosis));
        }
        return resultList;
    }

    public static ClaimDataV1.Diagnosis fillDiagnosis(ClaimDataV1.Diagnosis diagnosis) {
        ClaimDataV1.Diagnosis.Builder builder = ClaimDataV1.Diagnosis.newBuilder(diagnosis);

        return builder.build();
    }
}
