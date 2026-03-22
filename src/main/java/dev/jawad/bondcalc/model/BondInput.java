package dev.jawad.bondcalc.model;

public record BondInput(
        double faceValue,
        double couponRate,
        int couponFrequency,
        double yearsToMaturity,
        double marketYield,
        int daysSinceLastCoupon,
        int daysInCouponPeriod
) {
    public BondInput {
        if (faceValue <= 0) throw new IllegalArgumentException("Face value must be positive");
        if (couponRate < 0) throw new IllegalArgumentException("Coupon rate cannot be negative");
        if (couponFrequency < 1 || couponFrequency > 12) throw new IllegalArgumentException("Coupon frequency must be 1-12");
        if (yearsToMaturity <= 0) throw new IllegalArgumentException("Years to maturity must be positive");
        if (marketYield < 0) throw new IllegalArgumentException("Market yield cannot be negative");
        if (daysSinceLastCoupon < 0) throw new IllegalArgumentException("Days since last coupon cannot be negative");
        if (daysInCouponPeriod <= 0) throw new IllegalArgumentException("Days in coupon period must be positive");
    }
}
