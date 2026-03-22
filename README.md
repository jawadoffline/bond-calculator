# Bond Price Calculator

A fixed income bond pricing tool built with **Java 21 and Spring Boot**. Calculates clean price, dirty price, duration, DV01, convexity, and generates full cash flow schedules.

**Live Demo:** Coming soon

## What It Calculates

| Metric | Description |
|--------|-------------|
| Clean Price | Bond price excluding accrued interest |
| Dirty Price | Bond price including accrued interest |
| Accrued Interest | Interest accumulated since last coupon payment |
| Current Yield | Annual coupon / clean price |
| Macaulay Duration | Weighted average time to receive cash flows (years) |
| Modified Duration | Price sensitivity to yield changes |
| DV01 | Dollar value of a 1 basis point yield change |
| Convexity | Curvature of the price-yield relationship |
| Cash Flow Schedule | Full period-by-period breakdown with discount factors |

## Tech Stack

| Layer | Tech |
|-------|------|
| Backend | Java 21, Spring Boot 3.2 |
| Pricing Engine | Pure Java — stateless, no dependencies |
| Frontend | Thymeleaf, HTMX, Tailwind CSS |
| API | REST endpoint returning JSON |

## Run Locally

```bash
# Requires Java 21 and Maven
mvn spring-boot:run
# Open http://localhost:8081
```

## REST API

```bash
curl -X POST "http://localhost:8081/api/calculate?faceValue=1000&couponRate=5&couponFrequency=2&yearsToMaturity=10&marketYield=4.5"
```

Response:
```json
{
  "cleanPrice": 1039.9094,
  "dirtyPrice": 1039.9094,
  "accruedInterest": 0.0,
  "macaulayDuration": 8.0356,
  "modifiedDuration": 7.8587,
  "dv01": 0.008172,
  "convexity": 74.5506,
  "currentYield": 4.8081,
  "cashFlows": [...]
}
```

## Parameters

| Parameter | Description | Example |
|-----------|-------------|---------|
| faceValue | Par value of the bond | 1000 |
| couponRate | Annual coupon rate (%) | 5.0 |
| couponFrequency | Payments per year (1, 2, 4, 12) | 2 |
| yearsToMaturity | Time to maturity in years | 10 |
| marketYield | Yield to maturity (%) | 4.5 |
| daysSinceLastCoupon | Days since last coupon (optional) | 45 |
| daysInCouponPeriod | Days in the coupon period (optional) | 180 |

## Project Structure

```
src/main/java/dev/jawad/bondcalc/
├── BondCalculatorApplication.java
├── model/
│   ├── BondInput.java          # Input parameters (validated record)
│   ├── BondResult.java         # Output analytics
│   └── CashFlow.java           # Per-period cash flow data
├── service/
│   └── BondPricingService.java  # Core pricing engine
└── controller/
    └── CalculatorController.java # Web UI + REST API
```

## How The Math Works

The pricing engine uses standard discounted cash flow methodology:

1. **Cash Flow Schedule** — Generate coupon payments for each period, with principal at maturity
2. **Present Value** — Discount each cash flow by `1 / (1 + yield_per_period)^period`
3. **Dirty Price** — Sum of all present values
4. **Accrued Interest** — `coupon_per_period × (days_since_last_coupon / days_in_period)`
5. **Clean Price** — Dirty price minus accrued interest
6. **Duration** — Weighted average of `(time × PV) / dirty_price`
7. **DV01** — Modified duration × dirty price × 0.0001
8. **Convexity** — Second derivative of price with respect to yield
