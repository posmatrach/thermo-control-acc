package de.unikl.mse.thermocontrol.components.spec.base;

import de.unikl.mse.thermocontrol.messaging.base.BaseMessage;
import de.unikl.mse.thermocontrol.messaging.base.MessageConsumer;

/**
 * 
 * Interface intended for the composition purposes.
 * It defines interfaces that every component of the AC system should implement.
 * Namely {@link Runnable}, {@link Stoppable} and {@link MessageConsumer}
 * 
 * @author Nenad Natosevic <nenad.natoshevic@gmail.com>
 *
 * @param <T>
 */
public interface ACComponent<T extends BaseMessage<?>> extends MessageConsumer<T>, Runnable, Stoppable
{
	// No additional methods
}
