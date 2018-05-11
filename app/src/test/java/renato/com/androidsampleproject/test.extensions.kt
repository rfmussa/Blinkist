package renato.com.androidsampleproject

import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import kotlin.reflect.KClass

/** Verifies certain behavior happened exact [times]. */
fun <T : Any> T.assertCalled(times: Int): T = Mockito.verify(this, Mockito.times(times))

/** Verifies certain behavior never happened. */
fun <T : Any> T.assertNeverCalled(): T = assertCalled(0)

fun <R : Any> R?.mockReturn(value: R?) {
  Mockito.`when`(this).thenReturn(value)
}

fun <T : Any> KClass<T>.mock(): T = Mockito.mock(java)

fun <T> any(): T {
  Mockito.any<T>()
  return uninitialized()
}

fun <T> any(type: Class<T>): T {
  Mockito.any<T>(type)
  return uninitialized()
}

private fun <T> uninitialized(): T = null as T

fun <T> T.toAraMatcher(): T {
  ArgumentMatchers.eq<T>(this)
  return this
}
