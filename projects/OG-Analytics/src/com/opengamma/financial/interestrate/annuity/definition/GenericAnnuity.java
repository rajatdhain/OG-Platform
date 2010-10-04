/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.interestrate.annuity.definition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

import com.opengamma.financial.interestrate.InterestRateDerivative;
import com.opengamma.financial.interestrate.InterestRateDerivativeVisitor;
import com.opengamma.financial.interestrate.payments.Payment;

/**
 * A generic annuity is a set of payments (cash flows) at known future times. 
 * There payments can be known in advance, or depend on the future value of  some (possibly several) indices, e.g. the future Libor 
 */
public class GenericAnnuity<P extends Payment> implements InterestRateDerivative {

  private final P[] _payments;

  public GenericAnnuity(P[] payments) {
    Validate.noNullElements(payments);

    _payments = payments;

  }

  @SuppressWarnings("unchecked")
  public GenericAnnuity(List<? extends P> payments, Class<P> pType) {
    Validate.noNullElements(payments);
    _payments = payments.toArray((P[]) Array.newInstance(pType, 0));

  }

  public int getNumberOfpayments() {
    return _payments.length;
  }

  public P getNthPayment(int n) {
    return _payments[n];
  }

  /**
   * Gets the payments field.
   * @return the payments
   */
  public P[] getPayments() {
    return _payments;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(_payments);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GenericAnnuity<?> other = (GenericAnnuity<?>) obj;
    if (_payments.length != other._payments.length) {
      return false;
    }
    for (int i = 0; i < _payments.length; i++) {
      if (!ObjectUtils.equals(_payments[i], other._payments[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public <S, T> T accept(InterestRateDerivativeVisitor<S, T> visitor, S data) {
    return visitor.visitGenericAnnuity(this, data);
  }

  @Override
  public InterestRateDerivative withRate(double rate) {
    return null;
  }

}
