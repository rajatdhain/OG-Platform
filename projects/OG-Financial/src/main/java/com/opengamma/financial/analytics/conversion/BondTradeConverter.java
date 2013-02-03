/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.conversion;

import org.apache.commons.lang.Validate;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.instrument.InstrumentDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedSecurityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedTransactionDefinition;
import com.opengamma.core.position.Trade;
import com.opengamma.financial.security.bond.BondSecurity;
import com.opengamma.util.time.DateUtils;

/**
 * 
 */
public class BondTradeConverter {
  private final BondSecurityConverter _securityConverter;

  public BondTradeConverter(final BondSecurityConverter securityConverter) {
    Validate.notNull(securityConverter, "security converter");
    _securityConverter = securityConverter;
  }

  public BondFixedTransactionDefinition convert(final Trade trade) {
    Validate.notNull(trade, "trade");
    Validate.isTrue(trade.getSecurity() instanceof BondSecurity, "Can only handle trades with security type BondSecurity");
    final BondSecurity security = (BondSecurity) trade.getSecurity();
    final InstrumentDefinition<?> underlying = security.accept(_securityConverter);
    if (!(underlying instanceof BondFixedSecurityDefinition)) {
      throw new OpenGammaRuntimeException("Can only handle fixed coupon bonds");
    }
    final BondFixedSecurityDefinition bond = (BondFixedSecurityDefinition) underlying;
    final int quantity = 1; // trade.getQuantity().intValue();
    // REVIEW: The quantity mechanism should be reviewed.
    final ZonedDateTime settlementDate = DateUtils.offsetDateTime(trade.getTradeDate(), trade.getTradeTime()).atZoneSameInstant(ZoneOffset.UTC); //TODO get the real time zone
    final double price = trade.getPremium().doubleValue();
    return new BondFixedTransactionDefinition(bond, quantity, settlementDate, price);
  }
}
