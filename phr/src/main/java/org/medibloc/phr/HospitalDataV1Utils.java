package org.medibloc.phr;

import org.med4j.crypto.Hash;
import org.medibloc.phr.HospitalDataV1.*;

import java.util.ArrayList;
import java.util.List;

public class HospitalDataV1Utils {
    public static byte[] hash(Bill bill) {
        // TODO : check type, validate format, merklize data, ...
        return Hash.sha3256(bill.toByteArray());
    }

    public static HospitalDataV1.Bill fillBill(HospitalDataV1.Bill bill) {
        HospitalDataV1.Bill.Builder builder = HospitalDataV1.Bill.newBuilder(bill);

        builder.setNonce(Nonce.generateNonce());
        builder.setVersion(1);
        builder.setModuleVersion("0.0.1");

        builder.clearReceipts();
        builder.addAllReceipts(fillReceiptList(bill.getReceiptsList()));
        builder.clearPrescriptions();
        builder.addAllPrescriptions(fillPrescriptionList(bill.getPrescriptionsList()));

        return builder.build();
    }

    public static List<Receipt> fillReceiptList(List<HospitalDataV1.Receipt> receiptList) {
        List<HospitalDataV1.Receipt> resultList = new ArrayList<Receipt>();
        for (HospitalDataV1.Receipt receipt: receiptList) {
            resultList.add(fillReceipt(receipt));
        }
        return resultList;
    }

    public static HospitalDataV1.Receipt fillReceipt(HospitalDataV1.Receipt receipt) {
        HospitalDataV1.Receipt.Builder builder = HospitalDataV1.Receipt.newBuilder(receipt);

        builder.setNonce(Nonce.generateNonce());
        builder.clearFeeItems();
        builder.addAllFeeItems(fillFeeItemList(receipt.getFeeItemsList()));

        return builder.build();
    }

    public static List<HospitalDataV1.FeeItem> fillFeeItemList(List<HospitalDataV1.FeeItem> feeItemList) {
        List<HospitalDataV1.FeeItem> resultList = new ArrayList<HospitalDataV1.FeeItem>();
        for (HospitalDataV1.FeeItem feeItem: feeItemList) {
            resultList.add(fillFeeItem(feeItem));
        }
        return resultList;
    }

    public static HospitalDataV1.FeeItem fillFeeItem(HospitalDataV1.FeeItem feeItem) {
        HospitalDataV1.FeeItem.Builder builder = HospitalDataV1.FeeItem.newBuilder(feeItem);

        builder.setNonce(Nonce.generateNonce());

        return builder.build();
    }

    public static List<HospitalDataV1.Prescription> fillPrescriptionList(List<HospitalDataV1.Prescription> prescriptionList) {
        List<HospitalDataV1.Prescription> resultList = new ArrayList<HospitalDataV1.Prescription>();
        for (HospitalDataV1.Prescription prescription: prescriptionList) {
            resultList.add(fillPrescription(prescription));
        }
        return resultList;
    }

    public static HospitalDataV1.Prescription fillPrescription(HospitalDataV1.Prescription prescription) {
        HospitalDataV1.Prescription.Builder builder = HospitalDataV1.Prescription.newBuilder(prescription);

        builder.setNonce(Nonce.generateNonce());
        builder.clearPrescriptionItems();
        builder.addAllPrescriptionItems(fillPrescriptionItemList(prescription.getPrescriptionItemsList()));

        return builder.build();
    }

    public static List<HospitalDataV1.PrescriptionItem> fillPrescriptionItemList(List<HospitalDataV1.PrescriptionItem> prescriptionItemList) {
        List<HospitalDataV1.PrescriptionItem> resultList = new ArrayList<HospitalDataV1.PrescriptionItem>();
        for (HospitalDataV1.PrescriptionItem prescriptionItem: prescriptionItemList) {
            resultList.add(fillPrescriptionItem(prescriptionItem));
        }
        return resultList;
    }

    public static HospitalDataV1.PrescriptionItem fillPrescriptionItem(HospitalDataV1.PrescriptionItem prescriptionItem) {
        HospitalDataV1.PrescriptionItem.Builder builder = HospitalDataV1.PrescriptionItem.newBuilder(prescriptionItem);

        builder.setNonce(Nonce.generateNonce());

        return builder.build();
    }
}
