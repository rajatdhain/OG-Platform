/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.view.calc;

import java.util.Map;

import org.joda.beans.BeanDefinition;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;

import com.opengamma.engine.value.ComputedValueResult;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.PublicSPI;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Encapsulates the response to a query for computation results.
 */
@PublicSPI
@BeanDefinition
public class ComputationResultsResponse extends DirectBean {

  /**
   * The results obtained from the query.
   */
  @PropertyDefinition
  private Map<ValueSpecification, ComputedValueResult> _results;
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ComputationResultsResponse}.
   * @return the meta-bean, not null
   */
  public static ComputationResultsResponse.Meta meta() {
    return ComputationResultsResponse.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(ComputationResultsResponse.Meta.INSTANCE);
  }

  @Override
  public ComputationResultsResponse.Meta metaBean() {
    return ComputationResultsResponse.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1097546742:  // results
        return getResults();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1097546742:  // results
        setResults((Map<ValueSpecification, ComputedValueResult>) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ComputationResultsResponse other = (ComputationResultsResponse) obj;
      return JodaBeanUtils.equal(getResults(), other.getResults());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getResults());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the results obtained from the query.
   * @return the value of the property
   */
  public Map<ValueSpecification, ComputedValueResult> getResults() {
    return _results;
  }

  /**
   * Sets the results obtained from the query.
   * @param results  the new value of the property
   */
  public void setResults(Map<ValueSpecification, ComputedValueResult> results) {
    this._results = results;
  }

  /**
   * Gets the the {@code results} property.
   * @return the property, not null
   */
  public final Property<Map<ValueSpecification, ComputedValueResult>> results() {
    return metaBean().results().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ComputationResultsResponse}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code results} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<ValueSpecification, ComputedValueResult>> _results = DirectMetaProperty.ofReadWrite(
        this, "results", ComputationResultsResponse.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "results");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1097546742:  // results
          return _results;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ComputationResultsResponse> builder() {
      return new DirectBeanBuilder<ComputationResultsResponse>(new ComputationResultsResponse());
    }

    @Override
    public Class<? extends ComputationResultsResponse> beanType() {
      return ComputationResultsResponse.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code results} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<ValueSpecification, ComputedValueResult>> results() {
      return _results;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
