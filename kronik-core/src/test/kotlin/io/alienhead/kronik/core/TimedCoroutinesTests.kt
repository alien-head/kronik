package io.alienhead.kronik.core

import io.kotest.assertions.fail
import io.kotest.assertions.until.fixed
import io.kotest.assertions.until.until
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class TimedCoroutinesTests : DescribeSpec({

  describe("TimedCoroutines") {
    describe("delayedLaunch()") {
      it("delays the launch of a coroutine") {
        var toTest = 1

        launch {
          delayedLaunch(500) {
            toTest++
          }
        }

        withClue("delays for 500 ms") {
          until(Duration.Companion.milliseconds(300), Duration.milliseconds(100).fixed()) {
            toTest == 1
          }
        }

        withClue("updates toTest after 500 ms") {
          until(Duration.Companion.milliseconds(600), Duration.milliseconds(100).fixed()) {
            toTest == 2
          }
        }
      }

      it("cancels if max time value") {
        var toTest = 1

        val testJob = launch {
          delayedLaunch(Long.MAX_VALUE) {
            toTest++
          }
        }

        delay(100)
        if (!testJob.isCompleted) {
          testJob.cancel()
          testJob.join()
          fail("The coroutine should have cancelled.")
        }

        toTest shouldBe 1
      }
    }

    describe("intervalLaunchUntil()") {
      it("delays the launch of a coroutine") {
        var toTest = 1

        launch {
          intervalLaunchUntil(500, intervalMillis = 500, 2) {
            toTest++
          }
        }

        withClue("delays for 500 ms") {
          until(Duration.Companion.milliseconds(300), Duration.milliseconds(100).fixed()) {
            toTest == 1
          }
        }

        withClue("updates toTest after 500 ms") {
          until(Duration.Companion.milliseconds(600), Duration.milliseconds(100).fixed()) {
            toTest == 2
          }
        }

        withClue("runs block again after another 150 ms") {
          until(Duration.Companion.milliseconds(1100), Duration.milliseconds(100).fixed()) {
            toTest == 3
          }
        }
      }

      it("cancels if initial delay is the max value") {
        var toTest = 1

        val testJob = launch {
          intervalLaunchUntil(Long.MAX_VALUE, intervalMillis = 500, 1) {
            toTest++
          }
        }

        delay(100)
        if (!testJob.isCompleted) {
          testJob.cancel()
          testJob.join()
          fail("The coroutine should have cancelled.")
        }

        toTest shouldBe 1
      }

      it("cancels if interval delay is the max value") {
        var toTest = 1

        val testJob = launch {
          intervalLaunchUntil(500, intervalMillis = Long.MAX_VALUE, 1) {
            toTest++
          }
        }

        delay(100)
        if (!testJob.isCompleted) {
          testJob.cancel()
          testJob.join()
          fail("The coroutine should have cancelled.")
        }

        toTest shouldBe 1
      }
    }
  }
})
