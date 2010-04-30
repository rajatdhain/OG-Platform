/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.id;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;
import org.fudgemsg.FudgeFieldContainer;
import org.fudgemsg.FudgeMessageFactory;
import org.fudgemsg.MutableFudgeFieldContainer;

import com.opengamma.util.ArgumentChecker;

/**
 * An immutable identifier for an item.
 * <p>
 * The identifier is formed from two parts, the scheme and the value.
 * The {@link IdentificationScheme scheme} defines a single way of identifying items,
 * while the value is an identifier within that scheme.
 * A value from one scheme may refer to a completely different real-world item than
 * the same value from a different scheme.
 * <p>
 * Real-world examples of {@code Identifier} include instances of:
 * <ul>
 *   <li>Cusip</li>
 *   <li>Isin</li>
 *   <li>RIC code</li>
 *   <li>Bloomberg ID</li>
 *   <li>Bloomberg Ticker</li>
 *   <li>Trading system OTC trade ID</li>
 * </ul>
 */
public final class Identifier implements Identifiable, Comparable<Identifier>, Cloneable, Serializable {

  /**
   * Fudge message key for the scheme.
   */
  public static final String SCHEME_FUDGE_FIELD_NAME = "Scheme";
  /**
   * Fudge message key for the value.
   */
  public static final String VALUE_FUDGE_FIELD_NAME = "Value";

  /**
   * The scheme that categorizes the value.
   */
  private final IdentificationScheme _scheme;
  /**
   * The value (identifier) within the scheme.
   */
  private final String _value;

  /**
   * Obtains an identifier from a scheme and value.
   * @param scheme  the scheme of the identifier, not empty, not null
   * @param value  the value of the identifier, not empty, not null
   * @return the identifier, not null
   */
  public static Identifier of(IdentificationScheme scheme, String value) {
    return new Identifier(scheme, value);
  }

  /**
   * Obtains an identifier from a scheme and value.
   * @param scheme  the scheme of the identifier, not empty, not null
   * @param value  the value of the identifier, not empty, not null
   * @return the identifier, not null
   */
  public static Identifier of(String scheme, String value) {
    return new Identifier(scheme, value);
  }

  /**
   * Obtains an identifier from a formatted scheme and value.
   * <p>
   * This parses the identifier from the form produced by {@code toString()}
   * which is {@code <SCHEME>::<VALUE>}.
   * @param str  the identifier to parse, not null
   * @return the identifier, not null
   * @throws IllegalArgumentException if the identifier cannot be parsed
   */
  public static Identifier parse(String str) {
    int pos = str.indexOf("::");
    if (pos < 0) {
      throw new IllegalArgumentException("Invalid identifier format: " + str);
    }
    return new Identifier(str.substring(0, pos), str.substring(pos + 2));
  }

  /**
   * Constructs an identifier from the scheme and standalone identifier.
   * @param scheme  the scheme, not null
   * @param standaloneId  the standalone identifier, not empty, not null
   */
  public Identifier(IdentificationScheme scheme, String standaloneId) {
    ArgumentChecker.notNull(scheme, "scheme");
    ArgumentChecker.notEmpty(standaloneId, "standaloneId");
    _scheme = scheme;
    _value = standaloneId;
  }

  /**
   * Constructs an identifier from the scheme and standalone identifier.
   * @param schemeName  the scheme name, not null
   * @param standaloneId  the standalone identifier, not null
   */
  public Identifier(String schemeName, String standaloneId) {
    this(new IdentificationScheme(schemeName), standaloneId);
  }

  /**
   * Constructs an identifier from a Fudge message.
   * @param fudgeMsg  the fudge message, not null
   */
  public Identifier(FudgeFieldContainer fudgeMsg) {
    _scheme = new IdentificationScheme(fudgeMsg.getString(SCHEME_FUDGE_FIELD_NAME));
    _value = fudgeMsg.getString(VALUE_FUDGE_FIELD_NAME);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the identification scheme.
   * This provides the universe within which the standalone identifier has meaning.
   * @return the scheme, not null
   */
  public IdentificationScheme getScheme() {
    return _scheme;
  }

  /**
   * Checks of the identification scheme equals the specified scheme.
   * @param scheme  the scheme to check for, null returns false
   * @return true if the schemes match
   */
  public boolean isScheme(IdentificationScheme scheme) {
    return _scheme.equals(scheme);
  }

  /**
   * Checks of the identification scheme equals the specified scheme.
   * @param scheme  the scheme to check for, null returns true
   * @return true if the schemes are different
   */
  public boolean isNotScheme(IdentificationScheme scheme) {
    return _scheme.equals(scheme) == false;
  }

  /**
   * Checks of the identification scheme equals the specified scheme.
   * @param scheme  the scheme to check for, null returns false
   * @return true if the schemes match
   */
  public boolean isScheme(String scheme) {
    return _scheme.getName().equals(scheme);
  }

  /**
   * Checks of the identification scheme equals the specified scheme.
   * @param scheme  the scheme to check for, null returns true
   * @return true if the schemes are different
   */
  public boolean isNotScheme(String scheme) {
    return _scheme.getName().equals(scheme) == false;
  }

  /**
   * Gets the standalone identifier.
   * @return the value, not null
   */
  public String getValue() {
    return _value;
  }

  @Override
  public Identifier getIdentityKey() {
    return this;
  }

  //-------------------------------------------------------------------------
  /**
   * Compares the identifiers, sorting alphabetically by scheme followed by value.
   * @param other  the other identifier, not null
   * @return negative if this is less, zero if equal, positive if greater
   */
  @Override
  public int compareTo(Identifier o) {
    if (_scheme.compareTo(o._scheme) != 0) {
      return _scheme.compareTo(o._scheme);
    }
    return _value.compareTo(o._value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Identifier) {
      Identifier other = (Identifier) obj;
      return ObjectUtils.equals(_scheme.getName(), other._scheme.getName()) &&
              ObjectUtils.equals(_value, other._value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return _scheme.getName().hashCode() ^ _value.hashCode();
  }

  /**
   * Returns the identifier in the form {@code <SCHEME>::<VALUE>}.
   * @return the identifier, not null
   */
  @Override
  public String toString() {
    return _scheme.getName() + "::" + _value;
  }

  //-------------------------------------------------------------------------
  public FudgeFieldContainer toFudgeMsg(FudgeMessageFactory fudgeMessageFactory) {
    ArgumentChecker.notNull(fudgeMessageFactory, "Fudge Context");
    MutableFudgeFieldContainer msg = fudgeMessageFactory.newMessage();
    msg.add(SCHEME_FUDGE_FIELD_NAME, getScheme().getName());
    msg.add(VALUE_FUDGE_FIELD_NAME, getValue());
    return msg;
  }

}
