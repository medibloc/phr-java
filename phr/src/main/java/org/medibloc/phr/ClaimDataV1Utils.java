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

    public static Claim fillClaim(Claim.Builder claimBuilder) {
        claimBuilder.setVersion(1);

        claimBuilder.clearReceipts();
        claimBuilder.addAllReceipts(fillReceiptList(claimBuilder.getReceiptsBuilderList()));
        claimBuilder.clearDiagnoses();
        claimBuilder.addAllDiagnoses(fillDiagnosisList(claimBuilder.getDiagnosesBuilderList()));

        return claimBuilder.build();
    }

    public static List<ClaimDataV1.Receipt> fillReceiptList(List<ClaimDataV1.Receipt.Builder> receiptBuilderList) {
        List<ClaimDataV1.Receipt> resultList = new ArrayList<Receipt>();
        for (ClaimDataV1.Receipt.Builder receiptBuilder: receiptBuilderList) {
            resultList.add(fillReceipt(receiptBuilder));
        }
        return resultList;
    }

    public static ClaimDataV1.Receipt fillReceipt(ClaimDataV1.Receipt.Builder receiptBuilder) {
        receiptBuilder.clearFeeItems();
        receiptBuilder.addAllFeeItems(fillFeeItemList(receiptBuilder.getFeeItemsBuilderList()));

        return receiptBuilder.build();
    }

    public static List<ClaimDataV1.FeeItem> fillFeeItemList(List<ClaimDataV1.FeeItem.Builder> feeItemBuilderList) {
        List<ClaimDataV1.FeeItem> resultList = new ArrayList<ClaimDataV1.FeeItem>();
        for (ClaimDataV1.FeeItem.Builder feeItemBuilder: feeItemBuilderList) {
            resultList.add(fillFeeItem(feeItemBuilder));
        }
        return resultList;
    }

    public static ClaimDataV1.FeeItem fillFeeItem(ClaimDataV1.FeeItem.Builder feeItemBuilder) {
        return feeItemBuilder.build();
    }

    public static List<ClaimDataV1.Diagnosis> fillDiagnosisList(List<ClaimDataV1.Diagnosis.Builder> diagnosisBuilderList) {
        List<ClaimDataV1.Diagnosis> resultList = new ArrayList<ClaimDataV1.Diagnosis>();
        for (ClaimDataV1.Diagnosis.Builder diagnosisBuilder: diagnosisBuilderList) {
            resultList.add(fillDiagnosis(diagnosisBuilder));
        }
        return resultList;
    }

    public static ClaimDataV1.Diagnosis fillDiagnosis(ClaimDataV1.Diagnosis.Builder diagnosisBuilder) {
        return diagnosisBuilder.build();
    }
}
