/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.examples.function;

import java.util.List;
import java.util.Set;

import com.opengamma.engine.function.config.AbstractRepositoryConfigurationBean;
import com.opengamma.engine.function.config.FunctionConfiguration;
import com.opengamma.engine.function.config.RepositoryConfigurationSource;
import com.opengamma.examples.volatility.cube.ExampleSwaptionVolatilityCubeInstrumentProvider;
import com.opengamma.examples.volatility.cube.ExampleVolatilityCubeDefinitionSource;
import com.opengamma.financial.analytics.volatility.cube.VolatilityCubeFunction;
import com.opengamma.util.money.Currency;

/**
 * Function repository configuration source for the synthetic volatility cubes.
 */
public class SyntheticVolatilityCubeFunctions extends AbstractRepositoryConfigurationBean {

  /**
   * Default instance of a repository configuration source exposing the functions from this package.
   *
   * @return the configuration source exposing functions from this package
   */
  public static RepositoryConfigurationSource instance() {
    return new SyntheticVolatilityCubeFunctions().getObjectCreating();
  }

  protected void addVolatilityCubeFunction(final List<FunctionConfiguration> functions, final String currency, final String definition) {
    functions.add(functionConfiguration(VolatilityCubeFunction.class, currency, definition));
    functions.add(functionConfiguration(SyntheticVolatilityCubeMarketDataFunction.class, currency, definition));
  }

  @Override
  protected void addAllConfigurations(final List<FunctionConfiguration> functions) {
    final Set<Currency> volCubeCurrencies = ExampleSwaptionVolatilityCubeInstrumentProvider.INSTANCE.getAllCurrencies();
    for (final Currency currency : volCubeCurrencies) {
      addVolatilityCubeFunction(functions, currency.getCode(), ExampleVolatilityCubeDefinitionSource.DEFINITION_NAME);
    }
  }

}
