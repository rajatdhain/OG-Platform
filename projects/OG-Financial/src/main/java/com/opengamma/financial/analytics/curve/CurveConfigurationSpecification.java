/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve;

import java.io.Serializable;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.engine.ComputationTargetSpecification;

/**
 * 
 */
@BeanDefinition
public class CurveConfigurationSpecification extends DirectBean implements Serializable, Comparable<CurveConfigurationSpecification> {

  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The computation target specification.
   */
  @PropertyDefinition(validate = "notNull")
  private ComputationTargetSpecification _targetSpec;

  /**
   * The priority of the this computation configuration specification (lower is higher priority, with zero the highest)
   */
  @PropertyDefinition
  private int _priority;

  /* package */CurveConfigurationSpecification() {
  }

  public CurveConfigurationSpecification(final ComputationTargetSpecification targetSpec, final int priority) {
    setTargetSpec(targetSpec);
    setPriority(priority);
  }

  @Override
  public int compareTo(final CurveConfigurationSpecification other) {
    if (_priority == other.getPriority()) {
      return 0;
    }
    return _priority - other.getPriority();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CurveConfigurationSpecification}.
   * @return the meta-bean, not null
   */
  public static CurveConfigurationSpecification.Meta meta() {
    return CurveConfigurationSpecification.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(CurveConfigurationSpecification.Meta.INSTANCE);
  }

  @Override
  public CurveConfigurationSpecification.Meta metaBean() {
    return CurveConfigurationSpecification.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 486583532:  // targetSpec
        return getTargetSpec();
      case -1165461084:  // priority
        return getPriority();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 486583532:  // targetSpec
        setTargetSpec((ComputationTargetSpecification) newValue);
        return;
      case -1165461084:  // priority
        setPriority((Integer) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_targetSpec, "targetSpec");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CurveConfigurationSpecification other = (CurveConfigurationSpecification) obj;
      return JodaBeanUtils.equal(getTargetSpec(), other.getTargetSpec()) &&
          JodaBeanUtils.equal(getPriority(), other.getPriority());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getTargetSpec());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPriority());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the computation target specification.
   * @return the value of the property, not null
   */
  public ComputationTargetSpecification getTargetSpec() {
    return _targetSpec;
  }

  /**
   * Sets the computation target specification.
   * @param targetSpec  the new value of the property, not null
   */
  public void setTargetSpec(ComputationTargetSpecification targetSpec) {
    JodaBeanUtils.notNull(targetSpec, "targetSpec");
    this._targetSpec = targetSpec;
  }

  /**
   * Gets the the {@code targetSpec} property.
   * @return the property, not null
   */
  public final Property<ComputationTargetSpecification> targetSpec() {
    return metaBean().targetSpec().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the priority of the this computation configuration specification (lower is higher priority, with zero the highest)
   * @return the value of the property
   */
  public int getPriority() {
    return _priority;
  }

  /**
   * Sets the priority of the this computation configuration specification (lower is higher priority, with zero the highest)
   * @param priority  the new value of the property
   */
  public void setPriority(int priority) {
    this._priority = priority;
  }

  /**
   * Gets the the {@code priority} property.
   * @return the property, not null
   */
  public final Property<Integer> priority() {
    return metaBean().priority().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurveConfigurationSpecification}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code targetSpec} property.
     */
    private final MetaProperty<ComputationTargetSpecification> _targetSpec = DirectMetaProperty.ofReadWrite(
        this, "targetSpec", CurveConfigurationSpecification.class, ComputationTargetSpecification.class);
    /**
     * The meta-property for the {@code priority} property.
     */
    private final MetaProperty<Integer> _priority = DirectMetaProperty.ofReadWrite(
        this, "priority", CurveConfigurationSpecification.class, Integer.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "targetSpec",
        "priority");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 486583532:  // targetSpec
          return _targetSpec;
        case -1165461084:  // priority
          return _priority;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CurveConfigurationSpecification> builder() {
      return new DirectBeanBuilder<CurveConfigurationSpecification>(new CurveConfigurationSpecification());
    }

    @Override
    public Class<? extends CurveConfigurationSpecification> beanType() {
      return CurveConfigurationSpecification.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code targetSpec} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ComputationTargetSpecification> targetSpec() {
      return _targetSpec;
    }

    /**
     * The meta-property for the {@code priority} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> priority() {
      return _priority;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}