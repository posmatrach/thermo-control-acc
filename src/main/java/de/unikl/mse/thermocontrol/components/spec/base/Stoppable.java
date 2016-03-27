package de.unikl.mse.thermocontrol.components.spec.base;

/**
 * Custom counterpart to {@linkRunnable} interface.
 * Method terminate is supposed to signal termination of the processing loop
 * within the {@link Runnable#run()}
 * 
 * @author Nenad Natosevic <nenad.natoshevic@gmail.com>
 *
 */
public interface Stoppable
{
	void terminate();
}
