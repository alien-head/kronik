package io.alienhead.kronik.core

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Launches a coroutine to execute a block after a delay.
 *
 * @param timeInMillis The time in milliseconds to delay the code execution.
 */
suspend fun delayedLaunch(timeInMillis: Long, block: () -> Unit) = coroutineScope {
  // Prevent an infinite delay
  if (timeInMillis < Long.MAX_VALUE) {
    delay(timeInMillis)
    launch {
      block()
    }
  }
}

/**
 * Launches a coroutine to execute a block at an interval in millis.
 *
 * @param initialDelayMillis The initial delay in milliseconds before executing the code block.
 * @param intervalMillis The interval in milliseconds to execute the code block.
 * @param times The number of times to execute the code block.
 */
suspend fun intervalLaunchUntil(
  initialDelayMillis: Long = 0,
  intervalMillis: Long,
  times: Int,
  block: () -> Unit
) = coroutineScope {
  // Prevent an infinite delay
  if (initialDelayMillis < Long.MAX_VALUE && intervalMillis < Long.MAX_VALUE) {
    delay(initialDelayMillis)

    var timesLaunched = 0
    while (timesLaunched < times) {
      launch {
        block()
      }
      timesLaunched++
      delay(intervalMillis)
    }
  }
}
