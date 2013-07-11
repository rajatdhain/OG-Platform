/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.user;

import com.google.common.collect.Sets;
import com.opengamma.core.user.OGEntitlement;
import com.opengamma.core.user.OGRole;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.UniqueId;
import com.opengamma.util.PublicSPI;
import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaBean;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Set;
import java.util.Map;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import static com.google.common.collect.Sets.newHashSet;

/**
 * A role known to the OpenGamma Platform installation.
 * <p/>
 * A role within the role management system.
 * Support is provided for external roles as well as passwords.
 */
@PublicSPI
@BeanDefinition
public class ManageableOGRole extends DirectBean implements OGRole, Serializable {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The unique identifier of the role.
   * This must be null when adding to a master and not null when retrieved from a master.
   */
  @PropertyDefinition
  private UniqueId _uniqueId;

  /**
   * The role id that uniquely identifies the role
   * This is used with the password to authenticate.
   */
  @PropertyDefinition(validate = "notNull")
  private String _key;

  /**
   * The entitlements for the role.
   * This is a set of entitlements that the role has, which enables access restriction.
   */
  @PropertyDefinition(validate = "notNull")
  private final Set<OGEntitlement> _entitlements = newHashSet();
  /**
   * The display role name, used to identify the role in a GUI.
   */
  @PropertyDefinition
  private String _name;

  public void setEntitlements(OGEntitlement... entitlements) {
    setEntitlements(newHashSet(Arrays.asList(entitlements)));
  }

  private static SecureRandom random = new SecureRandom();

  private static String randomString(int numchars){
    StringBuffer sb = new StringBuffer();
    while(sb.length() < numchars){
      sb.append(Integer.toHexString(random.nextInt()));
    }

    return sb.toString().substring(0, numchars);
  }

  /**
   * Creates a role.
   */
  protected ManageableOGRole() {
  }

  /**
   * Creates a role, setting the role id.
   *
   * @param name the role name, not null
   */
  public ManageableOGRole(String name) {
    _name = name;
    _key = randomString(50);
  }

  public void revoke(){
    _key = randomString(50);
  }

  /**
   * Clones the role, returning an independent copy.
   *
   * @return the clone, not null
   */
  public ManageableOGRole clone() {
    ManageableOGRole cloned = new ManageableOGRole();
    cloned._uniqueId = _uniqueId;
    cloned._key = _key;
    cloned._entitlements.addAll(_entitlements);
    cloned._name = _name;
    return cloned;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ManageableOGRole}.
   * @return the meta-bean, not null
   */
  public static ManageableOGRole.Meta meta() {
    return ManageableOGRole.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(ManageableOGRole.Meta.INSTANCE);
  }

  @Override
  public ManageableOGRole.Meta metaBean() {
    return ManageableOGRole.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        return getUniqueId();
      case 106079:  // key
        return getKey();
      case -1704576794:  // entitlements
        return getEntitlements();
      case 3373707:  // name
        return getName();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -294460212:  // uniqueId
        setUniqueId((UniqueId) newValue);
        return;
      case 106079:  // key
        setKey((String) newValue);
        return;
      case -1704576794:  // entitlements
        setEntitlements((Set<OGEntitlement>) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_key, "key");
    JodaBeanUtils.notNull(_entitlements, "entitlements");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ManageableOGRole other = (ManageableOGRole) obj;
      return JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          JodaBeanUtils.equal(getKey(), other.getKey()) &&
          JodaBeanUtils.equal(getEntitlements(), other.getEntitlements()) &&
          JodaBeanUtils.equal(getName(), other.getName());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getKey());
    hash += hash * 31 + JodaBeanUtils.hashCode(getEntitlements());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique identifier of the role.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @return the value of the property
   */
  public UniqueId getUniqueId() {
    return _uniqueId;
  }

  /**
   * Sets the unique identifier of the role.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @param uniqueId  the new value of the property
   */
  public void setUniqueId(UniqueId uniqueId) {
    this._uniqueId = uniqueId;
  }

  /**
   * Gets the the {@code uniqueId} property.
   * This must be null when adding to a master and not null when retrieved from a master.
   * @return the property, not null
   */
  public final Property<UniqueId> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the role id that uniquely identifies the role
   * This is used with the password to authenticate.
   * @return the value of the property, not null
   */
  public String getKey() {
    return _key;
  }

  /**
   * Sets the role id that uniquely identifies the role
   * This is used with the password to authenticate.
   * @param key  the new value of the property, not null
   */
  public void setKey(String key) {
    JodaBeanUtils.notNull(key, "key");
    this._key = key;
  }

  /**
   * Gets the the {@code key} property.
   * This is used with the password to authenticate.
   * @return the property, not null
   */
  public final Property<String> key() {
    return metaBean().key().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the entitlements for the role.
   * This is a set of entitlements that the role has, which enables access restriction.
   * @return the value of the property, not null
   */
  public Set<OGEntitlement> getEntitlements() {
    return _entitlements;
  }

  /**
   * Sets the entitlements for the role.
   * This is a set of entitlements that the role has, which enables access restriction.
   * @param entitlements  the new value of the property
   */
  public void setEntitlements(Set<OGEntitlement> entitlements) {
    this._entitlements.clear();
    this._entitlements.addAll(entitlements);
  }

  /**
   * Gets the the {@code entitlements} property.
   * This is a set of entitlements that the role has, which enables access restriction.
   * @return the property, not null
   */
  public final Property<Set<OGEntitlement>> entitlements() {
    return metaBean().entitlements().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the display role name, used to identify the role in a GUI.
   * @return the value of the property
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the display role name, used to identify the role in a GUI.
   * @param name  the new value of the property
   */
  public void setName(String name) {
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ManageableOGRole}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueId> _uniqueId = DirectMetaProperty.ofReadWrite(
        this, "uniqueId", ManageableOGRole.class, UniqueId.class);
    /**
     * The meta-property for the {@code key} property.
     */
    private final MetaProperty<String> _key = DirectMetaProperty.ofReadWrite(
        this, "key", ManageableOGRole.class, String.class);
    /**
     * The meta-property for the {@code entitlements} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Set<OGEntitlement>> _entitlements = DirectMetaProperty.ofReadWrite(
        this, "entitlements", ManageableOGRole.class, (Class) Set.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", ManageableOGRole.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "uniqueId",
        "key",
        "entitlements",
        "name");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -294460212:  // uniqueId
          return _uniqueId;
        case 106079:  // key
          return _key;
        case -1704576794:  // entitlements
          return _entitlements;
        case 3373707:  // name
          return _name;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ManageableOGRole> builder() {
      return new DirectBeanBuilder<ManageableOGRole>(new ManageableOGRole());
    }

    @Override
    public Class<? extends ManageableOGRole> beanType() {
      return ManageableOGRole.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueId> uniqueId() {
      return _uniqueId;
    }

    /**
     * The meta-property for the {@code key} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> key() {
      return _key;
    }

    /**
     * The meta-property for the {@code entitlements} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Set<OGEntitlement>> entitlements() {
      return _entitlements;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
