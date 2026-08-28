package com.vikramaditya.portfolio.utils

import kotlin.math.PI
import kotlin.math.abs

/**
 * Motion primitives, ported from Apple's "Designing Fluid Interfaces" (WWDC 2018).
 *
 * Deliberately free of DOM and Compose references so the physics can be reasoned
 * about (and unit-tested) on its own.
 */

/** Matches the scroll-deceleration feel of the platform. Lower is snappier. */
const val DECELERATION_RATE = 0.998

/**
 * Where a flick would come to rest, using exponential decay.
 *
 * This is the projection Apple actually ships, not the textbook `v^2 / 2a`.
 * Snapping to the target nearest this projected point is what makes a flick feel
 * like it throws the content, rather than nudging it one step.
 */
fun projectMomentum(velocityPxPerSec: Double, decelerationRate: Double = DECELERATION_RATE): Double =
    (velocityPxPerSec / 1000.0) * decelerationRate / (1.0 - decelerationRate)

/**
 * Progressive resistance past a boundary. Odd-symmetric, so it behaves the same
 * in both directions. A hard stop reads as "frozen"; this reads as "responsive,
 * but there is nothing more here".
 */
fun rubberBand(overshoot: Double, dimension: Double, constant: Double = 0.55): Double =
    (overshoot * dimension * constant) / (dimension + constant * abs(overshoot))

/**
 * Kotlin/JS `Double.toString()` emits values like `0.8199999999999998`, which
 * bloats every transform string and defeats the write-skip cache in the render
 * loop. Route through JS `toFixed` instead.
 */
fun Double.fx(digits: Int): String = this.asDynamic().toFixed(digits).unsafeCast<String>()

/** Mutable position/velocity pair, so the hot loop allocates nothing. */
class SpringState {
    var x = 0.0
    var v = 0.0
}

/**
 * A damped harmonic oscillator described the way a designer thinks about it.
 *
 * @param dampingRatio 1.0 settles with no overshoot. Below 1.0 overshoots; reach
 *   for that only when a gesture's own momentum preceded the animation.
 * @param response how quickly the value reaches the target, in seconds. This is
 *   not a duration: a spring has no fixed duration, its settle time emerges.
 */
class Spring(dampingRatio: Double, response: Double) {
    private val omega = 2.0 * PI / response
    private val stiffness = omega * omega
    private val damping = 2.0 * dampingRatio * omega

    /**
     * One semi-implicit (symplectic) Euler step: velocity updates first, then
     * position uses the *new* velocity. Unlike explicit Euler this does not gain
     * energy on an oscillator, and unlike the closed-form solution it stays valid
     * at a damping ratio of exactly 1.0 and needs no coefficient re-solve when
     * the target changes mid-flight.
     */
    fun step(state: SpringState, target: Double, h: Double) {
        val acceleration = -stiffness * (state.x - target) - damping * state.v
        state.v += acceleration * h
        state.x += state.v * h
    }
}

/**
 * Release velocity from a short history of samples.
 *
 * Using only the final two events is wrong: pointer events coalesce and can share
 * a timestamp. Using the whole buffer regardless of age is also wrong: drag, hold
 * still for half a second, then release should yield zero velocity, but the oldest
 * sample would still be far away. Hence the staleness window.
 */
class VelocityTracker(private val capacity: Int = 5, private val staleMs: Double = 100.0) {
    private val xs = DoubleArray(capacity)
    private val ts = DoubleArray(capacity)
    private var count = 0
    private var head = 0

    fun reset() {
        count = 0
        head = 0
    }

    fun add(x: Double, t: Double) {
        xs[head] = x
        ts[head] = t
        head = (head + 1) % capacity
        if (count < capacity) count++
    }

    /** Pixels per second over the freshest usable window, or 0 if samples are stale. */
    fun velocity(): Double {
        if (count < 2) return 0.0
        val newest = (head - 1 + capacity) % capacity
        val now = ts[newest]
        var oldest = -1
        for (step in 1 until count) {
            val i = (head - 1 - step + capacity) % capacity
            if (now - ts[i] > staleMs) break
            oldest = i
        }
        if (oldest < 0) return 0.0
        val dt = ts[newest] - ts[oldest]
        if (dt <= 1e-3) return 0.0
        return (xs[newest] - xs[oldest]) / dt * 1000.0
    }
}
