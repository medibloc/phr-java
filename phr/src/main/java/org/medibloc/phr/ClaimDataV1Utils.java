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

    public static ClaimDataV1.Claim fillClaim(ClaimDataV1.Claim claim) {
        ClaimDataV1.Claim.Builder builder = ClaimDataV1.Claim.newBuilder(claim);

        builder.setVersion(1);

        builder.clearReceipts();
        builder.addAllReceipts(fillReceiptList(claim.getReceiptsList()));
        builder.clearPrescriptions();
        builder.addAllPrescriptions(fillPrescriptionList(claim.getPrescriptionsList()));

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

    public static List<ClaimDataV1.Prescription> fillPrescriptionList(List<ClaimDataV1.Prescription> prescriptionList) {
        List<ClaimDataV1.Prescription> resultList = new ArrayList<ClaimDataV1.Prescription>();
        for (ClaimDataV1.Prescription prescription: prescriptionList) {
            resultList.add(fillPrescription(prescription));
        }
        return resultList;
    }

    public static ClaimDataV1.Prescription fillPrescription(ClaimDataV1.Prescription prescription) {
        ClaimDataV1.Prescription.Builder builder = ClaimDataV1.Prescription.newBuilder(prescription);

        builder.clearPrescriptionItems();
        builder.addAllPrescriptionItems(fillPrescriptionItemList(prescription.getPrescriptionItemsList()));

        return builder.build();
    }

    public static List<ClaimDataV1.PrescriptionItem> fillPrescriptionItemList(List<ClaimDataV1.PrescriptionItem> prescriptionItemList) {
        List<ClaimDataV1.PrescriptionItem> resultList = new ArrayList<ClaimDataV1.PrescriptionItem>();
        for (ClaimDataV1.PrescriptionItem prescriptionItem: prescriptionItemList) {
            resultList.add(fillPrescriptionItem(prescriptionItem));
        }
        return resultList;
    }

    public static ClaimDataV1.PrescriptionItem fillPrescriptionItem(ClaimDataV1.PrescriptionItem prescriptionItem) {
        ClaimDataV1.PrescriptionItem.Builder builder = ClaimDataV1.PrescriptionItem.newBuilder(prescriptionItem);
        return builder.build();
    }
}
