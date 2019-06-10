package org.medibloc.phr;

import com.google.protobuf.util.JsonFormat;
import org.junit.Test;
import org.medibloc.phr.BillDataV1.*;

public class BillDataV1UtilsTest {
    @Test
    public void testFillBill() throws Exception {
        /*** Bill ***/
        Bill.Builder billBuilder = Bill.newBuilder();
        billBuilder.setBillNo("20181204-S1284");

        /*** Bill.Receipts ***/
        Receipt.Builder receiptBuilder = Receipt.newBuilder();
        receiptBuilder.setReceiptNo("20181204-S1284");
        receiptBuilder.setReceiptType("I");
        receiptBuilder.setPatientNo("12345678");
        receiptBuilder.setPatientName("홍길동");
        receiptBuilder.setCompanyRegistrationNo("11100999");
        receiptBuilder.setTreatmentStartDate("2018-12-06");
        receiptBuilder.setTreatmentEndDate("2018-12-06");
        receiptBuilder.setTreatmentDepartment("피부과");
        receiptBuilder.setTreatmentDepartmentCode("DER");
        receiptBuilder.setTreatmentType("");
        receiptBuilder.setTreatmentTypeCode("");
        receiptBuilder.setCoveredFee("11000");
        receiptBuilder.setUncoveredFee("20000");
        receiptBuilder.setUpperLimitExcess("0");
        receiptBuilder.setPayTotal("31000");
        receiptBuilder.setPatientPayTotal("21000");
        receiptBuilder.setDeductAmount("0");
        receiptBuilder.setAdvancePayAmount("0");
        receiptBuilder.setPayAmount("21000");
        receiptBuilder.setUncollectedPayAmount("0");
        receiptBuilder.setReceiptAmount("21000");
        receiptBuilder.setSurtaxAmount("0");
        receiptBuilder.setCashPayAmount("0");
        receiptBuilder.setCardPayAmount("21000");

        /*** Bill.Receipt.FeeItems ***/
        receiptBuilder.addFeeItems(FeeItem.newBuilder()
                .setFeeItemName("초진 진찰료")
                .setFeeItemCode("")
                .setTreatmentDate("2018-12-06")
                .setCoveredType("")
                .setMedicalChargeCode("AA157")
                .setPrice("11000")
                .setQuantity("1")
                .setRepeatNumber("1")
                .setFeeTotal("11000")
                .setCoveredPatientFee("1000")
                .setCoveredInsuranceFee("10000")
                .setCoveredPatientAllFee("0")
                .setUncoveredChosenFee("0")
                .setUncoveredUnchosenFee("0"));
        receiptBuilder.addFeeItems(FeeItem.newBuilder()
                .setFeeItemName("검사료")
                .setFeeItemCode("")
                .setTreatmentDate("2018-12-06")
                .setCoveredType("")
                .setMedicalChargeCode("BB157")
                .setPrice("20000")
                .setQuantity("1")
                .setRepeatNumber("1")
                .setFeeTotal("20000")
                .setCoveredPatientFee("0")
                .setCoveredInsuranceFee("0")
                .setCoveredPatientAllFee("0")
                .setUncoveredChosenFee("20000")
                .setUncoveredUnchosenFee("0"));

        /*** Bill.Diagnoses ***/
        Diagnosis.Builder diagnosisBuilder1 = Diagnosis.newBuilder();
        diagnosisBuilder1.setDiagnosisCodeVersion("ICD-10-2016");
        diagnosisBuilder1.setDiagnosisCodeType(10); // 주상병
        diagnosisBuilder1.setDiagnosisCode("J00");

        Diagnosis.Builder diagnosisBuilder2 = Diagnosis.newBuilder();
        diagnosisBuilder2.setDiagnosisCodeVersion("KCD-7");
        diagnosisBuilder2.setDiagnosisCodeType(20); // 부상병
        diagnosisBuilder2.setDiagnosisCode("J30.3");

        Bill.Builder partialBill = billBuilder
                .addReceipts(receiptBuilder)
                .addDiagnoses(diagnosisBuilder1)
                .addDiagnoses(diagnosisBuilder2);

        Bill actual = BillDataV1Utils.fillBill(partialBill);
        System.out.println(JsonFormat.printer().print(actual));
    }
}
