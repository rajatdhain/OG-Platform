/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.calcnode;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import javax.time.Instant;

import org.testng.annotations.Test;
import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeDeserializationContext;
import org.fudgemsg.mapping.FudgeSerializationContext;

import com.opengamma.id.UniqueIdentifier;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;

/**
 * 
 */
@Test
public class CalculationJobSpecificationTest {
  
  public void testHashCode() {
    Instant valuationTime = Instant.now();
    CalculationJobSpecification spec1 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewProcess"), "config", valuationTime, 1L);
    CalculationJobSpecification spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewProcess"), "config", valuationTime, 1L);
    
    assertEquals(spec1.hashCode(), spec2.hashCode());
    
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle2"), "config", valuationTime, 1L);
    assertFalse(spec1.hashCode() == spec2.hashCode());
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config2", valuationTime, 1L);
    assertFalse(spec1.hashCode() == spec2.hashCode());
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config", valuationTime.plusMillis(1), 1L);
    assertFalse(spec1.hashCode() == spec2.hashCode());
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config", valuationTime, 2L);
    assertFalse(spec1.hashCode() == spec2.hashCode());
  }

  public void testEquals() {
    Instant valuationTime = Instant.now();
    CalculationJobSpecification spec1 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewProcess"), "config", valuationTime, 1L);
    assertTrue(spec1.equals(spec1));
    assertFalse(spec1.equals(null));
    assertFalse(spec1.equals("Kirk"));
    CalculationJobSpecification spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewProcess"), "config", valuationTime, 1L);
    assertTrue(spec1.equals(spec2));
    
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle2"), "config", valuationTime, 1L);
    assertFalse(spec1.equals(spec2));
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config2", valuationTime, 1L);
    assertFalse(spec1.equals(spec2));
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config", valuationTime.plusMillis(1), 1L);
    assertFalse(spec1.equals(spec2));
    spec2 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config", valuationTime, 2L);
    assertFalse(spec1.equals(spec2));
  }
  
  public void fudgeEncoding() {
    FudgeContext context = OpenGammaFudgeContext.getInstance();
    CalculationJobSpecification spec1 = new CalculationJobSpecification(UniqueIdentifier.of("Test", "ViewCycle"), "config", Instant.now(), 1L);
    FudgeSerializationContext serializationContext = new FudgeSerializationContext(context);
    MutableFudgeMsg inMsg = serializationContext.objectToFudgeMsg(spec1);
    FudgeMsg outMsg = context.deserialize(context.toByteArray(inMsg)).getMessage();
    FudgeDeserializationContext deserializationContext = new FudgeDeserializationContext(context);
    CalculationJobSpecification spec2 = deserializationContext.fudgeMsgToObject(CalculationJobSpecification.class, outMsg);
    assertEquals(spec1, spec2);
  }

}
