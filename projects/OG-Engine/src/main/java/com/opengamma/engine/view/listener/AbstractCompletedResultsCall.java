/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;

import com.google.common.base.Function;
import com.opengamma.engine.view.ViewComputationResultModel;
import com.opengamma.engine.view.ViewDeltaResultModel;
import com.opengamma.engine.view.impl.InMemoryViewComputationResultModel;
import com.opengamma.engine.view.impl.InMemoryViewDeltaResultModel;

/**
 * Base class for cycle results.
 */
public abstract class AbstractCompletedResultsCall implements Function<ViewResultListener, Object> {

  private static final Logger s_logger = LoggerFactory.getLogger(AbstractCompletedResultsCall.class);

  private ViewComputationResultModel _full;
  private InMemoryViewComputationResultModel _fullCopy;
  private ViewDeltaResultModel _delta;
  private InMemoryViewDeltaResultModel _deltaCopy;

  public AbstractCompletedResultsCall(ViewComputationResultModel full, ViewDeltaResultModel delta) {
    update(full, delta);
  }

  // NOTE: The calculation time is machine time, not valuation time so we know which was computed first, unless the cycle times are
  // smaller than the resolution of the Instant clock.

  public void update(ViewComputationResultModel full, ViewDeltaResultModel delta) {
    if (full != null) {
      if (_full != null) {
        final Instant previous = _full.getCalculationTime();
        final Instant current = full.getCalculationTime();
        if (previous.isBefore(current)) {
          // New full result replaces the old one
          s_logger.debug("New full result from {} replaces one from {}", current, previous);
          _full = full;
          _fullCopy = null;
        } else if (previous.equals(current)) {
          // Two results calculated so close together they appear "at the same time". Better merge them, but the result might be wrong.
          s_logger.warn("Merging two results both calculated at {}", current);
          if (_fullCopy == null) {
            _fullCopy = new InMemoryViewComputationResultModel(_full);
            _full = _fullCopy;
          }
          _fullCopy.update(full);
        } else {
          // The previous full result is newer than the new one - discard the new one
          s_logger.info("Ignoring full result from {} that is newer than the previous one from {}", current, previous);
        }
      } else {
        s_logger.debug("Got initial full result");
        _full = full;
      }
    }
    if (delta != null) {
      if (_delta != null) {
        final Instant previous = _delta.getCalculationTime();
        final Instant current = delta.getCalculationTime();
        if (previous.isAfter(current)) {
          // This result predates the current value, so swap and merge to get ordering right
          s_logger.debug("Applying old delta from {} to new baseline delta from {}", previous, current);
          _deltaCopy = new InMemoryViewDeltaResultModel(delta);
          _deltaCopy.update(_delta);
          _delta = _deltaCopy;
        } else {
          // Merge the new result into the running delta
          if (previous.equals(current)) {
            // Two results calculated so close together they appear "at the same time". The merge order might be wrong.
            s_logger.warn("Merging two deltas both calculated at {}", current);
          } else {
            s_logger.debug("Applying new delta from {} to previous delta from {}", current, previous);
          }
          if (_deltaCopy == null) {
            _deltaCopy = new InMemoryViewDeltaResultModel(_delta);
            _delta = _deltaCopy;
          }
          _deltaCopy.update(delta);
        }
      } else {
        s_logger.debug("Got initial delta result");
        _delta = delta;
      }
    }
  }

  public ViewComputationResultModel getViewComputationResultModel() {
    return _full;
  }

  public ViewDeltaResultModel getViewDeltaResultModel() {
    return _delta;
  }

}
