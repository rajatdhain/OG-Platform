/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.forex.calculator;

import com.opengamma.analytics.financial.forex.derivative.ForexOptionVanilla;
import com.opengamma.analytics.financial.forex.method.ForexOptionVanillaBlackSmileMethod;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitorAdapter;
import com.opengamma.analytics.financial.interestrate.YieldCurveBundle;
import com.opengamma.util.money.CurrencyAmount;

/**
 * Calculator of the gamma (second order derivative with respect to the spot rate) for Forex derivatives in the Black (Garman-Kohlhagen) world.
 */
public class DeltaValueBlackForexCalculator extends InstrumentDerivativeVisitorAdapter<YieldCurveBundle, CurrencyAmount> {

  /**
   * The unique instance of the calculator.
   */
  private static final DeltaValueBlackForexCalculator INSTANCE = new DeltaValueBlackForexCalculator();

  /**
   * Gets the calculator instance.
   * @return The calculator.
   */
  public static DeltaValueBlackForexCalculator getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor.
   */
  DeltaValueBlackForexCalculator() {
  }

  /**
   * The methods used by the different instruments.
   */
  private static final ForexOptionVanillaBlackSmileMethod METHOD_FXOPTIONVANILLA = ForexOptionVanillaBlackSmileMethod.getInstance();

  @Override
  public CurrencyAmount visitForexOptionVanilla(final ForexOptionVanilla derivative, final YieldCurveBundle data) {
    return METHOD_FXOPTIONVANILLA.delta(derivative, data, true);
  }

}