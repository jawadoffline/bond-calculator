package dev.jawad.bondcalc.controller;

import dev.jawad.bondcalc.model.BondInput;
import dev.jawad.bondcalc.model.BondResult;
import dev.jawad.bondcalc.service.BondPricingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalculatorController {

    private final BondPricingService pricingService;

    public CalculatorController(BondPricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/")
    public String home() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam double faceValue,
            @RequestParam double couponRate,
            @RequestParam int couponFrequency,
            @RequestParam double yearsToMaturity,
            @RequestParam double marketYield,
            @RequestParam(defaultValue = "0") int daysSinceLastCoupon,
            @RequestParam(defaultValue = "180") int daysInCouponPeriod,
            Model model) {

        BondInput input = new BondInput(
                faceValue, couponRate, couponFrequency,
                yearsToMaturity, marketYield,
                daysSinceLastCoupon, daysInCouponPeriod
        );

        BondResult result = pricingService.calculate(input);
        model.addAttribute("result", result);
        model.addAttribute("input", input);
        return "fragments/results";
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public BondResult calculateApi(
            @RequestParam double faceValue,
            @RequestParam double couponRate,
            @RequestParam int couponFrequency,
            @RequestParam double yearsToMaturity,
            @RequestParam double marketYield,
            @RequestParam(defaultValue = "0") int daysSinceLastCoupon,
            @RequestParam(defaultValue = "180") int daysInCouponPeriod) {

        BondInput input = new BondInput(
                faceValue, couponRate, couponFrequency,
                yearsToMaturity, marketYield,
                daysSinceLastCoupon, daysInCouponPeriod
        );

        return pricingService.calculate(input);
    }
}
